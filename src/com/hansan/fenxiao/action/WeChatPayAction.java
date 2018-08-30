package com.hansan.fenxiao.action;

import java.util.Map;
import java.util.SortedMap;

import javax.annotation.Resource;


import com.hansan.fenxiao.auto.entities.WxUser;
import com.hansan.fenxiao.utils.MapUtil;
import com.hansan.fenxiao.utils.ReadXmlTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hansan.fenxiao.constant.WechatConstant;
import com.hansan.fenxiao.entities.Orders;
import com.hansan.fenxiao.entities.User;
import com.hansan.fenxiao.service.IOrdersService;
import com.hansan.fenxiao.service.IUserService;
import com.hansan.fenxiao.utils.wxpay.Maps;
import com.hansan.fenxiao.utils.wxpay.WeChatUtils;

@Controller("weChatPayAction")
@Scope("prototype")
public class WeChatPayAction extends BaseAction
{
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = LoggerFactory.getLogger(WeChatPayAction.class);
	
	@Resource(name="ordersService")
	private IOrdersService<Orders> ordersService;
	@Resource(name="userService")
	private IUserService<User> userService;
	
	String orderId;
	String appId;
	String nonceStr;
	String prepayId;
	String paySign;
	String timeStamp;
	String qrCodeUrl;
	String openId;

	public String unifiedOrder() throws Exception
	{
		String orderId =this.request.getParameter("orderId");
		this.orderId = orderId;
		Orders order = ordersService.findById(Orders.class, Integer.parseInt(orderId));
		com.hansan.fenxiao.auto.entities.WxUser loginUser = (com.hansan.fenxiao.auto.entities.WxUser)this.request.getSession().getAttribute("loginUser");
		//User loginUse = (User)this.request.getSession().getAttribute("loginUser");
		String openId = userService.getOpenIdByUserId(loginUser.getUserId());
				// "okAVEwR3jYKmDWYwJ2v6JQg-jBDU";//userService.getOpenIdByUserId(17);
		//String code = request.getParameter("code");
		//通过code获取网页授权access_token
        //AuthToken authToken = WeChatUtils.getTokenByAuthCode(code);
		
		String orderNo = order.getNo();
		int totalFee = (int)(order.getMoney().doubleValue() * 100);
		// 通过code获取网页授权access_token
		// 构建微信统一下单需要的参数
		Map<String, Object> map = Maps.newHashMap();
		map.put("openId", openId);// 用户标识openId
		map.put("remoteIp",this.request.getRemoteAddr());// 请求Ip地址
		// 调用统一下单service
		Map<String, Object> resultMap = WeChatUtils.unifiedOrder(order.getId(),orderNo, totalFee, map);
		String returnCode = (String) resultMap.get("return_code");// 通信标识
		String resultCode = (String) resultMap.get("result_code");// 交易标识
		// 只有当returnCode与resultCode均返回“success”，才代表微信支付统一下单成功
		if (WechatConstant.RETURN_SUCCESS.equals(resultCode) && WechatConstant.RETURN_SUCCESS.equals(returnCode))
		{
			String appId = (String) resultMap.get("appid");// 微信公众号AppId
			String timeStamp = WeChatUtils.getTimeStamp();// 当前时间戳
			String prepayId = "prepay_id=" + resultMap.get("prepay_id");// 统一下单返回的预支付id
			String nonceStr = WeChatUtils.getRandomStr(20);// 不长于32位的随机字符串
			SortedMap<String, Object> signMap = Maps.newTreeMap();// 自然升序map
			signMap.put("appId", appId);
			signMap.put("package", prepayId);
			signMap.put("timeStamp", timeStamp);
			signMap.put("nonceStr", nonceStr);
			signMap.put("signType", "MD5");
			this.appId = appId;
			this.timeStamp = timeStamp;
			this.nonceStr = nonceStr;
			this.prepayId = prepayId;
			this.openId=openId;
			this.paySign = WeChatUtils.getSign(signMap);// 获取签名
		}
		else
		{
			logger.error("微信统一下单失败,订单编号:" + orderNo + ",失败原因:" + resultMap.get("err_code_des"));
		}
		return SUCCESS;
	}
	
	public String paySuccess() {
		String orderId = this.request.getParameter("orderId");
		Orders order = ordersService.findById(Orders.class, Integer.parseInt(orderId));
		this.qrCodeUrl = order.getOrderQrUrl();
		return SUCCESS;
	}
	
	public void noticSuccess() throws NumberFormatException, Exception {
		  Map<String,String> map = ReadXmlTools.readXml(request);
		WxUser wxUser = MapUtil.get(map.get("openid"));
		ordersService.updateOrder(Integer.parseInt(map.get("orderId")),wxUser.getUserId());
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getAppId()
	{
		return appId;
	}
	public void setAppId(String appId)
	{
		this.appId = appId;
	}
	public String getNonceStr()
	{
		return nonceStr;
	}
	public void setNonceStr(String nonceStr)
	{
		this.nonceStr = nonceStr;
	}
	public String getPrepayId()
	{
		return prepayId;
	}
	public void setPrepayId(String prepayId)
	{
		this.prepayId = prepayId;
	}
	public String getPaySign()
	{
		return paySign;
	}
	public void setPaySign(String paySign)
	{
		this.paySign = paySign;
	}
	public String getTimeStamp()
	{
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp)
	{
		this.timeStamp = timeStamp;
	}
	public String getQrCodeUrl()
	{
		return qrCodeUrl;
	}
	public void setQrCodeUrl(String qrCodeUrl)
	{
		this.qrCodeUrl = qrCodeUrl;
	}


}
