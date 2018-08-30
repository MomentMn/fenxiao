package com.hansan.fenxiao.service;

import com.hansan.fenxiao.weixin.entity.WeChatUserInfo;



public interface IWeChatService<T extends WeChatUserInfo>{
	public abstract WeChatUserInfo getUserInfoService(String token,String openId);
	
	public String showQrcode(String ticket);
}
