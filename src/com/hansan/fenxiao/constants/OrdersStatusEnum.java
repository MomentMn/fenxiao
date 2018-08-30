package com.hansan.fenxiao.constants;

/**
 * @description:订单状态枚举
 * @projectName:fenxiao
 * @className:OrdersStatusEnum.java
 * @author:衷文涛
 * @createTime:2018年6月4日 下午2:14:14
 * @version 1.0
 */
public enum OrdersStatusEnum
{
	unpaid(0,"未付款"),
	already_paid(1,"已付款");
	
	private int id;
	private String name;
	
	private OrdersStatusEnum(int id,String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	};
	public String getName() {
		return name;
	}
}
