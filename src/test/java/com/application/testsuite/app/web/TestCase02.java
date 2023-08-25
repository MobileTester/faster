package com.application.testsuite.app.web;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.application.testscenarios.ApiExecutionScenario;
import com.application.testscenarios.GoogleSearchScenario;
import com.application.testsuite.base.UIAutomationBaseTestClass;
import com.application.testsuite.testdata.factory.TestDataReaderFactory;
import com.application.testsuite.testdata.web.beans.TestCase01Testdatum;
import com.application.utils.annotations.TestData;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.restassured.response.Response;

public class TestCase02 extends UIAutomationBaseTestClass {
	
	
	@Test(groups = {"apiAutomationTest"}, description="InitMethod")
	@Description("This method is initialization method")
	@Feature("This method is initialization method")
	@Story("This method is initialization method") 
	@Step("This method is initialization method")
	public void method00_InitMethod() {
		System.out.println("API Automation Initialized");
	}
	
	@Test(groups = {"apiAutomationTest"}, dependsOnMethods = {"method00_InitMethod"}, description="Get users")
	@TestData(type = TestDataReaderFactory.JSON, source = "TestCase01Testdata", dataBean = "com.application.testsuite.testdata.web.beans.TestCase01Testdatum")
	@Description("Get List of Users")
	@Feature("Get List of Users")
	@Story("Get List of Users") 
	@Step("Get List of Users")
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
		
	}
	
	@Test(groups = {"apiAutomationTest"}, dependsOnMethods = {"method01_GetListOfUsers"}, description="Create a user")
	@TestData(type = TestDataReaderFactory.JSON, source = "TestCase01Testdata", dataBean = "com.application.testsuite.testdata.web.beans.TestCase01Testdatum")
	@Description("Create a User")
	@Feature("Create a User")
	@Story("Create a User") 
	@Step("Create a User")
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
		
	}
}
