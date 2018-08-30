package com.hansan.fenxiao.utils.wxpay;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.alibaba.fastjson.JSON;
import com.hansan.fenxiao.constant.WechatConstant;
import com.hansan.fenxiao.pay.alipay.MD5;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

public class WeChatUtils
{
	/**
     * 根据code获取微信授权access_token
     * @param request
	 * @throws Exception 
     */
    public static AuthToken getTokenByAuthCode(String code) throws Exception{
        AuthToken authToken;
        StringBuilder json = new StringBuilder();
        try {
            URL url = new URL(WechatConstant.ACCESS_TOKEN_URL+"appid="+ WechatConstant.APPID+"&secret="+ WechatConstant.APPSECRET+"&code="+code+"&grant_type=authorization_code");
            URLConnection uc = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String inputLine ;
            while((inputLine=in.readLine())!=null){
                json.append(inputLine);
            }
            in.close();
            //将json字符串转成javaBean
            authToken = (AuthToken)JSON.parse(json.toString());
    } catch (Exception e) {
            throw new Exception("微信工具类:根据授权code获取access_token异常",e);
        }
        return authToken;
    }

    /**
     * 获取微信签名
     * @param map 请求参数集合
     * @return 微信请求签名串
     */
    public static String getSign(SortedMap<String,Object> map){
        StringBuffer sb = new StringBuffer();
        Set set = map.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()){
            Map.Entry entry = (Map.Entry) iterator.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            //参数中sign、key不参与签名加密
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)){
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + WechatConstant.KEY);
        String sign = MD5.MD5Encode(sb.toString(),null).toUpperCase();
        return sign;
    }

    /**
     * 解析微信服务器发来的请求
     * @param inputStream
     * @return 微信返回的参数集合
     * @throws Exception 
     */
    public static SortedMap<String,Object> parseXml(InputStream inputStream) throws Exception {
        SortedMap<String,Object> map = Maps.newTreeMap();
        try {
            //获取request输入流
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputStream);
            //得到xml根元素
            Element root = document.getRootElement();
            //得到根元素所有节点
            List<Element> elementList = root.elements();
            //遍历所有子节点
            for (Element element:elementList){
                map.put(element.getName(),element.getText());
            }
            //释放资源
            inputStream.close();
        } catch (Exception e) {
            throw new Exception("微信工具类:解析xml异常",e);
        }
        return map;
    }

    /**
     * 扩展xstream,使其支持name带有"_"的节点
     */
    public static XStream xStream = new XStream(new DomDriver("UTF-8",new XmlFriendlyNameCoder("-_","_")));

    /**
     * 请求参数转换成xml
     * @param data
     * @return xml字符串
     */
    public static String sendDataToXml(WxPaySendData data){
        xStream.autodetectAnnotations(true);
        xStream.alias("xml", WxPaySendData.class);
        return xStream.toXML(data);
    }

    /**
     *  获取当前时间戳
     * @return 当前时间戳字符串
     */
    public static String getTimeStamp(){
        return String.valueOf(System.currentTimeMillis()/1000);
    }

    /**
     * 获取指定长度的随机字符串
     * @param length
     * @return 随机字符串
     */
    public static String getRandomStr(int length){
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
    
    
    /**
	*微信支付统一下单
	 * @throws Exception 
	**/
	public static Map<String, Object> unifiedOrder(int orderId,String orderNo, int totalFee, Map<String, Object> map) throws Exception
	{
		Map<String, Object> resultMap;
		try
		{
			WxPaySendData paySendData = new WxPaySendData();
			String str = "微信订单支付:" + orderNo;
			// 构建微信支付请求参数集合
			paySendData.setAppId(WechatConstant.APPID);
			paySendData.setAttach(new String(str.getBytes("UTF-8")));
			paySendData.setBody(new String("商品描述".getBytes("UTF-8")));
			paySendData.setMchId(WechatConstant.MCH_ID);
			paySendData.setNonceStr(WeChatUtils.getRandomStr(32));
			paySendData.setNotifyUrl(WechatConstant.NOTIFY_URL+orderId);
			paySendData.setDeviceInfo("WEB");
			paySendData.setOutTradeNo(orderNo);
			paySendData.setTotalFee(totalFee);
			paySendData.setTradeType(WechatConstant.TRADE_TYPE_JSAPI);
			paySendData.setSpBillCreateIp((String) map.get("remoteIp"));
			paySendData.setOpenId((String) map.get("openId"));
			// 将参数拼成map,生产签名
			SortedMap<String, Object> params = buildParamMap(paySendData);
			paySendData.setSign(WeChatUtils.getSign(params));
			// 将请求参数对象转换成xml
			String reqXml = WeChatUtils.sendDataToXml(paySendData);
			// 发送请求
			byte[] xmlData = reqXml.getBytes();
			URL url = new URL(WechatConstant.UNIFIED_ORDER_URL);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setDoOutput(true);
			urlConnection.setDoInput(true);
			urlConnection.setUseCaches(false);
			urlConnection.setRequestProperty("Content_Type", "text/xml");
			urlConnection.setRequestProperty("Content-length", String.valueOf(xmlData.length));
			DataOutputStream outputStream = new DataOutputStream(urlConnection.getOutputStream());
			outputStream.write(xmlData);
			outputStream.flush();
			outputStream.close();
			resultMap = WeChatUtils.parseXml(urlConnection.getInputStream());
		}
		catch (Exception e)
		{
			throw new Exception("微信支付统一下单异常", e);
		}
		return resultMap;
	}

	/**
	 * 构建统一下单参数map 用于生成签名
	 * @param data
	 * @return SortedMap<String,Object>
	 */
	private static SortedMap<String, Object> buildParamMap(WxPaySendData data)
	{
		SortedMap<String, Object> paramters = new TreeMap<String, Object>();
		if (null != data)
		{
			if (StringUtils.isNotEmpty(data.getAppId()))
			{
				paramters.put("appid", data.getAppId());
			}
			if (StringUtils.isNotEmpty(data.getAttach()))
			{
				paramters.put("attach", data.getAttach());
			}
			if (StringUtils.isNotEmpty(data.getBody()))
			{
				paramters.put("body", data.getBody());
			}
			if (StringUtils.isNotEmpty(data.getDetail()))
			{
				paramters.put("detail", data.getDetail());
			}
			if (StringUtils.isNotEmpty(data.getDeviceInfo()))
			{
				paramters.put("device_info", data.getDeviceInfo());
			}
			if (StringUtils.isNotEmpty(data.getFeeType()))
			{
				paramters.put("fee_type", data.getFeeType());
			}
			if (StringUtils.isNotEmpty(data.getGoodsTag()))
			{
				paramters.put("goods_tag", data.getGoodsTag());
			}
			if (StringUtils.isNotEmpty(data.getLimitPay()))
			{
				paramters.put("limit_pay", data.getLimitPay());
			}
			if (StringUtils.isNotEmpty(data.getMchId()))
			{
				paramters.put("mch_id", data.getMchId());
			}
			if (StringUtils.isNotEmpty(data.getNonceStr()))
			{
				paramters.put("nonce_str", data.getNonceStr());
			}
			if (StringUtils.isNotEmpty(data.getNotifyUrl()))
			{
				paramters.put("notify_url", data.getNotifyUrl());
			}
			if (StringUtils.isNotEmpty(data.getOpenId()))
			{
				paramters.put("openid", data.getOpenId());
			}
			if (StringUtils.isNotEmpty(data.getOutTradeNo()))
			{
				paramters.put("out_trade_no", data.getOutTradeNo());
			}
			if (StringUtils.isNotEmpty(data.getSign()))
			{
				paramters.put("sign", data.getSign());
			}
			if (StringUtils.isNotEmpty(data.getSpBillCreateIp()))
			{
				paramters.put("spbill_create_ip", data.getSpBillCreateIp());
			}
			if (StringUtils.isNotEmpty(data.getTimeStart()))
			{
				paramters.put("time_start", data.getTimeStart());
			}
			if (StringUtils.isNotEmpty(data.getTimeExpire()))
			{
				paramters.put("time_expire", data.getTimeExpire());
			}
			if (StringUtils.isNotEmpty(data.getProductId()))
			{
				paramters.put("product_id", data.getProductId());
			}
			if (data.getTotalFee() > 0)
			{
				paramters.put("total_fee", data.getTotalFee());
			}
			if (StringUtils.isNotEmpty(data.getTradeType()))
			{
				paramters.put("trade_type", data.getTradeType());
			}
			// 申请退款参数
			if (StringUtils.isNotEmpty(data.getTransactionId()))
			{
				paramters.put("transaction_id", data.getTransactionId());
			}
			if (StringUtils.isNotEmpty(data.getOutRefundNo()))
			{
				paramters.put("out_refund_no", data.getOutRefundNo());
			}
			if (StringUtils.isNotEmpty(data.getOpUserId()))
			{
				paramters.put("op_user_id", data.getOpUserId());
			}
			if (StringUtils.isNotEmpty(data.getRefundFeeType()))
			{
				paramters.put("refund_fee_type", data.getRefundFeeType());
			}
			if (null != data.getRefundFee() && data.getRefundFee() > 0)
			{
				paramters.put("refund_fee", data.getRefundFee());
			}
		}
		return paramters;
	}
}
