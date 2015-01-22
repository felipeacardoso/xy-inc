package com.xyinc.restapi.dao.hibernate;

import com.xyinc.restapi.dao.*;
import com.xyinc.restapi.persistence.HibernateUtil;

import org.hibernate.*;
import org.hibernate.criterion.*;

import java.util.*;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

/**
 * Implements the generic CRUD data access operations using Hibernate APIs.
 * <p>
 * To write a DAO, subclass and parameterize this class with your persistent class.
 * Of course, assuming that you have a traditional 1:1 appraoch for Entity:DAO design.
 * <p>
 * You have to inject a current Hibernate <tt>Session</tt> to use a DAO. Otherwise, this
 * generic implementation will use <tt>HibernateUtil.getSessionFactory()</tt> to obtain the
 * curren <tt>Session</tt>.
 *
 * @see HibernateDAOFactory
 *
 * @author Felipe Cardoso
 * 
 */
public abstract class GenericHibernateDAO<T, ID extends Serializable>
        implements GenericDAO<T, ID> {

    private Class<T> persistentClass;
    private Session session;

    @SuppressWarnings("unchecked")
	public GenericHibernateDAO() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                                .getGenericSuperclass()).getActualTypeArguments()[0];
    }
    
    public void setSession(Session s) {
        this.session = s;
    }

    protected Session getSession() {
        if (session == null)
            session = HibernateUtil.getSessionFactory().getCurrentSession();
        return session;
    }
    
    protected boolean beginTransaction() {
		if(getSession().getTransaction().isActive()) {
			return false;
		} else {
			getSession().getTransaction().begin();
			return true;
		}
	}

    public Class<T> getPersistentClass() {
        return persistentClass;
    }

    @SuppressWarnings("unchecked")
    public T findById(ID id, boolean lock) {
        T entity;
        if (lock)
            entity = (T) getSession().load(getPersistentClass(), id, LockOptions.UPGRADE);
        else
            entity = (T) getSession().load(getPersistentClass(), id);

        return entity;
    }

    public List<T> findAll() throws Exception {
        return findByCriteria();
    }

    @SuppressWarnings("unchecked")
    public List<T> findByExample(T exampleInstance, String... excludeProperty) throws Exception {
    	
    	try {
    		boolean localTransaction = beginTransaction();
    		Criteria crit = getSession().createCriteria(getPersistentClass());
            Example example =  Example.create(exampleInstance);
            for (String exclude : excludeProperty) {
                example.excludeProperty(exclude);
            }
            crit.add(example);
            List<T> list = (List<T>)crit.list();
            
			if (localTransaction) {
				getSession().getTransaction().commit();
			}
			return list;
		} catch (Exception e) {
			if (getSession().getTransaction() != null)
				getSession().getTransaction().rollback();
			throw e;
		}
    }

    public T makePersistent(T entity) throws Exception {
    	try {
    		boolean localTransaction = beginTransaction();
	        getSession().saveOrUpdate(entity);
	        if (localTransaction) {
				getSession().getTransaction().commit();
			}
	        return entity;
		} catch (Exception e) {
			if (getSession().getTransaction() != null)
				getSession().getTransaction().rollback();
			throw e;
		}
        
    }

    public void makeTransient(T entity) throws Exception {
    	try {
    		boolean localTransaction = beginTransaction();
    		getSession().delete(entity);
	        if (localTransaction) {
				getSession().getTransaction().commit();
			}
		} catch (Exception e) {
			if (getSession().getTransaction() != null)
				getSession().getTransaction().rollback();
			throw e;
		}
    }

    /**
     * Use this inside subclasses as a convenience method.
     * @throws Exception 
     */
    @SuppressWarnings("unchecked")
    protected List<T> findByCriteria(Criterion... criterion) throws Exception {
    	try {
    		boolean localTransaction = beginTransaction();
            Criteria crit = getSession().createCriteria(getPersistentClass());
            for (Criterion c : criterion) {
                crit.add(c);
            }
            List<T> list = (List<T>)crit.list();
    		
			if (localTransaction) {
				getSession().getTransaction().commit();
			}
			return list;
		} catch (Exception e) {
			if (getSession().getTransaction() != null)
				getSession().getTransaction().rollback();
			throw e;
		}
   }

}
