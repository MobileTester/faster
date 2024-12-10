package com.scripting.testcases;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.data.annotations.TestData;
import com.data.reader.TestDataReaderFactory;
import com.scripting.base.UIAutomationBaseTestClass;
import com.scripting.beans.TestCase01Testdatum;
import com.scripting.scenarios.GoogleSearchScenario;

public class TestCase01 extends UIAutomationBaseTestClass {
	
	
	@Test(groups = {"uiAutomationTest"}, description="InitMethod")
	public void method00_InitMethod() {
		System.out.println("Initialized");
		extentForTestClass.log(Status.PASS, "Initialization successful");
	}
	
	@Test(groups = {"uiAutomationTest"}, dependsOnMethods = {"method00_InitMethod"}, description="Perform Google search")
	@TestData(type = TestDataReaderFactory.JSON, source = "TestCase01Testdata", dataBean = "com.scripting.beans.TestCase01Testdatum")
	public void method01_Search(TestCase01Testdatum testdatum) {
		GoogleSearchScenario googleSearchScenario = null;
		
		googleSearchScenario = new GoogleSearchScenario(webDriver);
		
		assertTrue(googleSearchScenario.s_PerfromGoogleSearch(testdatum.getGoogleURL(), testdatum.getSearchTerm()), "Search Operation Failed");
		extentForTestClass.log(Status.PASS, "Google Search passed");
	}
}
