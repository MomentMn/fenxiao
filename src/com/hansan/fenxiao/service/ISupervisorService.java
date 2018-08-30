package com.hansan.fenxiao.service;

import com.hansan.fenxiao.entities.Supervisor;

import java.util.Set;

/**
 * @description:
 * @author: shaopengxun
 * @createTime:2018/6/1 16:02
 **/
public abstract interface ISupervisorService <T extends Supervisor> extends IBaseService<T>{
    //根据用户id查询上下级
    Set<Supervisor> levelUserList(Integer id);
    //根据用户id,级别 查询上下级
    Set<Supervisor> levelList(Integer userId, Integer type);
}
