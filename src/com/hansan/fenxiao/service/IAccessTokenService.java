package com.hansan.fenxiao.service;

public interface IAccessTokenService {
	/** 
	 * 获取access_token 
	 * @param appid 凭证 
	 * @param appsecret 密钥 
	 * @return 
	 */  
	public String getAccessToken(String appid, String appsecret); 
}
