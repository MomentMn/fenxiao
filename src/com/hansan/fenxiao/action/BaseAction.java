 package com.hansan.fenxiao.action;
 
 import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hansan.fenxiao.entities.DataDict;
import com.hansan.fenxiao.utils.BjuiPage;
import com.opensymphony.xwork2.ActionSupport;

import freemarker.template.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;

 import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

 public class BaseAction extends ActionSupport
   implements ServletRequestAware, ServletResponseAware
 {
   protected Logger logger = LoggerFactory.getLogger(BaseAction.class);
   private static final long serialVersionUID = 1L;
   protected HttpServletRequest request;
   protected HttpServletResponse response;
   protected Configuration cfg;
   protected int pageCurrent = 1;
 
   protected int pageSize = 10;
   protected BjuiPage page;
   protected String ftlFileName;
   Log log = LogFactory.getLog(BaseAction.class);
 
   public void setServletResponse(HttpServletResponse arg0)
   {
     this.response = arg0;
   }
 
   public void setServletRequest(HttpServletRequest arg0)
   {
     this.request = arg0;
   }
 
   public void sendMsg(String json) throws UnsupportedEncodingException, IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setHeader("P3P","CP=\"NON DSP COR CURa ADMa DEVa TAIa PSAa PSDa IVAa IVDa CONa HISa TELa OTPa OUR UNRa IND UNI COM NAV INT DEM CNT PRE LOC\"");
		response.setHeader("Access-Control-Allow-Origin","*");
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getOutputStream().write(json.getBytes("utf-8"));
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
   
   public void setPage(BjuiPage page) {
     this.page = page;
   }
   public BjuiPage getPage() {
     return this.page;
   }
 
   public int getPageCurrent() {
     return this.pageCurrent;
   }
 
   public void setPageCurrent(int pageCurrent) {
     this.pageCurrent = pageCurrent;
   }
 
   public int getPageSize() {
     return this.pageSize;
   }
 
   public void setPageSize(int pageSize) {
     this.pageSize = pageSize;
   }
 
   public String getFtlFileName() {
     return this.ftlFileName;
   }
 
   public void setFtlFileName(String ftlFileName) {
     this.ftlFileName = ftlFileName;
   }

   /**
    * 以json 的是形式返回到前端
    * @param list
    */
   protected void renderJson(List list) {
       JSONArray listJson = (JSONArray)JSONArray.toJSON(list);
       // 返回到前端
       PrintWriter out = null;
       try {
         out = this.response.getWriter();
       } catch (IOException e1) {
         e1.printStackTrace();
       }
       out.print(listJson);
       out.flush();
       out.close();
   }

   /**
    *  将Mapper ，Object对象转成JSON字符串
    * @param object
    */
   protected  void renderJson(Object object){
     String jsonString = JSON.toJSONString(object);
     // 返回到前端
     PrintWriter out = null;
     try {
       out = this.response.getWriter();
     } catch (IOException e1) {
       e1.printStackTrace();
     }
     out.print(jsonString);
     out.flush();
     out.close();
   }

 }
