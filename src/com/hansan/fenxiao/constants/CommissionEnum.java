package com.hansan.fenxiao.constants;

/**
 * @version 1.0
 * @description:
 *   佣金明细表
 * @projectName: com.hansan.fenxiao.constants
 * @className: fenxiao
 * @author:谭农春
 * @createTime:2018/6/5 17:12
 */
public enum CommissionEnum {
  output(0,"扣款"),
  input(1,"入款"),
  ;

  private int code = 0;
  private String desc ;

  private CommissionEnum(int code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  public int getCode() {
    return code;
  }


  public String getDesc() {
    return desc;
  }
}
