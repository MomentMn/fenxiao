package com.hansan.fenxiao.service.impl;

import com.hansan.fenxiao.auto.entities.WxUser;
import com.hansan.fenxiao.constants.CommissionEnum;
import com.hansan.fenxiao.constants.DeletedEnum;
import com.hansan.fenxiao.constants.MessageInfo;
import com.hansan.fenxiao.dao.ICommissionDao;
import com.hansan.fenxiao.dao.IUserDao;
import com.hansan.fenxiao.entities.Commission;
import com.hansan.fenxiao.entities.User;

import com.hansan.fenxiao.pay.wxpay.WxPayforPersonUtils;
import com.hansan.fenxiao.service.ICommissionService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("commissionService")
@Scope("prototype")
public class CommissionServiceImpl<T extends Commission> extends BaseServiceImpl<T>
        implements ICommissionService<T> {

    @Resource(name = "commissionDao")
    private ICommissionDao commissionDao;
    @Resource(name="userDao")
    private IUserDao userDao;



    @Override
    public List<Commission> getByUser(Integer userId) {
        return this.commissionDao.getByUser(userId);
    }

    @Override
    public Double getAllPayMoney(Integer loginUserId, String Ym, int type) {
        return commissionDao.getAllPayMoney(loginUserId,Ym,type);
    }

    @Override
    public Double getIncomeMoney(Integer loginUserId, String Ym, int type) {
        return commissionDao.getIncomeMoney(loginUserId,Ym,type);
    }

    /**
     * @param loginUser
     *     -- 登录用户
     * @param path
     * @description: 佣金
     * @author:谭农春
     * @createTime: 2018/6/5 16:50
     */
    @Override
    public Double wxPayfor(WxUser loginUser, Double amount, String path) {
       Double commission= userDao.getUserCommission(loginUser.getUserId());
        if(commission<amount){
            throw new RuntimeException("提现金额超过佣金");
        }
        User user = userDao.findById(User.class,loginUser.getUserId());
        String tradeNo ="";
         Double amountDouble= amount*100;
        int amt= amountDouble.intValue();
        Long time = System.currentTimeMillis();
        // 微信支付 state
        Map<String,String> maps =   WxPayforPersonUtils.transfer(path,loginUser.getOpenId(),amt,"佣金提现",time.toString());
      Double restCommission =0.00;
      //支付成功
      if("SUCCESS".equals(maps.get("state"))){
        // 支付完成
        Commission bean = new Commission();
        bean.setType(CommissionEnum.output.getCode());
        bean.setRemark(MessageInfo.commission_error);
        bean.setLevel(0);
        bean.setCreateDate(new Date());
        bean.setMoney(amount);
        bean.setDeleted(DeletedEnum.normal.getCode());
        bean.setVersion(0);
        bean.setOperator(loginUser.getWxName());
        bean.setUser(user);
        bean.setNo(tradeNo);
        // 保存在数据库里面
        commissionDao.saveOrUpdate(bean);
        restCommission =commission-amount;
        user.setCommission(restCommission);
//         更新用户的佣金
        userDao.saveOrUpdate(user);
      }
      else{
        // 微信支付问题
         throw new RuntimeException(maps.get("err_code_des"));
      }
        // 剩余佣金
        return restCommission;
    }
}