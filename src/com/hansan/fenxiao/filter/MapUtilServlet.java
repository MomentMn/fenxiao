package com.hansan.fenxiao.filter;

import java.util.List;

import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hansan.fenxiao.auto.entities.WxUser;
import com.hansan.fenxiao.service.IUserService2;
import com.hansan.fenxiao.utils.MapUtil;
import com.hansan.fenxiao.utils.SpringContextUtil;

public class MapUtilServlet extends HttpServlet {

  private Logger logger = LoggerFactory.getLogger(MapUtilServlet.class);
	private static final long serialVersionUID = 1L;
	private IUserService2 userService2;
    @Override
		public void init(){
      try {
          logger.info("缓存数据加载中.....");
          if (userService2 == null) {
            userService2 = (IUserService2) SpringContextUtil.getBean("userService2");
          }
          // 加载缓存数据
          List<WxUser> userList = userService2.loadCacheData();
          logger.info("数据加载中..... nums is  " + userList.size());
          if (null != userList && !userList.isEmpty()) {
            for (WxUser wx : userList) {
              MapUtil.add(wx.getOpenId(), wx);
            }

          }
      }catch (Exception e){
        e.printStackTrace();
      }
    }
}
