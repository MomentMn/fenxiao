package com.hansan.fenxiao.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @version 1.0
 * @description:
 * @projectName: com.hansan.fenxiao.entities
 * @className: fenxiao
 * @author:谭农春
 * @createTime:2018/5/31 20:29
 */
@Entity
@Table(name = "user_address")
public class UserAddress extends BaseBean{

  @Column(name = "name")
  private String name;
  @Column(name = "phone")
  private String phone;

  @Column(name = "user_id")
  private Integer userId;

  @Column(name = "city_code")
  private String cityCode;
  @Basic
  @Column(name = "city_full_name")
  private String cityFullName;
  @Column(name = "details")
  private String details;

  @Basic
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Basic
  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  @Basic
  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  @Basic
  public String getCityCode() {
    return cityCode;
  }

  public void setCityCode(String cityCode) {
    this.cityCode = cityCode;
  }


  public String getCityFullName() {
    return cityFullName;
  }

  public void setCityFullName(String cityFullName) {
    this.cityFullName = cityFullName;
  }

  @Basic
  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

}
