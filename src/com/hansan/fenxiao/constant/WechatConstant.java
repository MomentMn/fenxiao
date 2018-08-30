package com.hansan.fenxiao.constant;

import java.util.HashMap;
import java.util.Map;

public class WechatConstant {
	
	/**
	 * 储存openid
	 * @author Wong
	 */
	public static Map<String,String> OPENID =new HashMap<String,String>();
	/** 
	 * 获取access_token的接口地址（GET） 限200（次/天）
	 */
	public final static String ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	/**
	 * 公众号APPID
	 * @author Wong
	 * wx3c72451e8d578432 测试
	 * wx77025818d05fc144 正式
	 */
	public static String APPID="wx77025818d05fc144";
	/**
	 * 公众号APPSECRET
	 * @author Wong
	 * 正式：3631a430c8c2e1d33ac0ffada614fa90
	 * 58ca13ce6fd5736f485941f6dafe8f54 测试
	 */   
	public static String APPSECRET="3631a430c8c2e1d33ac0ffada614fa90";//"58ca13ce6fd5736f485941f6dafe8f54";
	/**微信支付商户号*/
    public static final String MCH_ID = "1370367102";
    /**微信支付API秘钥*/
    public static final String KEY = "jiangxizhiquanwx77025818d05fc144";
	/**
	 * 
	 */
	public static String GRANTTYPE="authorization_code";
	/**
	 * 访问方式
	 */
	public static String REQUEST_TYPE="GET";
	/**
	 * 访问方式 post
	 */
	public static String REQUEST_POST="POST";
	/**
	 * 获取字段
	 */
	public static String OPENIDVALUE="openid";
	/**
	 * 微信ping 地址，来获取code
	 * @author Wong
	 */
	public static String AUTHOR_MESSAGE_URL="http://fenxiao.ceshi.xingaokaowang.cn/message?";

  /**
   * 微信ping 地址，来获取code
   * @author Wong
   */
  public static String JUMP_AUTHOR_MESSAGE_URL="http://fenxiao.ceshi.xingaokaowang.cn/wxJump?";

	/**
	 * 微信授权页面，获取response_type=code的参数 
	 *  snsapi_userinfo(弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息，) 
	 * snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid）
	 * @author Wong  
	 */
	public static String CODE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx77025818d05fc144&redirect_uri=%&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
	public static String CODE="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx77025818d05fc144&redirect_uri=http://fenxiao.ceshi.xingaokaowang.cn/&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
	/**
	 * 获取openID 的拼接地址
	 * @author Wong
	 */
	public static String ACCESS_TOKEN_URL="https://api.weixin.qq.com/sns/oauth2/access_token";
	/**
	 * 获取用户信息的授权地址
	 */
	public static String USER_URL="https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
	// 永久二维码(字符串)  
	public final static String QR_LIMIT_STR_SCENE = "QR_LIMIT_STR_SCENE"; 
	// 创建二维码  
	public final static String CREATE_TICKET_PATH = "https://api.weixin.qq.com/cgi-bin/qrcode/create";
	// 通过ticket换取二维码  
	public final static String SHOW_QR_CODE_PATH = "https://mp.weixin.qq.com/cgi-bin/showqrcode"; 
    // 获取access_token的路径  
    public static String TOKEN_PATH = "https://api.weixin.qq.com/cgi-bin/token";
    public static final String DEFAULT_CHARSET = "utf-8";  
    public static int DEFAULT_CONNTIME = 5000;  
    public static int DEFAULT_READTIME = 5000; 
    
    public static final String RETURN_SUCCESS = "SUCCESS";
    
    /**微信统一下单url*/
    public static final String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    
    /**微信支付通知url*/
    public static final String NOTIFY_URL = "http://fenxiao.ceshi.xingaokaowang.cn/user/noticSuccess?orderId=";
    
    /**微信交易类型:公众号支付*/
    public static final String TRADE_TYPE_JSAPI = "JSAPI";
    
    /**
     * 扫描二维码请求的方法
     */
    public static final String URL="http://fenxiao.ceshi.xingaokaowang.cn/code";
    
    /**
     * 订单跳转页面
     */
    public static String ORDERURL="http://fenxiao.ceshi.xingaokaowang.cn/detail?id=";
    /**
     * 跳转页面
     */
    public static String MYURL="http://fenxiao.ceshi.xingaokaowang.cn/index.jsp?myUserId=";
	/**
	 * 提现支付页面
	 */
	public  static String PAY_FOR_URL="https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
	/**
	 * 点击菜单的事件
	 */
	public  static  String WE_VIEW_EVENT="VIEW";

}
