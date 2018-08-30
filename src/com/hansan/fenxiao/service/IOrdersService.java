package com.hansan.fenxiao.service;

import com.hansan.fenxiao.entities.Orders;

public abstract interface IOrdersService<T extends Orders> extends IBaseService<T>
{
  public abstract Orders findByNo(String paramString);
  
  /**
   * @description:订单付款完成，调整订单状态，计算佣金，生成二维码。
   * @param orderId 订单id
   * @author:衷文涛
   * @createTime:2018年6月4日 上午11:16:34
   */
  public abstract void updateOrder(Integer orderId,Integer userId) throws Exception;
}