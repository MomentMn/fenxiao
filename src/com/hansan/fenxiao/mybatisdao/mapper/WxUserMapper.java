package com.hansan.fenxiao.mybatisdao.mapper;

import com.hansan.fenxiao.auto.entities.WxUser;
import com.hansan.fenxiao.auto.entities.WxUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WxUserMapper {
    long countByExample(WxUserExample example);

    int deleteByExample(WxUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WxUser record);

    int insertSelective(WxUser record);

    List<WxUser> selectByExample(WxUserExample example);

    WxUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WxUser record, @Param("example") WxUserExample example);

    int updateByExample(@Param("record") WxUser record, @Param("example") WxUserExample example);

    int updateByPrimaryKeySelective(WxUser record);

    int updateByPrimaryKey(WxUser record);
}