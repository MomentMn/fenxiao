package com.hansan.fenxiao.mybatisservice.impl;

import com.hansan.fenxiao.entities.DataDict;
import com.hansan.fenxiao.mybatisdao.IDataDictDAO;
import com.hansan.fenxiao.mybatisservice.IDataDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version 1.0
 * @description:
 *       数据字典服务
 * @projectName: com.hansan.fenxiao.mybatisservice.impl
 * @className: fenxiao
 * @author:谭农春
 * @createTime:2018/6/2 17:02
 */
@Service
public class DataDictServiceImpl  implements IDataDictService {

  @Autowired
  private IDataDictDAO dataDictDAO;
  /**
   * 快递列表
   *
   * @return
   *
   * @throws RuntimeException
   */
  @Override
  public List<DataDict> listExpress() throws RuntimeException {
    return dataDictDAO.listExpress();
  }

  /**
   * 上级地区
   *
   * @param parentCode
   *
   * @return
   *
   * @throws RuntimeException
   */
  @Override
  public List<DataDict> listArea(String parentCode) throws RuntimeException {
    return dataDictDAO.listArea(parentCode);
  }
}
