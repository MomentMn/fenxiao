package com.hansan.fenxiao.service.impl;

import com.hansan.fenxiao.entities.UserAddress;
import com.hansan.fenxiao.service.IUserAddressService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @class: UserAddressServiceImpl
 * @desc:
 * @author: huangxuejing
 * @date: 2018/6/5
 */
@Service("userAddressService")
@Scope("prototype")
public class UserAddressServiceImpl <T extends UserAddress> extends BaseServiceImpl<T> implements IUserAddressService<T> {
}
