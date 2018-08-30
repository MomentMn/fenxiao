package com.hansan.fenxiao.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @class: Cities
 * @desc:
 * @author: huangxuejing
 * @date: 2018/6/4
 */
public class Cities implements Serializable {
    private String cityName;
    private String cityId;
    private List<Areas> areas;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public List<Areas> getAreas() {
        return areas;
    }

    public void setAreas(List<Areas> areas) {
        this.areas = areas;
    }
}
