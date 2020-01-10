package com.test.testdemo.base.servcie;

import com.test.testdemo.base.entity.BaseEntity;
import com.test.testdemo.base.repository.CommonRepository;
import com.test.testdemo.base.util.BaseCriteria;
import com.test.testdemo.base.util.PageBean;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.hibernate.transform.Transformers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Repository;

@Repository("commonRepository")
public class CommonRepositoryImpl implements CommonRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public CommonRepositoryImpl() {
    }

    private Query createNamedQuery(EntityManager entityManager, String name, boolean cacheable, String cacheRegion, Map<String, Object> values) {
        Query query = entityManager.createNamedQuery(name.trim());
        if (cacheable) {
            query.setHint("org.hibernate.cacheable", "true");
            if (cacheRegion != null && !cacheRegion.equals("")) {
                query.setHint("org.hibernate.cacheRegion", cacheRegion);
            }
        }

        if (values != null && values.size() > 0) {
            Iterator var7 = values.entrySet().iterator();

            while(var7.hasNext()) {
                Entry<String, Object> e = (Entry)var7.next();
                query.setParameter((String)e.getKey(), e.getValue());
            }
        }

        return query;
    }

    private Query createNativeQuery(EntityManager entityManager, String sql, Class resultClass, boolean mapResult, Map<String, Object> values) {
        Query query = null;
        if (resultClass != null) {
            query = entityManager.createNativeQuery(sql, resultClass);
        } else {
            query = entityManager.createNativeQuery(sql);
        }

        if (mapResult) {
            ((org.hibernate.Query)query.unwrap(org.hibernate.Query.class)).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        }

        if (values != null && values.size() > 0) {
            Iterator var7 = values.entrySet().iterator();

            while(var7.hasNext()) {
                Entry<String, Object> e = (Entry)var7.next();
                query.setParameter((String)e.getKey(), e.getValue());
            }
        }

        return query;
    }

    private Query createQuery(EntityManager entityManager, String jpql, boolean cacheable, String cacheRegion, Map<String, Object> values) {
        Query query = entityManager.createQuery(jpql);
        if (cacheable) {
            query.setHint("org.hibernate.cacheable", "true");
            if (cacheRegion != null && !cacheRegion.equals("")) {
                query.setHint("org.hibernate.cacheRegion", cacheRegion);
            }
        }

        if (values != null && values.size() > 0) {
            Iterator var7 = values.entrySet().iterator();

            while(var7.hasNext()) {
                Entry<String, Object> e = (Entry)var7.next();
                query.setParameter((String)e.getKey(), e.getValue());
            }
        }

        return query;
    }

    public List find(String jpql) {
        return this.find((String)jpql, (Map)(new HashMap()));
    }

    public <T> T find(Class clazz, Serializable id) {
        return null;
    }

    public List find(String jpql, BaseCriteria baseCriteria) {
        return baseCriteria != null ? this.find(jpql + " " + baseCriteria.getCondition(), baseCriteria.getValues()) : this.find((String)jpql, (Map)(new HashMap()));
    }

    public void findByPage(String jpql, Map<String, Object> values, Integer rows, Integer page) {
        PageBean pageBean = new PageBean();
        pageBean.setRowsPerPage(rows);
        pageBean.setPageNo(page);
        jpql = jpql.toUpperCase();
        Query query = this.createQuery(this.entityManager, jpql, false, (String)null, values);
        query.setFirstResult(pageBean.getRowStart());
        query.setMaxResults(pageBean.getRowsPerPage());
        pageBean.setData(query.getResultList());
        int end = jpql.length();
        if (jpql.contains("ORDER BY")) {
            end = jpql.indexOf("ORDER BY");
        }

        String queryString;
        int j;
        if (jpql.toUpperCase().contains("SELECT")) {
            j = jpql.toUpperCase().indexOf("FROM");
            queryString = "SELECT COUNT(1) " + jpql.substring(j, end);
        } else {
            queryString = "SELECT COUNT(1) " + jpql.substring(0, end);
        }

        j = queryString.toUpperCase().lastIndexOf("ORDER");
        if (j != -1) {
            queryString = queryString.substring(0, j);
        }

        Query cquery = this.createQuery(this.entityManager, queryString, false, (String)null, values);
        int maxRow = Integer.valueOf(cquery.getSingleResult().toString());
        pageBean.setRowsCount(maxRow);
    }

    public List find(String jpql, Map<String, Object> values) {
        Query q = this.createQuery(this.entityManager, jpql, false, (String)null, values);
        return q.getResultList();
    }

    public void findByPage(PageBean pageBean) {
        this.findByPage(pageBean, (Map)(new HashMap()));
    }

    public void findByPage(PageBean pageBean, BaseCriteria baseCriteria) {
        if (baseCriteria != null) {
            pageBean.setCondition(baseCriteria.getCondition());
            this.findByPage(pageBean, baseCriteria.getValues());
        } else {
            this.findByPage(pageBean);
        }

    }

    public void findByPage(PageBean pageBean, Map<String, Object> values) {
        String jpql = pageBean.getQuery() != null ? pageBean.getQuery() : pageBean.getAutoQuery();
        Query query = this.createQuery(this.entityManager, jpql, false, (String)null, values);
        query.setFirstResult(pageBean.getRowStart());
        query.setMaxResults(pageBean.getRowsPerPage());
        pageBean.setData(query.getResultList());
        int end = jpql.length();
        if (jpql.contains("order by")) {
            end = jpql.indexOf("order by");
        }

        String queryString;
        int j;
        if (jpql.toUpperCase().contains("SELECT")) {
            j = jpql.toUpperCase().indexOf("FROM");
            queryString = "select count(1) " + jpql.substring(j, end);
        } else {
            queryString = "select count(1) " + jpql.substring(0, end);
        }

        j = queryString.toUpperCase().lastIndexOf("ORDER");
        if (j != -1) {
            queryString = queryString.substring(0, j);
        }

        Query cquery = this.createQuery(this.entityManager, queryString, false, (String)null, values);
        int maxRow = Integer.valueOf(cquery.getSingleResult().toString());
        pageBean.setRowsCount(maxRow);
    }

    public List findByProperty(Class cls, String propertyName, Object value) {
        String queryString = "from " + cls.getName() + " model where model." + propertyName + "= :propetyName";
        Map<String, Object> values = new HashMap();
        values.put("propetyName", value);
        return this.find((String)queryString, (Map)values);
    }

    public List findByPropertyIgnoreCase(Class cls, String propertyName, String value) {
        String queryString = "from " + cls.getName() + " model where lower(model." + propertyName + ")= :propetyName";
        Map<String, Object> values = new HashMap();
        values.put("propetyName", value);
        return this.find((String)queryString, (Map)values);
    }

    public List findBySQL(String sql, Class resultClass) {
        return this.findBySQL(sql, resultClass, (Map)(new HashMap()));
    }

    public List findBySQL(String sql, Class resultClass, BaseCriteria easyCriteria) {
        return easyCriteria != null ? this.findBySQL(sql + " " + easyCriteria.getCondition(), resultClass, easyCriteria.getValues()) : this.findBySQL(sql, resultClass, (Map)(new HashMap()));
    }

    public List findBySQL(String sql, Class resultClass, Map<String, Object> values) {
        Query q = this.createNativeQuery(this.entityManager, sql, resultClass, false, values);
        List list = q.getResultList();
        return list;
    }

    public List findBySQL(String sql) {
        return this.findBySQL(sql, (Map)(new HashMap()));
    }

    public List findBySQL(String sql, BaseCriteria easyCriteria) {
        return easyCriteria != null ? this.findBySQL(sql + " " + easyCriteria.getCondition(), easyCriteria.getValues()) : this.findBySQL(sql, (Map)(new HashMap()));
    }

    public List findBySQL(String sql, Map<String, Object> values) {
        Query q = this.createNativeQuery(this.entityManager, sql, (Class)null, false, values);
        List list = q.getResultList();
        return list;
    }

    public <T> T merge(BaseEntity value) {
        T entity = this.find(value.getClass(), value.getId());
        this.copyNonNullProperties(value, entity);
        this.entityManager.merge(entity);
        return entity;
    }

    private void copyNonNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, this.getNullPropertyNames(src));
    }

    private String[] getNullPropertyNames(Object source) {
        BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet();
        PropertyDescriptor[] var5 = pds;
        int var6 = pds.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            PropertyDescriptor pd = var5[var7];
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }

        String[] result = new String[emptyNames.size()];
        return (String[])emptyNames.toArray(result);
    }
}
