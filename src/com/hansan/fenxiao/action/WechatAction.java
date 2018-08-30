package com.hansan.fenxiao.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

import com.hansan.fenxiao.auto.entities.WxUser;
import com.hansan.fenxiao.constant.WechatConstant;
import com.hansan.fenxiao.entities.AccessToken;
import com.hansan.fenxiao.service.IQrCodeService;
import com.hansan.fenxiao.service.IUserService2;
import com.hansan.fenxiao.service.IWeChatService;
import com.hansan.fenxiao.utils.MapUtil;
import com.hansan.fenxiao.utils.ReadXmlTools;
import com.hansan.fenxiao.utils.WeChatHttpUtil;
import com.hansan.fenxiao.weixin.entity.WeChatUserInfo;
import com.sun.xml.internal.ws.util.xml.XmlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;


@Controller("wechatAction")
@Scope("prototype")
public class WechatAction extends BaseAction{
	private Logger logger = LoggerFactory.getLogger(WechatAction.class);
	private static final long serialVersionUID = 1L;
	private String openId;

	/**
	 * 微信接入口
	 * @throws IOException 
	 */
	public void token() throws Exception {

		String echostr=request.getParameter("echostr");
		if(echostr!=null){
			try {
				response.getWriter().print(echostr);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		

		
	}
	
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
}
