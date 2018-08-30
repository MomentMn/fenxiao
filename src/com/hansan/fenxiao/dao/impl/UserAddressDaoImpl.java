package com.hansan.fenxiao.dao.impl;

import com.hansan.fenxiao.dao.IUserAddressDao;
import com.hansan.fenxiao.entities.UserAddress;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * @class: UserAddressDaoImpl
 * @desc:
 * @author: huangxuejing
 * @date: 2018/6/5
 */
@Repository("userAddressDao")
@Scope("prototype")
public class UserAddressDaoImpl extends BaseDaoImpl<UserAddress> implements IUserAddressDao {
}
