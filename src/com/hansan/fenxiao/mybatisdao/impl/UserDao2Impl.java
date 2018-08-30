package com.hansan.fenxiao.mybatisdao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hansan.fenxiao.auto.entities.*;
import com.hansan.fenxiao.mybatisdao.IUserDao2;
import com.hansan.fenxiao.mybatisdao.mapper.UserMapper;
import com.hansan.fenxiao.mybatisdao.mapper.WxUserMapper;

import java.util.List;

@Service
public class UserDao2Impl implements IUserDao2{
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private WxUserMapper wxMapper;
	
	/**
	 * 
	 * @description:用户入库
	 * @param user
	 * @return
	 * @author:王涛
	 * @createTime:2018年6月5日 下午4:39:30
	 */
	@Override
	public void insertUser(User user) {
		userMapper.insertSelective(user);
	}
	/**
	 * 
	 * @description:微信信息入库
	 * @param wu
	 * @author:王涛
	 * @createTime:2018年6月5日 下午4:38:36
	 */
	@Override
	public void insertWxUser(WxUser wu) {
		wxMapper.insertSelective(wu);
	}

	/**
	 * @description:
	 * @author:谭农春
	 * @createTime: 2018/6/5 19:37
	 */
	@Override
	public List<WxUser> loadCacheData() {
    WxUserExample example = new WxUserExample();
		return  wxMapper.selectByExample(example);
	}
	
	/**
	 * 
	 * @description:更新微信对象里面的二维码地址
	 * @param wu
	 * @author:王涛
	 * @createTime:2018年6月6日 下午3:55:12
	 */
	@Override
	public int updateWxUser(WxUser wu) {
		return wxMapper.updateByPrimaryKey(wu);
	}
}
