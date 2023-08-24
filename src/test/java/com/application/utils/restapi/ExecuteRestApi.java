package com.application.utils.restapi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ExecuteRestApi {

	private static Logger logger = LogManager.getLogger(ExecuteRestApi.class.getName());
	
	public Response executeGetRequest(String requestUrl) {
		Response response = null;
		try {
			logger.info("ExecuteRestApi: executeGetRequest: Going to execute the get request: " + requestUrl);
			response = RestAssured.get(requestUrl);
			logger.info("ExecuteRestApi: executeGetRequest: Executed the get request: " + requestUrl);
		}
		catch(Exception e) {
			logger.error("ExecuteRestApi: executeGetRequest: Error occured: " + e.getMessage());
		}
		return response;
	}
	
	public Response executePostRequest(String requestUrl, String requestBody) {
		Response response = null;
		try {
			logger.info("ExecuteRestApi: executePostRequest: Going to execute the post request: " + requestUrl);
			response = RestAssured.given().contentType("application/json").body(requestBody).post(requestUrl);
			logger.info("ExecuteRestApi: executePostRequest: Executed the post request: " + requestUrl);
		}
		catch(Exception e) {
			logger.error("ExecuteRestApi: executePostRequest: Error occured: " + e.getMessage());
		}
		return response;
	}
	
	public Response executePutRequest(String requestUrl, String requestBody) {
		Response response = null;
		try {
			logger.info("ExecuteRestApi: executePutRequest: Going to execute the put request: " + requestUrl);
			response = RestAssured.given().contentType("application/json").body(requestBody).put(requestUrl);
			logger.info("ExecuteRestApi: executePutRequest: Executed the put request: " + requestUrl);
		}
		catch(Exception e) {
			logger.error("ExecuteRestApi: executePutRequest: Error occured: " + e.getMessage());
		}
		return response;
	}
	
	public Response executePatchRequest(String requestUrl, String requestBody) {
		Response response = null;
		try {
			logger.info("ExecuteRestApi: executePatchRequest: Going to execute the patch request: " + requestUrl);
			response = RestAssured.given().contentType("application/json").body(requestBody).patch(requestUrl);
			logger.info("ExecuteRestApi: executePatchRequest: Executed the patch request: " + requestUrl);
		}
		catch(Exception e) {
			logger.error("ExecuteRestApi: executePatchRequest: Error occured: " + e.getMessage());
		}
		return response;
	}
	
	public Response executeDeleteRequest(String requestUrl) {
		Response response = null;
		try {
			logger.info("ExecuteRestApi: executeDeleteRequest: Going to execute the delete request: " + requestUrl);
			response = RestAssured.delete(requestUrl);
			logger.info("ExecuteRestApi: executeDeleteRequest: Executed the delete request: " + requestUrl);
		}
		catch(Exception e) {
			logger.error("ExecuteRestApi: executeDeleteRequest: Error occured: " + e.getMessage());
		}
		return response;
	}
	
	
}
