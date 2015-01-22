package com.xyinc.restapi.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Defines all DAOs and the concrete factories to get the conrecte DAOs.
 * <p>
 * To get a concrete DAOFactory, call <tt>instance()</tt> with one of the
 * classes that extend this factory.
 * <p>
 * Implementation: If you write a new DAO, this class has to know about it.
 * If you add a new persistence mechanism, add an additional concrete factory
 * for it as a constant, like <tt>HIBERNATE</tt>.
 *
 * @author Felipe Cardoso
 */
public abstract class DAOFactory {

    private static Log log = LogFactory.getLog(DAOFactory.class);

    /**
     * Creates a standalone DAOFactory that returns unmanaged DAO
     * beans for use in any environment Hibernate has been configured
     * for. Uses HibernateUtil/SessionFactory and Hibernate context
     * propagation (CurrentSessionContext), thread-bound or transaction-bound,
     * and transaction scoped.
     */
    @SuppressWarnings("rawtypes")
	public static final Class HIBERNATE = com.xyinc.restapi.dao.hibernate.HibernateDAOFactory.class;

    /**
     * Factory method for instantiation of concrete factories.
     */
    public static DAOFactory instance(Class<DAOFactory> factory) {
        try {
            log.debug("Creating concrete DAO factory: " + factory);
            return (DAOFactory)factory.newInstance();
        } catch (Exception ex) {
            throw new RuntimeException("Couldn't create DAOFactory: " + factory);
        }
    }

    // Data Access Object Interfaces
    public abstract PoiDAO getPoiDAO();

}