package com.hansan.fenxiao.service.impl;/**
 * @description:
 * @author: shaopengxun
 * @createTime:2018/6/1 16:03
 **/

import com.hansan.fenxiao.dao.ISupervisorDao;
import com.hansan.fenxiao.entities.Supervisor;
import com.hansan.fenxiao.service.ISupervisorService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @description:
 * @author: shaopengxun
 * @createTime:2018/6/1 16:03
 **/
@Service("supervisorService")
@Scope("prototype")
public class SupervisorServiceImpl<T extends Supervisor> extends BaseServiceImpl<T> implements ISupervisorService<T> {

    @Resource(name="supervisorDao")
    private ISupervisorDao supervisorDao;

    @Override
    public Set<Supervisor> levelUserList(Integer id) {
        return supervisorDao.levelUserList(id);
    }

    @Override
    public Set<Supervisor> levelList(Integer userId, Integer type) {
        return supervisorDao.levelUserList(userId,type);
    }
}
