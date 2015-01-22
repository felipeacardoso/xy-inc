package com.xyinc.restapi.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class BadRequestException extends WebApplicationException {

	private static final long serialVersionUID = -7827094272588862910L;
	
	public BadRequestException(String message)
	{
		super(Response.status(Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON)
		        .entity(new ServiceError(Status.BAD_REQUEST.getStatusCode(), message)).build());
	}

}
