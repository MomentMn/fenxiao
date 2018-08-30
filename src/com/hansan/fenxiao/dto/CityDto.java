package com.hansan.fenxiao.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @class: CityDto
 * @desc:
 * @author: huangxuejing
 * @date: 2018/6/4
 */
public class CityDto implements Serializable {

    private String provinceName;
    private String provinceId;
    private List<Cities> cities;

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public List<Cities> getCities() {
        return cities;
    }

    public void setCities(List<Cities> cities) {
        this.cities = cities;
    }
}
