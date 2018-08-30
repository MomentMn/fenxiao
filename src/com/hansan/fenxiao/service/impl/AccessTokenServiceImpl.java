package com.hansan.fenxiao.service.impl;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.hansan.fenxiao.service.IAccessTokenService;
import com.hansan.fenxiao.utils.QRHttpUtils;

@Service("accessTokenService")
@Scope("prototype")
public class AccessTokenServiceImpl implements IAccessTokenService{

	private static final  Logger logger = LoggerFactory.getLogger(AccessTokenServiceImpl.class);
	@Override
	public String getAccessToken(String appid, String appsecret) {
		 String result = QRHttpUtils.getAccessToken(appid,appsecret);  
		    JSONObject jsonObject = JSONObject.fromObject(result);
		   logger.info(jsonObject.toString());
		    if (null != jsonObject) {  
		        try {  
		            result = jsonObject.getString("access_token");  
		        } catch (JSONException e) {
							logger.info(e.getMessage());
		        	System.out.println("获取token失败 errcode:"+jsonObject.getInt("errcode") +",errmsg:"+ jsonObject.getString("errmsg"));
		           //logger.info("获取token失败 errcode:"+jsonObject.getInt("errcode") +",errmsg:"+ jsonObject.getString("errmsg"));                
		        }  
		    }  
		    return result;  
	}
}
