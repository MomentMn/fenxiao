package com.hansan.fenxiao.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.hansan.fenxiao.constant.WechatConstant;
import com.hansan.fenxiao.entities.AccessToken;

public class WeChatHttpUtil {
	//private static Logger logger = Logger.getLogger(WeChatHttpUtil.class);
	
	/**
	 * 
	 * @description:https请求
	 * @param requestUrl
	 * @param requestMethod
	 * @param params
	 * @return
	 * @author:王涛
	 * @createTime:2018年6月1日 下午2:20:45
	 */
	public static JSONObject httpsRequest(String requestUrl,
			String requestMethod, String params)  {

		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();

		try {
			/** 创建SSLContext对象,并用我们制定的管理对象初始化 */
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			/** 从上述的SSLContext对象中获得 SSLSocketFactory */
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpsUrlConn = (HttpsURLConnection) url
					.openConnection();
			httpsUrlConn.setSSLSocketFactory(ssf);

			httpsUrlConn.setDoInput(true);
			httpsUrlConn.setDoOutput(true);
			httpsUrlConn.setUseCaches(false);
			httpsUrlConn.setRequestMethod(requestMethod);
			if ("GET".equalsIgnoreCase(requestMethod)) {
				httpsUrlConn.connect();
			}
			/** 当有数据提交时 */
			if (null != params) {
				OutputStream output = httpsUrlConn.getOutputStream();
				/** 设置编码格式，防止中文乱码 */
				output.write(params.getBytes("UTF-8"));
				output.close();
			}
			
			String contentType = httpsUrlConn.getHeaderField("Content-Type");
			if(contentType.indexOf("image") != -1){// 返回值类型是图片
				String oldFileName = httpsUrlConn.getHeaderField("Content-disposition");
				String[] oldFileNameSplits = oldFileName.split("\\.");
				String fileType = oldFileNameSplits[oldFileNameSplits.length - 1];
				oldFileNameSplits = fileType.split("\\\"");
				fileType = oldFileNameSplits[0];
				String folderPath = getProjectPath()+"/resource/weixinimg";
				String fileName = "WX_" + getUUID() + "." + fileType;
				
				File folder = new File(folderPath);
				// 判断文件目录是否存在,创建文件夹和文件
				if (!folder.exists()) {
					folder.mkdirs();
				}
				File file = new File(folderPath + "/" + fileName);
				BufferedInputStream bis = new BufferedInputStream(httpsUrlConn.getInputStream());
				FileOutputStream fos = new FileOutputStream(file);
				byte[] bufferArray = new byte[1024];
				int len;
				while ((len = bis.read(bufferArray)) != -1) {
					fos.write(bufferArray, 0, len);
				}
				fos.close();
				bis.close();

				httpsUrlConn.disconnect();
				
				jsonObject = new JSONObject();
				jsonObject.put("JSON_PARAM_NAME_IMAGE_PATH", "/resource/weixinimg/" + fileName);
			}else{// 返回值类型是文本
				/** 将返回的输入流转换成字符 */
				InputStream inputStream = httpsUrlConn.getInputStream();
				InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8");
				BufferedReader bufferedReader = new BufferedReader(isr);
	
				String str = null;
				while ((str = bufferedReader.readLine()) != null) {
					buffer.append(str);
				}
				bufferedReader.close();
				isr.close();
				inputStream.close();
	
				inputStream = null;
				httpsUrlConn.disconnect();
				jsonObject = JSONObject.parseObject(buffer.toString());
				
				/*String errcode = jsonObject.getString("JSON_PARAM_NAME_ERRCODE");
				if(errcode != null && !"".equals(errcode) && !"0".equals(errcode)){
					logger.error(jsonObject.toJSONString());
					logger.error(WeixinConst.ERR_MSG_WEIXIN_RESPONSE.get(errcode));
					throw new SystemException(WeixinConst.ERR_MSG_WEIXIN_INVOKE);
				}*/
			}
		} catch (ConnectException ce) {
			System.out.println("wechat connect is error."+ce);
			//logger.error("wechat connect is error.", ce);
		} catch (Exception e) {
			//logger.error("https request error.", e);
			System.out.println("https request error." +e);
		}
		return jsonObject;
	}
	
	/**
	 * 
	 * @description:
	 * @return
	 * @author:王涛
	 * @createTime:2018年6月1日 上午11:47:02
	 */
	public static String getUUID(){
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replaceAll("-", "");
		return uuid;
	}
	
	/**
	 * 
	 * @description:
	 * @return
	 * @author:王涛
	 * @createTime:2018年6月1日 上午11:45:58
	 */
	public final static String getProjectPath() {
		  String ProjectPath= WeChatHttpUtil.class.getClassLoader().getResource("").toString();
		  ProjectPath = replaceSlash(ProjectPath);
		  int i=0;
		  while (ProjectPath.indexOf("//")!=-1 && i<20){
		      ProjectPath = ProjectPath.replaceAll("\\Q//\\E", "/");
		      i++;
		  }
		  ProjectPath = ProjectPath.replaceAll("\\Qfile:\\E", "");
		  if(ProjectPath.indexOf(":")!=-1)
				 ProjectPath = ProjectPath.substring(1);
		  if(ProjectPath.indexOf("WEB-INF")!=-1)
	   	     ProjectPath = ProjectPath.substring(0,ProjectPath.indexOf("WEB-INF")-1);
	    return ProjectPath;  
	}
	/**
	 * 
	 * @description:
	 * @param str
	 * @return
	 * @author:王涛
	 * @createTime:2018年6月1日 上午11:46:14
	 */
	public static String replaceSlash(String str){
		  String temp="";
		  int n=0;
		  for(int i = 0; i < str.length(); i ++){ 
			    if(str.charAt(i) == '\\'){
			    	temp = temp + str.substring(n,i)+"/";
			    	n = i+1;
			    }
			}
		  if(n<str.length())
			  temp = temp + str.substring(n);
		  return temp;
	}
	/**
	 * 获取openid
	 * @param code
	 * @return
	 */
	public static String getOauthOpenId(String code){
		for(String key : WechatConstant.OPENID.keySet()) {
			if (code.equalsIgnoreCase(key)) {
				return WechatConstant.OPENID.get(key);
			}
		}
		StringBuilder url = new StringBuilder(WechatConstant.ACCESS_TOKEN_URL);
		url.append("?appid=").append(WechatConstant.APPID)
			.append("&secret=").append(WechatConstant.APPSECRET)
			.append("&code=").append(code)
			.append("&grant_type=").append(WechatConstant.GRANTTYPE);
		JSONObject response = WeChatHttpUtil.httpsRequest(url.toString(), WechatConstant.REQUEST_TYPE, null);
		String openId = response.getString(WechatConstant.OPENIDVALUE);
		WechatConstant.OPENID.put(code, openId);
		return openId;
	}
	
	/**
	 * 获取access_token
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return
	 */
	public static AccessToken getAccessToken(String appid, String appsecret) {
		AccessToken accessToken = null;

		String requestUrl = WechatConstant.ACCESS_TOKEN.replace("APPID", appid).replace(
				"APPSECRET", appsecret);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getIntValue("expires_in"));
			} catch (JSONException e) {
				accessToken = null;
				// 获取token失败
			}
		}
		return accessToken;
	}
	/**
	 * 
	 * @description:http 请求
	 * @param requestUrl
	 * @param requestMethod
	 * @param params
	 * @return
	 * @author:王涛
	 * @createTime:2018年6月1日 下午2:20:04
	 */
	public static JSONObject httpRequest(String requestUrl,
			String requestMethod, String params) {

		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();

		try {
			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url
					.openConnection();

			httpUrlConn.setDoInput(true);
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setUseCaches(false);
			httpUrlConn.setRequestMethod(requestMethod);
			if ("GET".equalsIgnoreCase(requestMethod)) {
				httpUrlConn.connect();
			}
			/** 当有数据提交时 */
			if (null != params) {
				OutputStream output = httpUrlConn.getOutputStream();
				/** 设置编码格式，防止中文乱码 */
				output.write(params.getBytes("UTF-8"));
				output.close();
			}
			
			String contentType = httpUrlConn.getHeaderField("Content-Type");
			if(contentType.indexOf("image") != -1){// 返回值类型是图片
				String oldFileName = httpUrlConn.getHeaderField("Content-disposition");
				String[] oldFileNameSplits = oldFileName.split("\\.");
				String fileType = oldFileNameSplits[oldFileNameSplits.length - 1];
				oldFileNameSplits = fileType.split("\\\"");
				fileType = oldFileNameSplits[0];
				String folderPath = getProjectPath() + "upload/images";
				String fileName = "WX_" + getUUID() + "." + fileType;
				
				File folder = new File(folderPath);
				// 判断文件目录是否存在,创建文件夹和文件
				if (!folder.exists()) {
					folder.mkdirs();
				}
				File file = new File(folderPath + "/" + fileName);
				BufferedInputStream bis = new BufferedInputStream(httpUrlConn.getInputStream());
				FileOutputStream fos = new FileOutputStream(file);
				byte[] bufferArray = new byte[1024];
				int len;
				while ((len = bis.read(bufferArray)) != -1) {
					fos.write(bufferArray, 0, len);
				}
				fos.close();
				bis.close();

				httpUrlConn.disconnect();
				
				jsonObject = new JSONObject();
				jsonObject.put("JSON_PARAM_NAME_IMAGE_PATH", "/upload/images/" + fileName);
			}else{// 返回值类型是文本
				/** 将返回的输入流转换成字符 */
				InputStream inputStream = httpUrlConn.getInputStream();
				InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8");
				BufferedReader bufferedReader = new BufferedReader(isr);
	
				String str = null;
				while ((str = bufferedReader.readLine()) != null) {
					buffer.append(str);
				}
				bufferedReader.close();
				isr.close();
				inputStream.close();
	
				inputStream = null;
				httpUrlConn.disconnect();
				jsonObject = JSONObject.parseObject(buffer.toString());
				
			}
		} catch (ConnectException ce) {
			//logger.error("wechat connect is error.", ce);
		} catch (Exception e) {
			//logger.error("http request error.", e);
		}
		return jsonObject;
	}
}
