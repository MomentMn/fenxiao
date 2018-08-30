package com.hansan.fenxiao.dto;

import java.io.Serializable;

/**
 * @class: Areas
 * @desc:
 * @author: huangxuejing
 * @date: 2018/6/4
 */
public class Areas implements Serializable {
    private String areaName;
    private String areaId;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }
}
