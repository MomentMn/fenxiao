package com.hansan.fenxiao.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ReadXmlTools {
	public static Map<String, String> readXml(HttpServletRequest request) throws IOException{
		/*
		 * 该部分我们获取用户发送的信息，并且解析成<K,V>的形式进行显示
		 */
		// 解析用户发送过来的信息
		InputStream is = request.getInputStream();// 拿取请求流
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();
		// 解析xml，将获取到的返回结果xml进行解析成我们习惯的文字信息
		SAXReader reader = new SAXReader();// 第三方jar:dom4j【百度：saxreader解析xml】
		Document document = null;
		try {
		 document = reader.read(is);
		} catch (DocumentException e1) {
		 e1.printStackTrace();
		}
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementList = root.elements();
		// 遍历所有子节点
		for (Element e : elementList) {
			map.put(e.getName(), e.getText());
		}
	  return  map;
	}
}
