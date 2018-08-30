package com.hansan.fenxiao.utils;

import com.hansan.fenxiao.auto.entities.WxUser;

import java.util.HashMap;
import java.util.Map;

public class MapUtil {

	private static Map<String,WxUser> maps=new HashMap<String,WxUser>(32);
	/**
	 * 
	 * @description:增加该key
	 * @param key
	 * @param value
	 * @author:王涛
	 * @createTime:2018年6月5日 下午4:58:52
	 */
	public static void add(String key,WxUser value){
		maps.put(key, value);
	}
	
	/**
	 * 
	 * @description:根据key删除
	 * @param key
	 * @author:王涛
	 * @createTime:2018年6月5日 下午4:59:19
	 */
	public static void del(String key){
		maps.remove(key);
	}
	/**
	 * 
	 * @description:maps 根据key获取微信对象
	 * @param key
	 * @return
	 * @author:王涛
	 * @createTime:2018年6月6日 上午10:03:32
	 */
	public static WxUser get(String key){
		return maps.get(key);
	}
	/**
	 * 
	 * @description: 判断是否存在该值
	 * @param key
	 * @return
	 * @author:王涛
	 * @createTime:2018年6月5日 下午4:58:35
	 */
	public static boolean isExist(String key){
		if(null==maps.get(key)){
			return false;
		}else{
			return true;
		}
	}
}
