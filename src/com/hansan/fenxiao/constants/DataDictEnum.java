package com.hansan.fenxiao.constants;

/**
 * @version 1.0
 * @description:
 *   业务数据字典
 * @projectName: com.hansan.fenxiao.constants
 * @className: fenxiao
 * @author:谭农春
 * @createTime:2018/5/31 20:46
 */
public enum DataDictEnum {
  area("area","地区"),
  express("express","快递"),
  parentProvice("0000","上级省份"),
  parentCity("00","上级市"),
  ;

  private String code = "";
  private String desc ;

  private DataDictEnum(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  public String getCode() {
    return code;
  }


  public String getDesc() {
    return desc;
  }
}
