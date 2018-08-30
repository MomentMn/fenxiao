package com.hansan.fenxiao.mybatisdao;

import com.hansan.fenxiao.entities.DataDict;

import java.util.List;

/**
 * @version 1.0
 * @description:
 * @projectName: com.hansan.fenxiao.dao.mybatis
 * @className: fenxiao
 * @author:谭农春
 * @createTime:2018/6/2 11:45
 */
public interface IDataDictDAO {
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
   */
  public List<DataDict> listArea(String parentCode)  throws RuntimeException;

}
