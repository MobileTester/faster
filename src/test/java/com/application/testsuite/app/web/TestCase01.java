package com.application.testsuite.app.web;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.application.testscenarios.GoogleSearchScenario;
import com.application.testsuite.base.UIAutomationBaseTestClass;
import com.application.testsuite.testdata.factory.TestDataReaderFactory;
import com.application.testsuite.testdata.web.beans.TestCase01Testdatum;
import com.application.utils.annotations.TestData;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;

public class TestCase01 extends UIAutomationBaseTestClass {
	
	
	@Test(groups = {"uiAutomationTest"}, description="InitMethod")
	@Description("This method is initialization method")
	@Feature("This method is initialization method")
	@Story("This method is initialization method") 
	@Step("This method is initialization method")
	public void method00_InitMethod() {
		System.out.println("Initialized");
	}
	
	@Test(groups = {"uiAutomationTest"}, dependsOnMethods = {"method00_InitMethod"}, description="Perform Google search")
	@TestData(type = TestDataReaderFactory.JSON, source = "TestCase01Testdata", dataBean = "com.application.testsuite.testdata.web.beans.TestCase01Testdatum")
	@Description("This method is for Google search")
	@Feature("This method is for Google search")
	@Story("This method is for Google search") 
	@Step("This method is for Google search")
	public void method01_Search(TestCase01Testdatum testdatum) {
		GoogleSearchScenario googleSearchScenario = null;
		
		googleSearchScenario = new GoogleSearchScenario(driver);
		
		assertTrue(googleSearchScenario.s_PerfromGoogleSearch(testdatum.getGoogleURL(), testdatum.getSearchTerm()), "Search Operation Failed");
	}
}
