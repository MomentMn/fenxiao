 package com.hansan.fenxiao.filter;
 
 import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hansan.fenxiao.auto.entities.WxUser;
 
 public class SetcharFilter
   implements Filter
 {
   public void destroy()
   {
   }
 
   public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
     throws IOException, ServletException
   {
     HttpServletRequest request = (HttpServletRequest)req;
     HttpServletResponse response = (HttpServletResponse)res;
     request.setCharacterEncoding("UTF-8");
     response.setCharacterEncoding("UTF-8");
     response.setContentType("text/html");
     HttpSession session = request.getSession();
     String url = request.getRequestURL().toString();
     if(url.startsWith("http://localhost:")) {
            WxUser wu = new WxUser();
            wu.setId(19);
       	 wu.setUserId(25);
       	 wu.setOpenId("oE6-90faAKA8elY8c9Tb6rF_i6qI");
       	 wu.setWxName("WONG");
       	 wu.setWxImageUrl("http://thirdwx.qlogo.cn/mmopen/hPn4wR0Inbg961tnPpG9xjKv4NG0cGickwJoqic5Tm3iccRe5NjO8bjOzlZGALP0HXeFnmTFtewm8B78WFSLcLO4A4evnt1HBXm/132");
       	 wu.setIsAuth(true);
       	 session.setAttribute("loginUser", wu);
     }
	 
     chain.doFilter(request, response);
   }
 
   public void init(FilterConfig filterConfig)
     throws ServletException
   {
   }
 }