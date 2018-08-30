package com.hansan.fenxiao.enums;

/**
 * @description:
 * @author: shaopengxun
 * @createTime:2018/6/1 14:53
 **/
public enum CommissionTypeEnum {
    PAY(0, "扣款"),
    INCOME(1, "入款");

    final private int id;
    final private String name;

    CommissionTypeEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
