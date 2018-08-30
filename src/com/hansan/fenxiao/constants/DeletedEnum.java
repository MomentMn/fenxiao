package com.hansan.fenxiao.constants;

/**
 * @version 1.0
 * @description:
 *      删除状态
 * @projectName: com.hansan.fenxiao.constants
 * @className: fenxiao
 * @author:谭农春
 * @createTime:2018/5/31 20:38
 */
public enum DeletedEnum {
  normal(false,"正常"),
  deleted(true,"删除"),
  ;

  private boolean code = false;
  private String desc ;

  private DeletedEnum(boolean code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  public boolean getCode() {
    return code;
  }


  public String getDesc() {
    return desc;
  }


}
