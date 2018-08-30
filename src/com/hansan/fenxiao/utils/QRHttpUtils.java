package com.hansan.fenxiao.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

import com.hansan.fenxiao.constant.WechatConstant;

/**
 * 
 * @description:微信二维码生成工具
 * @author:王涛
 * @createTime:2018年6月4日 上午11:38:46
 * @version 1.0
 */
public class QRHttpUtils {
	public static String HttpsDefaultExecute(String method, String path, Map<String, String> map, String data) {  
        String result = "";  
        try {  
            String url = setParmas((TreeMap<String, String>) map, path, "utf-8");  
            result = defaultConnection(method, url, WechatConstant.DEFAULT_CONNTIME, WechatConstant.DEFAULT_READTIME, data);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return result;  
    }  
	
	  /** 
     * 设置参数 
     *  
     * @param map 
     *            参数map 
     * @param path 
     *            需要赋值的path 
     * @param charset 
     *            编码格式 默认编码为utf-8(取消默认) 
     * @return 已经赋值好的url 只需要访问即可 
     */  
    public static String setParmas(Map<String, String> map, String path, String charset) throws Exception {  
        String result = "";  
        boolean hasParams = false;  
        if (path != null && !"".equals(path)) {  
            if (map != null && map.size() > 0) {  
                StringBuilder builder = new StringBuilder();  
                Set<Entry<String, String>> params = map.entrySet();  
                for (Entry<String, String> entry : params) {  
                    String key = entry.getKey().trim();  
                    String value = entry.getValue().trim();  
                    if (hasParams) {  
                        builder.append("&");  
                    } else {  
                        hasParams = true;  
                    }  
                    if(charset != null && !"".equals(charset)){  
                        //builder.append(key).append("=").append(URLDecoder.(value, charset));    
                        builder.append(key).append("=").append(urlEncode(value, charset));  
                    }else{  
                        builder.append(key).append("=").append(value);  
                    }  
                }  
                result = builder.toString();  
            }  
        }  
        return doUrlPath(path, result).toString();  
    }  
  
    /** 
     * 设置连接参数 
     *  
     * @param path 
     *            路径 
     * @return 
     */  
    private static URL doUrlPath(String path, String query) throws Exception {  
        URL url = new URL(path);  
        if (org.apache.commons.lang.StringUtils.isEmpty(path)) {  
            return url;  
        }  
        if (org.apache.commons.lang.StringUtils.isEmpty(url.getQuery())) {  
            if (path.endsWith("?")) {  
                path += query;  
            } else {  
                path = path + "?" + query;  
            }  
        } else {  
            if (path.endsWith("&")) {  
                path += query;  
            } else {  
                path = path + "&" + query;  
            }  
        }  
        return new URL(path);  
    }
    
    public static String urlEncode(String source, String encode) {  
        String result = source;  
        try {  
            result = URLEncoder.encode(source, encode);  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        return result;  
    }  
    
    
    /** 
     * http请求 
     *  
     * @param method 
     *            请求方法GET/POST 
     * @param path 
     *            请求路径 
     * @param timeout 
     *            连接超时时间 默认为5000 
     * @param readTimeout 
     *            读取超时时间 默认为5000 
     * @param data  数据 
     * @return 
     */  
    public static String defaultConnection(String method, String path, int timeout, int readTimeout, String data)  
            throws Exception {  
        String result = "";  
        URL url = new URL(path);  
        if (url != null) {  
            HttpURLConnection conn = getConnection(method, url);  
            conn.setConnectTimeout(timeout == 0 ? WechatConstant.DEFAULT_CONNTIME : timeout);  
            conn.setReadTimeout(readTimeout == 0 ? WechatConstant.DEFAULT_READTIME : readTimeout);  
            if (data != null && !"".equals(data)) {  
                OutputStream output = conn.getOutputStream();  
                output.write(data.getBytes(WechatConstant.DEFAULT_CHARSET));  
                output.flush();  
                output.close();  
            }  
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {  
                InputStream input = conn.getInputStream();  
                result = inputToStr(input);  
                input.close();  
                conn.disconnect();  
            }  
        }  
        return result;  
    }  
    

    /** 
     * 根据url的协议选择对应的请求方式 例如 http://www.baidu.com 则使用http请求,https://www.baidu.com 
     * 则使用https请求 
     *  
     * @param method 
     *            请求的方法 
     * @return 
     * @throws IOException 
     */  
    private static HttpURLConnection getConnection(String method, URL url) throws IOException {  
        HttpURLConnection conn = null;  
        if ("https".equals(url.getProtocol())) {  
            SSLContext context = null;  
            try {  
                context = SSLContext.getInstance("SSL", "SunJSSE");  
                context.init(new KeyManager[0], new TrustManager[] { new MyX509TrustManager() },  
                        new java.security.SecureRandom());  
            } catch (Exception e) {  
                throw new IOException(e);  
            }  
            HttpsURLConnection connHttps = (HttpsURLConnection) url.openConnection();  
            connHttps.setSSLSocketFactory(context.getSocketFactory());  
            connHttps.setHostnameVerifier(new HostnameVerifier() {  
				@Override
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}  
            });  
            conn = connHttps;  
        } else {  
            conn = (HttpURLConnection) url.openConnection();  
        }  
        conn.setRequestMethod(method);  
        conn.setUseCaches(false);  
        conn.setDoInput(true);  
        conn.setDoOutput(true);  
        return conn;  
    }  
    /** 
     * 将输入流转换为字符串 
     *  
     * @param input 
     *            输入流 
     * @return 转换后的字符串 
     */  
    public static String inputToStr(InputStream input) {  
        String result = "";  
        if (input != null) {  
            byte[] array = new byte[1024];  
            StringBuffer buffer = new StringBuffer();  
            try {  
                for (int index; (index = (input.read(array))) > -1;) {  
                    buffer.append(new String(array, 0, index, WechatConstant.DEFAULT_CHARSET));  
                }  
                result = buffer.toString();  
            } catch (IOException e) {  
                e.printStackTrace();  
                result = "";  
            }  
        }  
        return result;  
    }  
    
    /** 
     * 获取授权token 
     *  
     * @param key 应用key 
     * @param secret 应用密匙 
     * @return json格式的字符串 
     */  
    public static String getAccessToken(String key, String secret) {  
        TreeMap<String, String> map = new TreeMap<String, String>();  
        map.put("grant_type", "client_credential");  
        map.put("appid", key);  
        map.put("secret", secret);  
        String result = HttpDefaultExecute(WechatConstant.REQUEST_TYPE, WechatConstant.TOKEN_PATH, map, "");  
        return result;  
    }
    
    /** 
     * 默认的http请求执行方法,返回 
     *  
     * @param method 
     *            请求的方法 POST/GET 
     * @param path 
     *            请求path 路径 
     * @param map 
     *            请求参数集合 
     * @param data 
     *            输入的数据 允许为空 
     * @return 
     */  
    public static String HttpDefaultExecute(String method, String path, Map<String, String> map, String data) {  
        String result = "";  
        try {  
            String url = setParmas((TreeMap<String, String>) map, path, "");  
            result = defaultConnection(method, url, WechatConstant.DEFAULT_CONNTIME, WechatConstant.DEFAULT_READTIME, data);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return result;  
    }  
}
