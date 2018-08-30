package com.hansan.fenxiao.mybatisdao;

import com.hansan.fenxiao.auto.entities.User;
import com.hansan.fenxiao.auto.entities.WxUser;

import java.util.List;

public interface IUserDao2 {
	/**
	 * 
	 * @description:user入库
	 * @param user
	 * @return
	 * @author:王涛
	 * @createTime:2018年6月5日 下午4:37:22
	 */
	void insertUser(User user);
	void insertWxUser(WxUser wu);
/**
 * @description:
 *
 * @param
 *     --  加载缓存数据
 * @author:谭农春
 * @createTime: 2018/6/5 19:37
 */

  public List<WxUser> loadCacheData();
  /**
   * 
   * @description:更新微信对象里面的二维码地址
   * @param wu
   * @author:王涛
   * @createTime:2018年6月6日 下午3:54:23
   */
  int updateWxUser(WxUser wu);
}
