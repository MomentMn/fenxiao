package com.hansan.fenxiao.constants;

/**
 * @version 1.0
 * @description:
 *  是否授权
 * @projectName: com.hansan.fenxiao.constants
 * @className: fenxiao
 * @author:谭农春
 * @createTime:2018/5/31 20:46
 */
public enum AuthEnum {
  unAuth(false,"未授权"),
  authed(true,"已授权"),
  ;

  private boolean code = false;
  private String desc ;

  private AuthEnum(boolean code, String desc) {
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
