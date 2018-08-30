package com.hansan.fenxiao.dao.impl;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.hansan.fenxiao.dao.IWxUserDao;
import com.hansan.fenxiao.entities.WxUser;

@Repository("wxUserDao")
@Scope("prototype")
public class WxUserDaoImpl extends BaseDaoImpl<WxUser> implements IWxUserDao
{
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	private Session getSession()
	{
		return this.sessionFactory.getCurrentSession();
	}

	@Override
	public WxUser getWxUserByUserId(int userId)
	{
		String hql = "from WxUser where userId=" + userId;
		WxUser wxUser = (WxUser) getSession().createQuery(hql).uniqueResult();
		return wxUser;
	}
}
