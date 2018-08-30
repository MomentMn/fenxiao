package com.hansan.fenxiao.dao;

import com.hansan.fenxiao.entities.Supervisor;

import java.util.Set;

/**
 * @description:
 * @author: shaopengxun
 * @createTime:2018/6/1 15:57
 **/
public interface ISupervisorDao extends IBaseDao<Supervisor>{
    //根据用户id查询上下级
    Set<Supervisor> levelUserList(Integer id);

    Set<Supervisor> levelUserList(Integer userId, Integer type);
}



