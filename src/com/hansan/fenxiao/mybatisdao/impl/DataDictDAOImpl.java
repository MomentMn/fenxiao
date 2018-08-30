package com.hansan.fenxiao.mybatisdao.impl;

import com.hansan.fenxiao.constants.DataDictEnum;
import com.hansan.fenxiao.entities.DataDict;
import com.hansan.fenxiao.entities.DataDictExample;
import com.hansan.fenxiao.mybatisdao.IDataDictDAO;
import com.hansan.fenxiao.mybatisdao.mapper.DataDictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version 1.0
 * @description:
 * @projectName: com.hansan.fenxiao.mybatisdao.impl
 * @className: fenxiao
 * @author:谭农春
 * @createTime:2018/6/2 11:58
 */
@Service
public class DataDictDAOImpl implements IDataDictDAO {

  @Autowired
  private DataDictMapper dataDictMapper;
  /**
   * 分组列表
   *
   * @return
   * @throws RuntimeException
   */
  @Override
  public List<DataDict> listExpress() throws RuntimeException {
    DataDictExample example = new DataDictExample();
    // 获取快递数据
    example.createCriteria().andGroupTypeEqualTo(DataDictEnum.express.getCode());
    // 查询快递的数据
    return dataDictMapper.selectByExample(example);
  }

  /**
   * 上级地区
   *
   * @param parentCode
   *
   * @return
   */
  @Override
  public List<DataDict> listArea(String parentCode) throws RuntimeException {

    DataDictExample example = new DataDictExample();
    DataDictExample.Criteria  criteria = example.createCriteria();
    // 获取地区数据
    criteria.andGroupTypeEqualTo(DataDictEnum.area.getCode());
    // 上级省份
    criteria.andParentCodeEqualTo(parentCode);
    return dataDictMapper.selectByExample(example);
  }
}
