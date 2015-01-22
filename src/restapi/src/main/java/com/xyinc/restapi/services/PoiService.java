package com.xyinc.restapi.services;

import java.util.List;

import com.xyinc.restapi.dao.DAOFactory;
import com.xyinc.restapi.model.Poi;

public class PoiService {

	private static PoiService instance;

	public static synchronized PoiService getInstance() {
		if (instance == null) {
			instance = new PoiService();
		}

		return instance;
	}

	@SuppressWarnings("unchecked")
	public Poi createPoi(Poi poi) throws Exception {
		return DAOFactory.instance(DAOFactory.HIBERNATE).getPoiDAO().makePersistent(poi);
	}
	
	@SuppressWarnings("unchecked")
	public void deletePoi(long poiId) throws Exception {
		DAOFactory.instance(DAOFactory.HIBERNATE).getPoiDAO().delete(poiId);
	}
	
	@SuppressWarnings("unchecked")
	public List<Poi> getAllPois() throws Exception {
		return DAOFactory.instance(DAOFactory.HIBERNATE).getPoiDAO().findAll();
	}
	
	@SuppressWarnings("unchecked")
	public List<Poi> getNear(int x, int y, int radius) throws Exception {
		return DAOFactory.instance(DAOFactory.HIBERNATE).getPoiDAO().findNear(x, y, radius);
	}
	
}
