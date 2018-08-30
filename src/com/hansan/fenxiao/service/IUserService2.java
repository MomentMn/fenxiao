package com.hansan.fenxiao.service;

import com.hansan.fenxiao.auto.entities.WxUser;
import com.hansan.fenxiao.weixin.entity.WeChatUserInfo;

import java.util.List;

public abstract interface IUserService2 {
	 /**
     * 
     * @description:user表 WXuser表入库
     * @param user
     * @author:王涛
     * @createTime:2018年6月5日 下午3:26:51
     */
	public abstract WxUser addWeChatAndUser(WeChatUserInfo user);
 
	/**
	 * @description:  
	 *    
	 * @param
	 *    -- 加载缓存数据
	 * @author:谭农春
	 * @createTime: 2018/6/5 19:35 
	 */
	
	public List<WxUser> loadCacheData();
	/**
	 * 
	 * @description:更新微信对象表里的个人二维码url
	 * @param wu
	 * @param url
	 * @author:王涛
	 * @createTime:2018年6月6日 下午3:50:50
	 */
	public abstract int updateWxUserQrCode(WxUser wu, String url);
}
