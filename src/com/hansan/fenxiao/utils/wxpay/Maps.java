package com.hansan.fenxiao.utils.wxpay;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class Maps
{
	public static SortedMap<String,Object> newTreeMap() {
		return new TreeMap<>();
	}
	
	public static Map<String,Object> newHashMap(){
		return new HashMap<>();
	}
}
