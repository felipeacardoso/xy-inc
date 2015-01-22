package com.xyinc.restapi.validation;

import org.apache.commons.lang3.StringUtils;

import com.xyinc.restapi.exception.BadRequestException;

public class ValidationHelper {

	public static void validatePositiveParam(String paramName, int paramValue) {
		if (paramValue < 0) {
			throw new BadRequestException(String.format("The param '%s' cannot be negative.", paramName));
		}
	}
	
	public static void validateRequiredParam(String paramName, Object paramValue) {
		if (paramValue == null || StringUtils.isEmpty(paramValue.toString())) {
			throw new BadRequestException(String.format("The param '%s' is required.", paramName));
		}
	}
	
}
