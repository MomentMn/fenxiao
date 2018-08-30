package com.hansan.fenxiao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hansan.fenxiao.auto.entities.User;
import com.hansan.fenxiao.auto.entities.WxUser;
import com.hansan.fenxiao.mybatisdao.IUserDao2;
import com.hansan.fenxiao.service.IUserService2;
import com.hansan.fenxiao.utils.MapUtil;
import com.hansan.fenxiao.weixin.entity.WeChatUserInfo;

public class UserService2Impl implements IUserService2{
	@Autowired
	private IUserDao2 userDao;
	@Override
	public WxUser addWeChatAndUser(WeChatUserInfo wxuser) {
		User user=this.setUser();
		userDao.insertUser(user);
		WxUser wu=this.setWxUser(user.getId(),wxuser);
		/**
		 * 微信用户缓存进 Map里
		 */
		MapUtil.add(wxuser.getOpenId(), wu);
		userDao.insertWxUser(wu);
		return wu;
	}

	/**
	 * @description:
	 * @author:谭农春
	 * @createTime: 2018/6/5 19:35
	 */
	@Override
	public List<WxUser> loadCacheData() {
		return userDao.loadCacheData();
	}

	/**
	 * 
	 * @description:微信对象赋值
	 * @param id
	 * @param wxuser
	 * @return
	 * @author:王涛
	 * @createTime:2018年6月5日 下午4:30:13
	 */
	private WxUser setWxUser(Integer id, WeChatUserInfo wxuser) {
		WxUser wuser=new WxUser();
		wuser.setId(null);
		wuser.setCreatedate(new Date());
		wuser.setDeleted(false);
		wuser.setVersion(0);
		wuser.setUserId(id);
		wuser.setOpenId(wxuser.getOpenId());
		wuser.setWxName(wxuser.getNickname());
		wuser.setWxImageUrl(wxuser.getHeadImgUrl());
		wuser.setIsAuth(true);
		return wuser;
	}
	/**
	 * 
	 * @description:User 赋值
	 * @return
	 * @author:王涛
	 * @createTime:2018年6月5日 下午3:35:28
	 */
	private User setUser() {
		User user=new User();
		user.setId(null);
		user.setCreatedate(new Date());
		user.setDeleted(false);
		user.setVersion(0);
		return user;
	}
	
	/**
	 * 
	 * @description:更新微信表里的二维码地址
	 * @param wu
	 * @param url
	 * @author:王涛
	 * @createTime:2018年6月6日 下午3:51:59
	 */
	@Override
	public int updateWxUserQrCode(WxUser wu, String url) {
		wu.setWxQrUrl(url);
		int num=userDao.updateWxUser(wu);
		return num;
	}
}
