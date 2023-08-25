package com.application.testscenarios;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.application.utils.restapi.ExecuteRestApi;
import com.application.utils.restapi.ReadResponse;

import io.qameta.allure.Step;
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
	@Step("Api for getting list of users with url: {0}")
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
	@Step("Api for creating a user with url: {0}, requestBody: {1}")
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
