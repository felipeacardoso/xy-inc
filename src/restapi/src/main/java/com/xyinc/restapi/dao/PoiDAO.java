package com.xyinc.restapi.dao;

import java.util.List;

import com.xyinc.restapi.model.Poi;

/**
 * Business DAO operations related to the <tt>Poi</tt> entity.
 *
 * @see Poi
 * @author Felipe Cardoso
 */
public interface PoiDAO extends GenericDAO<Poi, Long> {

	public void delete(long poiId) throws Exception;
	public List<Poi> findNear(int x, int y, int radius) throws Exception;

}