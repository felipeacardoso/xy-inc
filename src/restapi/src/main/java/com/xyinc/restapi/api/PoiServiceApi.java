package com.xyinc.restapi.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xyinc.restapi.exception.BadRequestException;
import com.xyinc.restapi.exception.ServiceException;
import com.xyinc.restapi.model.Poi;
import com.xyinc.restapi.services.PoiService;
import com.xyinc.restapi.validation.ValidationHelper;

@Path("pois")
public class PoiServiceApi {

	private static Log log = LogFactory.getLog(PoiServiceApi.class);
	
	/**
	 * Service method to get all registered POI's
	 * 
	 * @return a list of POI's
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Poi> getAllPois() {
		try {
			return PoiService.getInstance().getAllPois();
		} catch (Exception ex) {
			log.fatal("Error on getAllPois", ex);
			throw new ServiceException();
		}
	}
	
	/**
	 * Service method to delete POI by id
	 * 
	 * @param poiId
	 */
	@Path("/{id}")
	@DELETE
	public void deletePoi(@PathParam("id") int poiId) {
		try {
			PoiService.getInstance().deletePoi(poiId);
		} catch (Exception ex) {
			log.fatal("Error on deletePoi", ex);
			throw new ServiceException();
		}
	}

	/**
	 * Service method to create a new POI
	 * 
	 * @param poi
	 * @return created POI object
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Poi createPoi(Poi poi) {
		try {
			//validates all parameters received from call
			ValidationHelper.validateRequiredParam("POI", poi);
			ValidationHelper.validateRequiredParam("name", poi.getName());
			ValidationHelper.validatePositiveParam("x", poi.getX());
			ValidationHelper.validatePositiveParam("y", poi.getY());
			
			return PoiService.getInstance().createPoi(poi);
		} catch (BadRequestException bre) {
			log.fatal("Error on createPoi", bre);
			throw bre;
		} catch (Exception ex) {
			log.fatal("Error on createPoi", ex);
			throw new ServiceException();
		}
	}

	/**
	 * Service method to get nearest POIs from a point in a radius
	 * 
	 * @param x
	 * @param y
	 * @param radius
	 * @return a list of POI's
	 */
	@Path("/near")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Poi> getPoisNear(@QueryParam("x") int x, @QueryParam("y") int y,
	        @QueryParam("dmax") int radius) {
		try {
			ValidationHelper.validatePositiveParam("x", x);
			ValidationHelper.validatePositiveParam("y", y);
			ValidationHelper.validatePositiveParam("dmax", radius);

			return PoiService.getInstance().getNear(x, y, radius);
		} catch (BadRequestException bre) {
			log.fatal("Error on getPoisNear", bre);
			throw bre;
		} catch (Exception ex) {
			log.fatal("Error on getPoisNear", ex);
			throw new ServiceException();
		}
	}
}
