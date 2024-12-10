package com.scripting.testcases;

import static org.testng.Assert.assertTrue;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.data.annotations.TestData;
import com.data.reader.TestDataReaderFactory;
import com.scripting.base.UIAutomationBaseTestClass;
import com.scripting.beans.TestCase01Testdatum;
import com.scripting.scenarios.CalculatorScenario;
import com.scripting.scenarios.GoogleSearchScenario;

public class TestCase04 extends UIAutomationBaseTestClass {
	
	
	@Test(groups = {"uiAutomationTest"}, description="InitMethod")
	public void method00_InitMethod() {
		System.out.println("Initialized");
		extentForTestClass.log(Status.PASS, "Initialization successful");
	}
	
	@Test(groups = {"uiAutomationTest"}, dependsOnMethods = {"method00_InitMethod"}, description="Mobile Automation Test")
	@TestData(type = TestDataReaderFactory.JSON, source = "TestCase01Testdata", dataBean = "com.scripting.beans.TestCase01Testdatum")
	public void method01_Search(TestCase01Testdatum testdatum) {
		CalculatorScenario calculatorScenario = new CalculatorScenario(appiumDriver);
		Integer result = calculatorScenario.s_PerformAdditionOfTwoIntegers2(10, 20);
		Assert.assertEquals(result, 30); 
	}
}
