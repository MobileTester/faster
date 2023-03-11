package com.application.testsuite.app.web;

import org.testng.annotations.Test;

import com.application.testsuite.base.UIAutomationBaseTestClass;
import com.application.testsuite.testdata.factory.TestDataReaderFactory;
import com.application.testsuite.testdata.web.beans.TestCase01Testdatum;
import com.application.utils.annotations.TestData;

public class TestCase01 extends UIAutomationBaseTestClass {
	
	
	@Test(groups = {"uiAutomationTest"}, description="InitMethod")
	public void method00_InitMethod() {
		System.out.println("Initialized");
	}
	
	@Test(groups = {"uiAutomationTest"}, dependsOnMethods = {"method00_InitMethod"}, description="Perform Google search")
	@TestData(type = TestDataReaderFactory.JSON, source = "TestCase01Testdata", dataBean = "com.application.testsuite.testdata.web.beans.TestCase01Testdatum")
	public void method01_Search(TestCase01Testdatum testdatum) {
		driver.get(testdatum.getGoogleURL());; 
	}
}
