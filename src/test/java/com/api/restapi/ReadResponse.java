package com.api.restapi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.restassured.response.Response;

public final class ReadResponse {

	private static Logger logger = LogManager.getLogger(ReadResponse.class.getName());
	
	public String getString(Response response, String jsonPath) {
		String value = "";
		try {
			value = response.jsonPath().getString(jsonPath);
			logger.info("ReadResponse: getString: value " + value);
		} 
		catch (Exception e) {
			logger.error(e.getMessage());
		}
		return value;
	}
	
	public Integer getInteger(Response response, String jsonPath) {
		Integer value = null;
		try {
			value = response.jsonPath().getInt(jsonPath); 
			logger.info("ReadResponse: getString: value " + value);
		} 
		catch (Exception e) {
			logger.error(e.getMessage());
		}
		return value;
	}
	
	
}
