package com.xyinc.restapi.dao.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xyinc.restapi.dao.DAOFactory;
import com.xyinc.restapi.dao.PoiDAO;

/**
 * Returns Hibernate-specific instances of DAOs.
 * <p/>
 * If for a particular DAO there is no additional non-CRUD functionality, we use
 * a nested static class to implement the interface in a generic way. This allows clean
 * refactoring later on, should the interface implement business data access
 * methods at some later time. Then, we would externalize the implementation into
 * its own first-level class.
 *
 * @author Felipe Cardoso
 */
public class HibernateDAOFactory extends DAOFactory {

    private static Log log = LogFactory.getLog(HibernateDAOFactory.class);

    public PoiDAO getPoiDAO() {
        return (PoiDAO)instantiateDAO(PoiDAOHibernate.class);
    }

    private GenericHibernateDAO<?, ?> instantiateDAO(Class<PoiDAOHibernate> daoClass) {
        try {
            log.debug("Instantiating DAO: " + daoClass);
            return (GenericHibernateDAO<?, ?>)daoClass.newInstance();
        } catch (Exception ex) {
            throw new RuntimeException("Can not instantiate DAO: " + daoClass, ex);
        }
    }

}