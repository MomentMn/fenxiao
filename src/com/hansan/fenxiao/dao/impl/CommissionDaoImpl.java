package com.hansan.fenxiao.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.hansan.fenxiao.dao.ICommissionDao;
import com.hansan.fenxiao.entities.Commission;

@Repository("commissionDao")
@Scope("prototype")
public class CommissionDaoImpl extends BaseDaoImpl<Commission>
  implements ICommissionDao
{

  @Resource(name="sessionFactory")
  private SessionFactory sessionFactory;

  private Session getSession()
  {
    return this.sessionFactory.getCurrentSession();
  }

  public List<Commission> getByUser(Integer userId)
  {
    String hql = "from Commission where user.id=:userId and deleted=0";

    List commissionList = getSession().createQuery(hql)
      .setInteger("userId", userId.intValue()).list();
    return commissionList;
  }

  @Override
  public Double getIncomeMoney(Integer loginUserId, String ym, int type) {
    String hql = "select sum(money) from Commission where user.id=:userId and createDate like :Ym and type=:type";
    //总收入
    Double incomeMoney = 0D;
    Query query = getSession().createQuery(hql);
    query.setInteger("userId",loginUserId).setString("Ym", ym + "%").setInteger("type",type);
    incomeMoney = (Double) query.uniqueResult();
    return incomeMoney;
  }

  @Override
  public Double getAllPayMoney(Integer loginUserId, String ym, int type) {
    //总支出
    Double allPayMoney = 0D;
    String hql = "select sum(money) from Commission where user.id=:userId and createDate like :Ym and type=:type";
    Query query = getSession().createQuery(hql);
    query.setInteger("userId",loginUserId).setString("Ym", ym + "%").setInteger("type",type);
    allPayMoney = (Double) query.uniqueResult();

    return allPayMoney;
  }
}
