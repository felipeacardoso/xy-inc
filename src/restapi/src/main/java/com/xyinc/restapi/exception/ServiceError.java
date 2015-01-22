package com.xyinc.restapi.exception;

import java.io.Serializable;

/**
 * Class to personalize error messages.
 * 
 * @author Felipe Cardoso
 *
 */
public class ServiceError implements Serializable {

	private static final long serialVersionUID = -6787044855771152710L;
	private int errorCode;
	private String errorMsg;

	public ServiceError() {
		
	}
	
	public ServiceError(int errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
}
