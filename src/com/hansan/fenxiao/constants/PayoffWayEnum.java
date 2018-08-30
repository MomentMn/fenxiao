package com.hansan.fenxiao.constants;

/**
 * @class: PayoffWayEnum
 * @desc:
 * @author: huangxuejing
 * @date: 2018/6/5
 */
public enum PayoffWayEnum {
    wechat(0, "微信"),
    alipay(1, "支付宝"),
    unionpay(2, "银联支付")
    ;

    private int code;
    private String desc;

    private PayoffWayEnum(int code, String desc)
    {
        this.code = code;
        this.desc = desc;
    }

    public int getCode()
    {
        return code;
    }

    public String getDesc()
    {
        return desc;
    }
}
