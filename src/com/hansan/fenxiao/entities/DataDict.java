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
@Table(name = "data_dict")
public class DataDict  extends BaseBean{

  private String name;
  private String fullName;
  private String code;
  private String parentCode;
  private String groupType;


  @Basic
  @Column(name = "name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Basic
  @Column(name = "full_name")
  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  @Basic
  @Column(name = "code")
  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  @Basic
  @Column(name = "parent_code")
  public String getParentCode() {
    return parentCode;
  }

  public void setParentCode(String parentCode) {
    this.parentCode = parentCode;
  }

  @Basic
  @Column(name = "group_type")
  public String getGroupType() {
    return groupType;
  }

  public void setGroupType(String groupType) {
    this.groupType = groupType;
  }




}
