package com.hansan.fenxiao.service;

import com.hansan.fenxiao.auto.entities.WxUser;
import com.hansan.fenxiao.entities.Commission;
import java.util.List;

public abstract interface ICommissionService<T extends Commission> extends IBaseService<T>
{
  public abstract List<Commission> getByUser(Integer paramInteger);

  //获取用户总支出
  Double getAllPayMoney(Integer loginUserId, String Ym, int type);

  //获取用户总收入
  Double getIncomeMoney(Integer loginUserId, String Ym, int type);

  /**
   * @description:  
   *     佣金
   * @param  loginUser
   *          用户id
   * @param path
   * @author:谭农春
   * @createTime: 2018/6/5 16:50 
   */
  Double wxPayfor(WxUser loginUser, Double amount, String path);
}