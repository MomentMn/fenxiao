package com.hansan.fenxiao.action;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hansan.fenxiao.auto.entities.WxUser;
import com.hansan.fenxiao.constant.WechatConstant;
import com.hansan.fenxiao.entities.AccessToken;
import com.hansan.fenxiao.service.IQrCodeService;
import com.hansan.fenxiao.service.IUserService2;
import com.hansan.fenxiao.service.IWeChatService;
import com.hansan.fenxiao.utils.MapUtil;
import com.hansan.fenxiao.utils.WeChatHttpUtil;
import com.hansan.fenxiao.weixin.entity.WeChatUserInfo;

@Controller("wechatMessageAction")
@Scope("prototype")
public class WeChatMessageAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	@Resource(name = "weChatService")
	private IWeChatService<WeChatUserInfo> weChatService;
	@Autowired
	private IUserService2 userService2;
	private String openId;
	@Autowired
	private IQrCodeService qrCodeService;

	/**
	 * 
	 * @description:获取code
	 * @author:王涛
	 * @throws IOException 
	 * @createTime:2018年6月1日 上午10:04:25
	 */
	public void getCode() throws IOException{
		String param=request.getParameter("paramt");
		//System.out.println("-- "+param);
		//微信ping的地址，来获取这个code，url是授权域名+方法名称
		String url=WechatConstant.AUTHOR_MESSAGE_URL;
		String code_url=WechatConstant.CODE_URL.replace("%",url);
		if(null==param){
			response.sendRedirect(code_url);
		}
		else{
			url=url+"param1="+URLEncoder.encode(param,"utf-8");
			code_url=WechatConstant.CODE_URL.replace("%",url);
			//System.out.println(URLDecoder.decode(code_url, "UTF-8"));
			response.sendRedirect(code_url);
		}
	}

	
	/**
	 * 
	 * @description:微信服务 ping过来的方法，获取code及其openId 的数据
	 * @return
	 * @author:王涛
	 * @createTime:2018年6月1日 上午10:16:39
	 */
	public void getMessage(){
		try{
			String code=request.getParameter("code");
			String param=request.getParameter("param1");
			if(null!=request.getParameter("param") ){
				param =param+"&param="+request.getParameter("param");
			}
			if(null!=code){
				openId=WeChatHttpUtil.getOauthOpenId(code);	//获取openid
			}
			AccessToken at=WeChatHttpUtil.getAccessToken(WechatConstant.APPID, WechatConstant.APPSECRET);
			WeChatUserInfo user=weChatService.getUserInfoService(at.getToken(),openId);
			WxUser wu=null;
			//map缓存 里面是否存在openid 存在 就不存储数据库，不存在 就 存储进数据库
			if(!MapUtil.isExist(user.getOpenId())){
				//微信绑定user 入库操作
				wu=userService2.addWeChatAndUser(user);
				request.getSession().setAttribute("loginUser", wu);
			}else{
				wu=MapUtil.get(user.getOpenId());
				request.getSession().setAttribute("loginUser", wu);
			}
			/**
			 * 自己的二维码生成
			 */
			int[] ids=new int[]{wu.getUserId()};
			String myUrl=qrCodeService.getQrCodeUrl(wu.getUserId(),0,ids);
			int num=userService2.updateWxUserQrCode(wu,myUrl);
			if(num!=0){
				System.out.println("用户["+wu.getWxName()+"]二维码生成成功,并更新入库！");
			}
			
			if(null!=param){
				String url=URLDecoder.decode(param, "UTF-8");
				response.sendRedirect(url);
			}else{
				String url=WechatConstant.MYURL;
				response.sendRedirect(url);
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}



	/**
	 *
	 * @description:jump
	 * @author:王涛
	 * @throws IOException
	 * @createTime:2018年6月1日 上午10:04:25
	 */
	public void jump() throws IOException{
		String param=request.getParameter("url");
		//System.out.println("-- "+param);
		//微信ping的地址，来获取这个code，url是授权域名+方法名称
		String url=WechatConstant.JUMP_AUTHOR_MESSAGE_URL+"url="+param;
		String code_url=WechatConstant.CODE_URL.replace("%",url);
		response.sendRedirect(code_url);
	}


	/**
	 *
	 * @description:微信服务 ping过来的方法，获取code及其openId 的数据
	 * @return
	 * @author:王涛
	 * @createTime:2018年6月1日 上午10:16:39
	 */
	public void wxJump(){
		try{
			String code=request.getParameter("code");
			String param=request.getParameter("url");
			if(null!=code){
				openId=WeChatHttpUtil.getOauthOpenId(code);	//获取openid
			}
			AccessToken at=WeChatHttpUtil.getAccessToken(WechatConstant.APPID, WechatConstant.APPSECRET);
			WeChatUserInfo user=weChatService.getUserInfoService(at.getToken(),openId);
			WxUser wu=null;
			//map缓存 里面是否存在openid 存在 就不存储数据库，不存在 就 存储进数据库
			if(!MapUtil.isExist(user.getOpenId())){
				//微信绑定user 入库操作
				wu=userService2.addWeChatAndUser(user);
				request.getSession().setAttribute("loginUser", wu);
			}else{
				wu=MapUtil.get(user.getOpenId());
				request.getSession().setAttribute("loginUser", wu);
			}

			/**
			 * 自己的二维码生成
			 */
			int[] ids=new int[]{};
			String myUrl=qrCodeService.getQrCodeUrl(wu.getUserId(),0,ids);
			int num=userService2.updateWxUserQrCode(wu,myUrl);
			if(num!=0){
				System.out.println("用户["+wu.getWxName()+"]二维码生成成功,并更新入库！");
			}

			if(null!=param){
				String url=URLDecoder.decode(param, "UTF-8");
				response.sendRedirect(url);
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}


	/**
	 * 
	 * @description:我的二维码生成地址
	 * @author:王涛
	 * @createTime:2018年6月6日 下午3:10:43
	 */
	public void createQrCode(){
		try {
			WxUser wu=(WxUser) request.getSession().getAttribute("loginUser");
			int userId=wu.getUserId();
			int[] ids=new int[]{};
			String url=qrCodeService.getQrCodeUrl(userId,0,ids);
			int num=userService2.updateWxUserQrCode(wu,url);
			if(num==0){
				System.out.println("更新二维码地址失败["+url+"]");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
}
