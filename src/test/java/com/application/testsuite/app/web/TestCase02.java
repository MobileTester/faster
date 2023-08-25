package com.application.testsuite.app.web;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.Test;

import com.application.testscenarios.ApiExecutionScenario;
import com.application.testsuite.base.UIAutomationBaseTestClass;
import com.application.testsuite.testdata.factory.TestDataReaderFactory;
import com.application.testsuite.testdata.web.beans.TestCase01Testdatum;
import com.application.utils.annotations.TestData;
import com.aventstack.extentreports.Status;

import io.restassured.response.Response;

public class TestCase02 extends UIAutomationBaseTestClass {
	
	
	@Test(groups = {"apiAutomationTest"}, description="InitMethod")
	public void method00_InitMethod() {
		System.out.println("API Automation Initialized");
		extentForTestClass.log(Status.PASS, "Initialization successful");
	}
	
	@Test(groups = {"apiAutomationTest"}, dependsOnMethods = {"method00_InitMethod"}, description="Get users")
	@TestData(type = TestDataReaderFactory.JSON, source = "TestCase01Testdata", dataBean = "com.application.testsuite.testdata.web.beans.TestCase01Testdatum")
	public void method01_GetListOfUsers(TestCase01Testdatum testdatum) {
		// ToDo: Change it to Data Provider. 
		String url = "https://reqres.in/api/users?page=2";
		
		// Creating the scenario file
		ApiExecutionScenario apiExecutionScenario = new ApiExecutionScenario(executeRestApi, readResponse);
		// Getting the response
		Response response = apiExecutionScenario.getListOfUsers(url);
		// Validating the response
		assertEquals(response.statusCode(), 200, "API Execution Failed"); 
		assertNotNull(response.getBody(), "API Exeucution Failed"); 
		extentForTestClass.log(Status.PASS, "Successfully got the list of users");
		
	}
	
	@Test(groups = {"apiAutomationTest"}, dependsOnMethods = {"method01_GetListOfUsers"}, description="Create a user")
	@TestData(type = TestDataReaderFactory.JSON, source = "TestCase01Testdata", dataBean = "com.application.testsuite.testdata.web.beans.TestCase01Testdatum")
	public void method02_CreateAUser(TestCase01Testdatum testdatum) {
		// ToDo: Change it to Data Provider. 
		String url = "https://reqres.in/api/users";
		String requestBody = "{\r\n"
				+ "    \"name\": \"morpheus\",\r\n"
				+ "    \"job\": \"leader\"\r\n"
				+ "}";
		
		// Creating the scenario file
		ApiExecutionScenario apiExecutionScenario = new ApiExecutionScenario(executeRestApi, readResponse);
		// Getting the response
		Response response = apiExecutionScenario.createAUser(url, requestBody);
		// Validating the response
		assertEquals(response.statusCode(), 201, "API Execution Failed"); 
		assertNotNull(response.getBody(), "API Exeucution Failed"); 
		extentForTestClass.log(Status.PASS, "Successfully created a user");
	}
}
