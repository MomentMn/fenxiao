package com.hansan.fenxiao.mybatisservice;

import com.hansan.fenxiao.entities.DataDict;

import java.util.List;

/**
 * @version 1.0
 * @description:
 *     数据字典服务
 * @projectName: com.hansan.fenxiao.mybatisservice.impl
 * @className: fenxiao
 * @author:谭农春
 * @createTime:2018/6/2 17:01
 */
public interface IDataDictService {
  /**
   *  快递列表
   * @return
   * @throws RuntimeException
   */
  public List<DataDict> listExpress() throws RuntimeException;

  /**
   * 上级地区
   * @param parentCode
   * @return
   * @throws RuntimeException
   */
  public List<DataDict> listArea(String parentCode) throws RuntimeException;
}
