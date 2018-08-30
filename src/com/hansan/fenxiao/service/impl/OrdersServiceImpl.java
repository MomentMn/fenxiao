package com.hansan.fenxiao.service.impl;

import java.util.Date;

import javax.annotation.Resource;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.hansan.fenxiao.constants.CommissionTypeEnum;
import com.hansan.fenxiao.constants.DeletedEnum;
import com.hansan.fenxiao.constants.OrdersStatusEnum;
import com.hansan.fenxiao.dao.ICommissionDao;
import com.hansan.fenxiao.dao.IConfigDao;
import com.hansan.fenxiao.dao.IOrdersDao;
import com.hansan.fenxiao.dao.ISupervisorDao;
import com.hansan.fenxiao.dao.IUserDao;
import com.hansan.fenxiao.dao.IWxUserDao;
import com.hansan.fenxiao.entities.Commission;
import com.hansan.fenxiao.entities.Config;
import com.hansan.fenxiao.entities.Orders;
import com.hansan.fenxiao.entities.Supervisor;
import com.hansan.fenxiao.entities.User;
import com.hansan.fenxiao.entities.WxUser;
import com.hansan.fenxiao.enums.LevelEnum;
import com.hansan.fenxiao.service.IOrdersService;
import com.hansan.fenxiao.service.IQrCodeService;
import com.hansan.fenxiao.utils.SavePic;

@Service("ordersService")
@Scope("prototype")
public class OrdersServiceImpl<T extends Orders> extends BaseServiceImpl<T> implements IOrdersService<T>
{

	@Resource(name = "ordersDao")
	private IOrdersDao ordersDao;
	@Resource(name = "configDao")
	private IConfigDao configDao;
	@Resource(name = "wxUserDao")
	private IWxUserDao wxUserDao;
	@Resource(name = "supervisorDao")
	private ISupervisorDao supervisorDao;
	@Resource(name = "userDao")
	private IUserDao userDao;
	@Resource(name = "commissionDao")
	private ICommissionDao commissionDao;
	
	@Autowired
	private IQrCodeService qrCodeService;

	public Orders findByNo(String no)
	{
		return this.ordersDao.findByNo(no);
	}

	/**
	   * @description:订单付款完成，调整订单状态，计算佣金，生成二维码。
	   * @author:衷文涛
	 * @throws Exception
	   * @createTime:2018年6月4日 上午11:16:34
	   */
	@Override
	public void updateOrder(Integer orderId,Integer userId) throws Exception
	{

		if (orderId == null)
		{
			throw new Exception("订单id不能为空");
		}
		Orders orders = this.ordersDao.findById(Orders.class, orderId);// 从数据库中获取order
		if (orders == null)
		{
			throw new Exception("未找到订单");
		}

//		if (orders.getStatus().intValue() == OrdersStatusEnum.already_paid.getId())
//		{
//			throw new Exception("订单已支付");
//		}
		if(null!= orders.getParentParam() )
		// 计算佣金
		{
			calculationCommission(orders);
		}
		int[] ids = new int[0];
		String parentParam = orders.getParentParam();
		if(parentParam == null || "".equals(parentParam)) {
			ids = new int[1];
			ids[0]= userId.intValue();
		}else if(parentParam.split(",").length == 1){
			ids = new int[2];
			ids[0] = Integer.parseInt(parentParam.split("~")[0]);
			ids[1] = userId.intValue();
		}else {
			ids = new int[2];
			ids[0] = Integer.parseInt(parentParam.split("~")[1]);
			ids[1] = userId.intValue();
		}
		logger.info(" old userids is " + orders.getParentParam() +" && new userids is " + StringUtils.join(ids,"~"));
		// 生成二维码
		String orderQrUrl = getQrUrl(orders.getNo(),orders.getUser().getId(),Integer.parseInt(orders.getProductId()),ids);
		// 更新order
		orders.setStatus(OrdersStatusEnum.already_paid.getId());
		orders.setPayoffDate(new Date());
		orders.setOrderQrUrl(orderQrUrl);
		ordersDao.saveOrUpdate(orders);
	}

	// 获取图片
	private String getQrUrl(String orderNo,int userId,int productID,int[] ids) throws Exception
	{
		//生成微信二维码
		//String picUrl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQEH8TwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyRnV5THc1akxlWmgxMDAwMHcwN3YAAgSzshRbAwQAAAAA";
		String picUrl = qrCodeService.getQrCodeUrl(userId,productID,ids);
		return SavePic.savePic(orderNo, picUrl);
	}

	// 计算佣金
	private void calculationCommission(Orders orders) 
	{
		Config config = configDao.findById(Config.class, 1);

		String parentParam = orders.getParentParam();
		if(parentParam == null || "".equals(parentParam)) {
			inserCommission(orders.getUser().getId(), orders.getUser().getId(), LevelEnum.FIRST_LEVEL.getId(), orders, config);
		}else {
			String[] ids = parentParam.split("~");
			if(ids.length == 1) {
				//两级
				inserCommission(orders.getUser().getId(), orders.getUser().getId(), LevelEnum.FIRST_LEVEL.getId(), orders, config);
				inserCommission(Integer.parseInt(ids[0]), orders.getUser().getId(), LevelEnum.SECOND_LEVEL.getId(), orders, config);
			}else {
				//三级分销
				inserCommission(orders.getUser().getId(), orders.getUser().getId(), LevelEnum.FIRST_LEVEL.getId(), orders, config);
				inserCommission(Integer.parseInt(ids[0]), orders.getUser().getId(), LevelEnum.SECOND_LEVEL.getId(), orders, config);
				inserCommission(Integer.parseInt(ids[1]), Integer.parseInt(ids[0]), LevelEnum.THIRD_LEVEL.getId(), orders, config);
			}
		}
	}

	// 插入返利
	private void inserCommission(int preId,int id,int level,Orders orders,Config config) 
	{
		//订单总额
		double ordersTotalMoney = orders.getMoney() == null ? 0d : orders.getMoney() ;
		//只有一级
		double commissionMoney = 0d;
		
		if(level == LevelEnum.FIRST_LEVEL.getId()) {
			commissionMoney = ordersTotalMoney*config.getFirstLevel();
		}else if(level == LevelEnum.SECOND_LEVEL.getId()) {
			commissionMoney = ordersTotalMoney*config.getSecondLevel();
		}else {
			commissionMoney = ordersTotalMoney*config.getThirdLevel();
		}
		
		User operatorUser = orders.getUser();
		
		WxUser preUser = wxUserDao.getWxUserByUserId(preId);
		Date date = new Date();
		
		User pUser = userDao.findById(User.class, preId);
		
		Supervisor supervisor = new Supervisor();
		supervisor.setDeleted(DeletedEnum.normal.getCode());
		supervisor.setCreateDate(date);
		supervisor.setVersion(0);
		supervisor.setParentUserId(preId);
		supervisor.setParentWxName(preUser.getWxName());
		supervisor.setParentWxImageUrl(preUser.getWxImageUrl());
		supervisor.setUserId(id);
		supervisor.setLevel(level);
		supervisor.setOrderId(orders.getId());
		
		supervisorDao.saveOrUpdate(supervisor);
		
		Commission commission = new Commission();
		
		commission.setDeleted(DeletedEnum.normal.getCode());
		commission.setCreateDate(date);
		commission.setVersion(0);
		commission.setUser(pUser);
		commission.setType(CommissionTypeEnum.admission.getCode());
		commission.setMoney(commissionMoney);
		commission.setNo(orders.getNo());
		commission.setLevel(level);
		commission.setLowerLevelNo(null);
		commission.setRemark(null);
		commission.setOperator(operatorUser.getName());
		//大于零的佣金才记录
		if(commissionMoney>0){
			commissionDao.saveOrUpdate(commission);
		}
		double balance = pUser.getCommission() == null ? 0d : pUser.getCommission();
		pUser.setCommission(balance + commissionMoney);
		userDao.saveOrUpdate(pUser);
	}
}
