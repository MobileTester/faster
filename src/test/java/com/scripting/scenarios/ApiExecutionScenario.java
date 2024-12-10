package com.scripting.scenarios;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.restapi.ExecuteRestApi;
import com.api.restapi.ReadResponse;

import io.restassured.response.Response;

public class ApiExecutionScenario {

	private Logger logger = LogManager.getLogger(ApiExecutionScenario.class.getName());
	// object for executing rest apis
	private ExecuteRestApi executeRestApi = null;
	private ReadResponse readResponse = null;
	
	public ApiExecutionScenario(ExecuteRestApi executeRestApi, ReadResponse readResponse) {
		this.executeRestApi = executeRestApi;
		this.readResponse = readResponse;
	}
	
	// Sample Get API
	public Response getListOfUsers(String url) {
		Response responseGet = null;
		try {
			responseGet = executeRestApi.executeGetRequest(url); 
		}
		catch(Exception e) {
			logger.error(e.getMessage());
		}
		return responseGet;
	}
	
	// Sample Post API
	public Response createAUser(String url, String requestBody) {
		Response responsePost = null;
		try {
			responsePost = executeRestApi.executePostRequest(url, requestBody);
		}
		catch(Exception e) {
			logger.error(e.getMessage());
		}
		return responsePost;
	}
}
