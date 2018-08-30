package com.hansan.fenxiao.dao.impl;

import com.hansan.fenxiao.dao.IBaseDao;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;

import com.hansan.fenxiao.utils.Page;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.*;
import org.hibernate.transform.Transformers;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository("baseDao")
@Scope("prototype")
public class BaseDaoImpl<T>
  implements IBaseDao<T>
{

  @Resource(name="sessionFactory")
  protected SessionFactory sessionFactory;
  Log log = LogFactory.getLog(AdminDaoImpl.class);

  private Session getSession() {
    return this.sessionFactory.getCurrentSession();
  }


  /**
   *  执行SQL
   * @param queryString
   * @param values
   * @return
   */
  protected SQLQuery createSqlQuery(String queryString, Object... values) {
    SQLQuery sqlQuery = getSession().createSQLQuery(queryString);
    if (values != null) {
      for (int i = 0; i < values.length; i++) {
        if (values[i] != null) {
          sqlQuery.setParameter(i, values[i]);
        }
      }
    }
    return sqlQuery;
  }

  /**
   * 执行SQL文
   * @param queryString
   * @param values
   *   eg:
   *   select id,parent_code  from data_dict where group_type=:exp
   *   map.put("exp","experss");
   * @return
   */
  protected SQLQuery createSqlQuery(String queryString, Map<String, ?> values) {
    SQLQuery sqlQuery = getSession().createSQLQuery(queryString);
    if (values != null) {
      sqlQuery.setProperties(values);
    }
    return sqlQuery;
  }

  /**
   * 根据查询HQL与参数列表创建Query对象.
   *
   * @param values
   *            命名参数,按名称绑定.
   */
  public Query createQuery(final String queryString,
                           final Map<String, ?> values) {
    Assert.hasText(queryString, "queryString不能为空");
    Query query = getSession().createQuery(queryString);
    if (values != null) {
      query.setProperties(values);
    }
    return query;
  }

  public Query createQuery(String queryString, Object... values)  {
    try {
      Query queryObject = this.getSession().createQuery(queryString);
      if (values != null) {
        for (int i = 0; i < values.length; i++) {
          if (values[i] != null) {
            queryObject.setParameter(i, values[i]);
          }
        }
      }
      return queryObject;
    } catch (HibernateException e) {
      throw new HibernateException(e);
    }
  }
  @Override
  public T findById(Class<T> clazz, int id)
  {
	return (T)getSession().get(clazz,id);
  }

  @Override
  public boolean saveOrUpdate(T baseBean)
  {
    try {
      getSession().saveOrUpdate(baseBean);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }return false;
  }

  @Override
  public boolean delete(T baseBean)
  {
    try
    {
      getSession().delete(baseBean);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }return false;
  }


  public List<T> list(String hql)
  {
    return getSession().createQuery(hql).list();
  }

  public List<T> list(String hql, int firstResult, int maxResult, Object[] params)
  {
    Query query = createQuery(hql);
    for (int i = 0; (params != null) && (i < params.length); i++) {
      query.setParameter(i + 1, params[i]);
    }

    List list = createQuery(hql).setFirstResult(firstResult).setMaxResults(maxResult).list();
    return list;
  }

  @Override
  public int getTotalCount(String hql, Object[] params)
  {
    Query query = createQuery(hql);
    for (int i = 0; (params != null) && (i < params.length); i++) {
      query.setParameter(i + 1, params[i]);
    }

    Object obj = query.uniqueResult();
    return ((Long)obj).intValue();
  }

  @Override
  public Query createQuery(String hql)
  {
    return getSession().createQuery(hql);
  }

  @Override
  public void deleteAll(String entity, String where)
  {
    Query query = createQuery("delete from " + entity + " where " + where);
    query.executeUpdate();
  }

  private String removeSelect(String hql) {
    int beginPos = hql.toLowerCase().indexOf("from");
    return hql.substring(beginPos);
  }

  private static String removeOrders(String hql) {
    Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", 2);
    Matcher m = p.matcher(hql);
    StringBuffer sb = new StringBuffer();
    while (m.find()) {
      m.appendReplacement(sb, "");
    }
    m.appendTail(sb);
		/*if(hql.trim().endsWith(")")){
			sb.append(")");
		}*/
    return sb.toString();
  }

  public void setFlushMode(FlushMode mode) {
    this.getSession().setFlushMode(mode);
  }

  public void flush()  {
    try {
      this.getSession().flush();
    } catch (HibernateException e) {
      throw new HibernateException(e);
    }
  }

  public <T> void merge(T entity)  {
    try {
      this.getSession().merge(entity);
    } catch (HibernateException e) {
      throw new HibernateException(e);
    }
  }

  public void clear()  {
    this.getSession().clear();
  }

  public <T> void evict(T entity)  {
    try {
      this.getSession().evict(entity);
    } catch (HibernateException e) {
      throw new HibernateException(e);
    }
  }

  public void refresh(Object entity)  {
    try {
      this.getSession().refresh(entity);
    } catch (HibernateException e) {
      throw new HibernateException(e);
    }
  }

  /**
   * 执行HQL进行批量修改/删除操作.
   *
   * @param values
   *            数量可变的参数,按顺序绑定.
   * @return 更新记录数.
   */
  public int batchExecute(final String hql, final Object... values) {
    return createQuery(hql, values).executeUpdate();
  }

  /**
   * 执行HQL进行批量修改/删除操作.
   *
   * @param values
   *            命名参数,按名称绑定.
   * @return 更新记录数.
   */
  public int batchExecute(final String hql, final Map<String, ?> values) {
    return createQuery(hql, values).executeUpdate();
  }

  /**
   * 执行SQL进行修改/删除操作.
   *
   * @param values
   *            数量可变的参数,按顺序绑定.
   * @return 更新记录数.
   */
  public int executeSql(final String sql, final Object... values) {
    return createSqlQuery(sql, values).executeUpdate();
  }

  /**
   * 执行SQL进行修改/删除操作.
   *
   * @param values
   *            命名参数,按名称绑定.
   * @return 更新记录数.
   */
  public int executeSql(final String sql, final Map<String, ?> values) {
    return createSqlQuery(sql, values).executeUpdate();
  }

  /**
   * 分页查询
   *
   * @param page
   * @param sql
   * @param values
   * @return
   */
  public Page<Map<String, Object>> findBySql(Page<Map<String, Object>> page,
                                             String sql, Map<String, Object> values) {

    try {
      String countQueryString = " select count(1) "
          + removeSelect(removeOrders(sql));

			/*if (page.isOrderBySetted()) {
				countQueryString += "order by " + page.getOrder();
			}*/

      if (page.isAutoCount()) {
        try {
          SQLQuery cq = createSqlQuery(countQueryString, values);
          Object o = cq.uniqueResult();
          Long count = 0l;
          if(o instanceof Long){
            count = (Long) o;
          }else if(o instanceof BigDecimal){
            BigDecimal b = (BigDecimal) o;
            count = b.longValue();
          }else if(o instanceof BigInteger){
            BigInteger b = (BigInteger) o;
            count = b.longValue();
          }
          page.setTotalCount(count.intValue());
        } catch (Exception e) {
          e.printStackTrace();
          page.setTotalCount(0);
        }
      }
      if (page.getPageNo() > page.getTotalPages()) {
        page.setPageNo(1);
      }

      SQLQuery q = createSqlQuery(sql, values);
      if (page.isFirstSetted()) {
        q.setFirstResult(page.getFirst());
      }
      if (page.isPageSizeSetted()) {
        q.setMaxResults(page.getPageSize());
      }
      List<Map<String, Object>> result = q.setResultTransformer(
          Transformers.ALIAS_TO_ENTITY_MAP).list();
      page.setResult(result);

      return page;
    } catch (HibernateException e) {
      throw new HibernateException(e);
    }
  }

  /**
   * 分页查询
   *
   * @param page
   * @param sql
   * @param values
   * @return
   */
  public Page<Map<String, Object>> findBySql(Page<Map<String, Object>> page, String sql, Object... values) {

    try {
      String countQueryString = " select count(1) "
          + removeSelect(removeOrders(sql));

      if (page.isAutoCount()) {
        try {
          SQLQuery cq = createSqlQuery(countQueryString, values);
          Object o = cq.uniqueResult();
          Long count = 0l;
          if(o instanceof Long){
            count = (Long) o;
          }else if(o instanceof BigDecimal){
            BigDecimal b = (BigDecimal) o;
            count = b.longValue();
          }else if(o instanceof BigInteger){
            BigInteger b = (BigInteger) o;
            count = b.longValue();
          }
          page.setTotalCount(count.intValue());
        } catch (Exception e) {
          e.printStackTrace();
          page.setTotalCount(0);
        }
      }
      if (page.getPageNo() > page.getTotalPages()) {
        page.setPageNo(1);
      }

      SQLQuery q = createSqlQuery(sql, values);
      if (page.isFirstSetted()) {
        q.setFirstResult(page.getFirst());
      }
      if (page.isPageSizeSetted()) {
        q.setMaxResults(page.getPageSize());
      }
      List<Map<String, Object>> result = q.setResultTransformer(
          Transformers.ALIAS_TO_ENTITY_MAP).list();
      page.setResult(result);
      return page;
    } catch (HibernateException e) {
      throw new HibernateException(e);
    }
  }

  /**
   * 分页查询(计划专用)
   *
   * @param page
   * @param sql
   * @param values
   * @return
   */
  public Page<Map<String, Object>> findPlanBySql(Page<Map<String, Object>> page, String sql, Object... values) {

    try {
      String countQueryString = " select count(1) "
          + removePlanSelect(removeOrders(sql));

      if (page.isAutoCount()) {
        try {
          SQLQuery cq = createSqlQuery(countQueryString, values);
          Object o = cq.uniqueResult();
          Long count = 0l;
          if(o instanceof Long){
            count = (Long) o;
          }else if(o instanceof BigDecimal){
            BigDecimal b = (BigDecimal) o;
            count = b.longValue();
          }else if(o instanceof BigInteger){
            BigInteger b = (BigInteger) o;
            count = b.longValue();
          }
          page.setTotalCount(count.intValue());
        } catch (Exception e) {
          e.printStackTrace();
          page.setTotalCount(0);
        }
      }
      if (page.getPageNo() > page.getTotalPages()) {
        page.setPageNo(1);
      }

      SQLQuery q = createSqlQuery(sql, values);
      if (page.isFirstSetted()) {
        q.setFirstResult(page.getFirst());
      }
      if (page.isPageSizeSetted()) {
        q.setMaxResults(page.getPageSize());
      }
      List<Map<String, Object>> result = q.setResultTransformer(
          Transformers.ALIAS_TO_ENTITY_MAP).list();
      page.setResult(result);
      return page;
    } catch (HibernateException e) {
      throw new HibernateException(e);
    }
  }

  private String removePlanSelect(String hql) {
    return " FROM ( " + hql + ")";
  }

  @SuppressWarnings("unchecked")
  public <T> Page<T> find(Page<T> page, String hql, Object... values)
       {
    try {
      String countQueryString = " select count(*) "
          + removeSelect(removeOrders(hql));

      if (page.isAutoCount()) {
        try {
          Query cq = createQuery(countQueryString, values);
          Object o = cq.uniqueResult();
          Long count = 0l;
          if(o instanceof Long){
            count = (Long) o;
          }else if(o instanceof BigDecimal){
            BigDecimal b = (BigDecimal) o;
            count = b.longValue();
          }else if(o instanceof BigInteger){
            BigInteger b = (BigInteger) o;
            count = b.longValue();
          }
          page.setTotalCount(count.intValue());
        } catch (Exception e) {
          page.setTotalCount(0);
        }
      }
      if (page.getPageNo() > page.getTotalPages()) {
        page.setPageNo(1);
      }

      Query q = createQuery(hql, values);
      if (page.isFirstSetted()) {
        q.setFirstResult(page.getFirst());
      }
      if (page.isPageSizeSetted()) {
        q.setMaxResults(page.getPageSize());
      }
      List<T> result = q.list();
      page.setResult(result);
      return page;
    } catch (HibernateException e) {
      throw new HibernateException(e);
    }
  }

  public <T> Page<T> find(Page<T> page, String hql, Map<String, ?> values)
       {
    try {
      String countQueryString = " select count(*) "
          + removeSelect(removeOrders(hql));

      if (page.isAutoCount()) {
        try {
          Query cq = createQuery(countQueryString, values);
          Object o = cq.uniqueResult();
          Long count = 0l;
          if(o instanceof Long){
            count = (Long) o;
          }else if(o instanceof BigDecimal){
            BigDecimal b = (BigDecimal) o;
            count = b.longValue();
          }else if(o instanceof BigInteger){
            BigInteger b = (BigInteger) o;
            count = b.longValue();
          }
          page.setTotalCount(count.intValue());
        } catch (Exception e) {
          page.setTotalCount(0);
        }
      }
      if (page.getPageNo() > page.getTotalPages()) {
        page.setPageNo(1);
      }

      Query q = createQuery(hql, values);
      if (page.isFirstSetted()) {
        q.setFirstResult(page.getFirst());
      }
      if (page.isPageSizeSetted()) {
        q.setMaxResults(page.getPageSize());
      }
      List<T> result = q.list();
      page.setResult(result);
      return page;
    } catch (HibernateException e) {
      throw new HibernateException(e);
    }
  }
}