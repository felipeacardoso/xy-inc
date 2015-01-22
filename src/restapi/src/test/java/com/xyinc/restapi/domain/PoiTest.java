package com.xyinc.restapi.domain;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.xyinc.restapi.dao.DAOFactory;
import com.xyinc.restapi.dao.PoiDAO;
import com.xyinc.restapi.model.Poi;
import com.xyinc.restapi.persistence.HibernateUtil;

public class PoiTest  {

	private Session session;
	private PoiDAO poiDAO;

	@SuppressWarnings("unchecked")
	@Before
	public void configureExecution() {
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		poiDAO = DAOFactory.instance(DAOFactory.HIBERNATE).getPoiDAO();
	}

	@Test
	public void createAndDeletePoiTest() throws Exception {
		
		session.getTransaction().begin();

		Poi poi = poiDAO.makePersistent(new Poi("Test PoI", 10, 20));
		assertNotNull(poi.getId());
		
		poiDAO.delete(poi.getId());
		
		List<Poi> testResult = poiDAO.findAll();
		assertTrue(!testResult.contains(poi));

		session.getTransaction().rollback();
	}

	@Test
	public void findNearTest() throws Exception {

		session.getTransaction().begin();
		
		Poi poiNear1 = poiDAO.makePersistent(new Poi("Near PoI 1", 5, 5));
		Poi poiNear2 = poiDAO.makePersistent(new Poi("Near PoI 2", 10, 8));
		Poi poiAway1 = poiDAO.makePersistent(new Poi("PoI away 1", 0, 0));
		Poi poiAway2 = poiDAO.makePersistent(new Poi("PoI away 2", 4, 5));
		Poi poiAway3 = poiDAO.makePersistent(new Poi("PoI away 3", 14, 9));
		
		List<Poi> testResult = poiDAO.findNear(10, 5, 5);
		assertTrue(testResult.contains(poiNear1));
		assertTrue(testResult.contains(poiNear2));
		assertTrue(!testResult.contains(poiAway1));
		assertTrue(!testResult.contains(poiAway2));
		assertTrue(!testResult.contains(poiAway3));

		session.getTransaction().rollback();
	}

	@After
	public void afterExecution() {
		HibernateUtil.shutdown();
	}
	
}
