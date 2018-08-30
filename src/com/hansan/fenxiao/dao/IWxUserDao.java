package com.hansan.fenxiao.dao;

import com.hansan.fenxiao.entities.WxUser;

public abstract  interface IWxUserDao extends IBaseDao<WxUser>
{
	public WxUser getWxUserByUserId(int userId);
}
