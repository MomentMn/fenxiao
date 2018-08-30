package com.hansan.fenxiao.dao.impl;/**
 * @description:
 * @author: shaopengxun
 * @createTime:2018/6/1 15:59
 **/

import com.hansan.fenxiao.dao.ISupervisorDao;
import com.hansan.fenxiao.entities.Supervisor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @description:
 * @author: shaopengxun
 * @createTime:2018/6/1 15:59
 **/
@Repository("supervisorDao")
@Scope("prototype")
public class SupervisorDaoImpl extends BaseDaoImpl<Supervisor> implements ISupervisorDao {
    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    private Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public Set<Supervisor> levelUserList(Integer userId) {
        Set<Supervisor> supervisorSet = new HashSet<>();
        String hql = "from Supervisor where user_id = ? and deleted = ?";
        Query query = getSession().createQuery(hql);
        query.setParameter(0, userId);
        query.setParameter(1, false);
        List levelUserList = query.list();
        supervisorSet.addAll(levelUserList);
        return supervisorSet;
    }

    @Override
    public Set<Supervisor> levelUserList(Integer userId, Integer type) {
        Set<Supervisor> supervisorSet = new HashSet<>();
        //查询所有
        if (type==0){
            Set<Supervisor> supervisors = this.levelUserList(userId);
            return supervisors;
        }
        String hql = "from Supervisor where user_id = ? and deleted = ? and level = ? ";
        Query query = getSession().createQuery(hql);
        query.setParameter(0, userId);
        query.setParameter(1, false);
        query.setParameter(2, type);
        List levelUserList = query.list();
        supervisorSet.addAll(levelUserList);
        return supervisorSet;
    }
}
