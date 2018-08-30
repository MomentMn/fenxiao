 package com.hansan.fenxiao.filter;
 
import com.hansan.fenxiao.auto.entities.WxUser;
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

 /**
  * @description:
  *    用户登录的拦截器
  * @param null
  * @author:谭农春
  * @createTime: 2018/6/4 20:12
  */

 public class UserLoginFilter implements Filter
 {
   @Override
   public void destroy()
   {
   }
 
   @Override
   public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException
   {
	   //System.out.println("进来------------");
     HttpServletRequest request = (HttpServletRequest)req;
     HttpServletResponse response = (HttpServletResponse)res;
     HttpSession session = request.getSession();
    String url = request.getRequestURL().toString();
     if(url.startsWith("http://localhost:")) {
//       WxUser wu = new WxUser();
//       wu.setId(11);
//       wu.setUserId(17);
//       wu.setOpenId("oE6-90faAKA8elY8c9Tb6rF_i6qI");
//       wu.setWxName("WONG");
//       wu.setWxImageUrl("http://thirdwx.qlogo.cn/mmopen/hPn4wR0Inbg961tnPpG9xjKv4NG0cGickwJoqic5Tm3iccRe5NjO8bjOzlZGALP0HXeFnmTFtewm8B78WFSLcLO4A4evnt1HBXm/132");
//       wu.setIsAuth(true);
//       session.setAttribute("loginUser", wu);
     }
     chain.doFilter(request, response);
    // if (!"".equals(session.getAttribute("loginUser"))) {
      // loginUser = (User)session.getAttribute("loginUser");
    //	 wu=(WxUser) session.getAttribute("loginUser");
   //  }
     
//     if ((loginUser == null) || (loginUser.getId() == null)) {
//       response.sendRedirect(request.getContextPath() + "/login.jsp");
//     }
   //  if((null==wu) || (null==wu.getUserId())){
    //	 wu.setId(11);
    //	 wu.setUserId(17);
    //	 wu.setOpenId("oE6-90faAKA8elY8c9Tb6rF_i6qI");
    //	 wu.setWxName("WONG");
    //	 wu.setWxImageUrl("http://thirdwx.qlogo.cn/mmopen/hPn4wR0Inbg961tnPpG9xjKv4NG0cGickwJoqic5Tm3iccRe5NjO8bjOzlZGALP0HXeFnmTFtewm8B78WFSLcLO4A4evnt1HBXm/132");
    //	 wu.setIsAuth(true);
    //	 session.setAttribute("loginUser", wu);
    // }
//        chain.doFilter(request, response);
   }
 
   @Override
   public void init(FilterConfig arg0)
     throws ServletException
   {
   }
 }