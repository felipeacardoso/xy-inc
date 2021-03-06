package com.xyinc.restapi.persistence;

import java.util.logging.Logger;

import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.*;

import javax.naming.*;

/**
 * Basic Hibernate helper class for Hibernate configuration and startup.
 * <p>
 * Uses a static initializer to read startup options and initialize
 * <tt>Configuration</tt> and <tt>SessionFactory</tt>.
 * <p>
 * This class also tries to figure out if JNDI binding of the <tt>SessionFactory</tt>
 * is used, otherwise it falls back to a global static variable (Singleton). If
 * you use this helper class to obtain a <tt>SessionFactory</tt> in your code,
 * you are shielded from these deployment differences.
 * <p>
 * Another advantage of this class is access to the <tt>Configuration</tt> object
 * that was used to build the current <tt>SessionFactory</tt>. You can access
 * mapping metadata programmatically with this API, and even change it and rebuild
 * the <tt>SessionFactory</tt>.
 * <p>
 * Note: This class supports annotations if you replace the line that creates
 * a Configuration object.
 * <p>
 * Note: This class supports only one data store. Support for several
 * <tt>SessionFactory</tt> instances can be easily added (through a static <tt>Map</tt>,
 * for example). You could then lookup a <tt>SessionFactory</tt> by its name.
 *
 * @author Christian Bauer
 * 
 */
public class HibernateUtil {

	private static Logger Log = Logger.getLogger(HibernateUtil.class.getName());

    private static Configuration configuration;
    private static SessionFactory sessionFactory;

    static {
        // Create the initial SessionFactory from the default configuration files
        try {
            // Read hibernate.properties, if present in hibernate.cfg.xml
            configuration = new Configuration().configure();
            
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
			sessionFactory = configuration.buildSessionFactory(builder.build());

            // Build and store (either in JNDI or static variable)
            rebuildSessionFactory(configuration);
        } catch (Throwable ex) {
        	Log.log(java.util.logging.Level.SEVERE, "Error on initializing Hibernate", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Returns the Hibernate configuration that was used to build the SessionFactory.
     *
     * @return Configuration
     */
    public static Configuration getConfiguration() {
        return configuration;
    }

    /**
     * Returns the global SessionFactory either from a static variable or a JNDI lookup.
     *
     * @return SessionFactory
     */
    public static SessionFactory getSessionFactory() {
        String sfName = configuration.getProperty(Environment.SESSION_FACTORY_NAME);
        if ( sfName != null) {
            try {
                return (SessionFactory) new InitialContext().lookup(sfName);
            } catch (NamingException ex) {
                throw new RuntimeException(ex);
            }
        } else if (sessionFactory == null) {
            rebuildSessionFactory();
        }
        return sessionFactory;
    }

    /**
     * Closes the current SessionFactory and releases all resources.
     * <p>
     * The only other method that can be called on HibernateUtil
     * after this one is rebuildSessionFactory(Configuration).
     */
    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();

        // Clear static variables
        sessionFactory = null;
    }


    /**
     * Rebuild the SessionFactory with the static Configuration.
     * <p>
     * Note that this method should only be used with static SessionFactory
     * management, not with JNDI or any other external registry. This method also closes
     * the old static variable SessionFactory before, if it is still open.
     */
     public static void rebuildSessionFactory() {
        rebuildSessionFactory(configuration);
     }

    /**
     * Rebuild the SessionFactory with the given Hibernate Configuration.
     * <p>
     * HibernateUtil does not configure() the given Configuration object,
     * it directly calls buildSessionFactory(). This method also closes
     * the old static variable SessionFactory before, if it is still open.
     *
     * @param cfg
     */
     public static void rebuildSessionFactory(Configuration cfg) {
        if (sessionFactory != null && !sessionFactory.isClosed())
            sessionFactory.close();
        
        if (cfg.getProperty(Environment.SESSION_FACTORY_NAME) != null) {
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
			cfg.buildSessionFactory(builder.build());
        } else {
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
			sessionFactory = cfg.buildSessionFactory(builder.build());
        }
        configuration = cfg;
     }

}