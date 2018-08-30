package com.hansan.fenxiao.enums;

/**
 * @description:
 * @author: shaopengxun
 * @createTime:2018/6/1 14:53
 **/
public enum DeletedEnum {
    UN_DELETED(0, "未删"),
    DELETED(1, "删除");

    final private int id;
    final private String name;

    DeletedEnum(int id, String name) {
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
