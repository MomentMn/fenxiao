 package com.hansan.fenxiao.service.impl;
 
 import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.hansan.fenxiao.dao.IUserDao;
import com.hansan.fenxiao.dao.IWxUserDao;
import com.hansan.fenxiao.entities.User;
import com.hansan.fenxiao.entities.WxUser;
import com.hansan.fenxiao.mybatisdao.IUserDao2;
import com.hansan.fenxiao.service.IUserService;
 
 @Service("userService")
 @Scope("prototype")
 public class UserServiceImpl<T extends User> extends BaseServiceImpl<T>
   implements IUserService<T>
 {
 
   @Resource(name="userDao")
   private IUserDao userDao;
   @Resource(name="wxUserDao")
   private IWxUserDao wxUserDao;
   @Autowired
   private IUserDao2 userDao2;
 
   public User getUserByName(String name)
   {
     return this.userDao.getUserByName(name);
   }
 
   public User login(String name, String password)
   {
     return this.userDao.login(name, password);
   }
 
   public User getUserByPhone(String phone)
   {
     return this.userDao.getUserByPhone(phone);
   }
 
   public User getUserByNo(String no)
   {
     return this.userDao.getUserByNo(no);
   }
 
   public List<User> levelUserList(String no)
   {
     return this.userDao.levelUserList(no);
   }
 
   public List<User> levelUserTodayList(String no)
   {
     return this.userDao.levelUserTodayList(no);
   }
 
   public List<User> levelUserTodayStatusList(String no)
   {
     return this.userDao.levelUserTodayStatusList(no);
   }

     public User getUserByNameAndPhone(String name, String phone)
   {
     return this.userDao.getUserByNameAndPhone(name, phone);
   }

     @Override
     public Double getUserCommission(Integer userId) {
         return userDao.getUserCommission(userId);
     }

	@Override
	public String getOpenIdByUserId(int userId)
	{
		WxUser wxUser = wxUserDao.getWxUserByUserId(userId);
		return wxUser.getOpenId();
	}
 }