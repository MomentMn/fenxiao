package com.hansan.fenxiao.enums;

/**
 * @description:
 * @author: shaopengxun
 * @createTime:2018/6/1 14:53
 **/
public enum LevelEnum {
    FIRST_LEVEL(1, "一级"),
    SECOND_LEVEL(2, "二级"),
    THIRD_LEVEL(3, "三级");

    final private int id;
    final private String name;

    LevelEnum(int id, String name) {
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
