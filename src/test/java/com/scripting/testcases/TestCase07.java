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
import com.scripting.scenarios.SwagLabsScenario;

public class TestCase07 extends UIAutomationBaseTestClass {
	
	
	@Test(groups = {"uiAutomationTest"}, description="InitMethod")
	public void method00_InitMethod() {
		System.out.println("Initialized");
		extentForTestClass.log(Status.PASS, "Initialization successful");
	}
	
	@Test(groups = {"uiAutomationTest"}, dependsOnMethods = {"method00_InitMethod"}, description="Swag Labs login")
	@TestData(type = TestDataReaderFactory.JSON, source = "TestCase01Testdata", dataBean = "com.scripting.beans.TestCase01Testdatum")
	public void method01_Search(TestCase01Testdatum testdatum) {
		SwagLabsScenario swagLabsScenario = null;
		swagLabsScenario = new SwagLabsScenario(webDriver);
		
		assertTrue(swagLabsScenario.s_PerformSwagLabsLogin("https://www.saucedemo.com/", "standard_user", "secret_sauce"), "Sign-In Button click Operation Failed");
		extentForTestClass.log(Status.PASS, "Swag Labs Login Passed");
	}
}
