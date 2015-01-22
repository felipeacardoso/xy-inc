package com.xyinc.restapi.dao.hibernate;

import java.util.List;

import com.xyinc.restapi.dao.PoiDAO;
import com.xyinc.restapi.model.Poi;

/**
 * Hibernate-specific implementation of the <tt>PoiDAO</tt>
 * non-CRUD data access object.
 *
 * @author Felipe Cardoso
 */
public class PoiDAOHibernate
        extends     GenericHibernateDAO<Poi, Long>
        implements  PoiDAO {
	
	private static final String SEARCH_NEAR_QUERY = "SELECT * FROM poi WHERE (POWER(:x - x, 2) + POWER(:y - y, 2)) <=  POWER(:radius, 2);";
	private static final String DELETE_QUERY = "DELETE FROM poi WHERE id = :poiId";

	/**
	 * Deletes the POI from database by Id
	 * 
	 * @param poiId id of the POI
	 */
	public void delete(long poiId) throws Exception {
		try {
			boolean startedLocally = beginTransaction();
			getSession().createSQLQuery(DELETE_QUERY)
			        .setParameter("poiId", poiId).executeUpdate();
			if (startedLocally) {
				getSession().getTransaction().commit();
			}
		} catch (Exception e) {
			if (getSession().getTransaction() != null)
				getSession().getTransaction().rollback();
			throw e;
		}
	}

	/**
	 * Receives X and Y coordinates specifying a reference point
	 * and a distance (radius) in meters and returns all the POI's from database 
	 * which are in the radius from the point reference
	 * 
	 * @param x point x of the reference
	 * @param y point y of the reference
	 * @param radius the max distance of the POI from the reference point
	 */
	public List<Poi> findNear(int x, int y, int radius) throws Exception {
		try {
			boolean startedLocally = beginTransaction();
			@SuppressWarnings("unchecked")
			List<Poi> poiList = (List<Poi>) getSession().createSQLQuery(SEARCH_NEAR_QUERY)
			        .addEntity(Poi.class).setParameter("x", x).setParameter("y", y)
			        .setParameter("radius", radius).list();
			if (startedLocally) {
				getSession().getTransaction().commit();
			}
			return poiList;
		} catch (Exception e) {
			if (getSession().getTransaction() != null)
				getSession().getTransaction().rollback();
			throw e;
		}
	}

}