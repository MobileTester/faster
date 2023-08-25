package com.application.testsuite.base;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

import com.application.utils.configurationproperties.ConfigurationProperties;
import com.application.utils.drivers.factory.WebDriverFactory;
import com.application.utils.restapi.ExecuteRestApi;
import com.application.utils.restapi.ReadResponse;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.model.Test;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
public class UIAutomationBaseTestClass {

	static Logger logger = LogManager.getLogger(UIAutomationBaseTestClass.class.getName());
	private String batchExecutionName = null;
	/** Soft Assert object for a Test Class*/
	protected SoftAssert completeFlowSoftAssert = null;
	private String browserName = null;
	protected WebDriver driver = null;
	protected ExecuteRestApi executeRestApi = null;
	protected ReadResponse readResponse = null;
	protected static ExtentReports extent = null;
	protected ExtentTest extentForTestClass = null;
	
	@BeforeSuite(description = "Execute before all test cases", alwaysRun = true)
	public void beforeAllTests(ITestContext context) {
		
		batchExecutionName = "UI_Execution_" + getTodaysDateAndTime();
		browserName = ConfigurationProperties.getProperty("browser");
		
		// instantiating extent report
		extent = new ExtentReports();
		ExtentSparkReporter htmlReporter = new ExtentSparkReporter("test-output/extent-report.html");
		extent.attachReporter(htmlReporter);
		
		logger.info("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		logger.info("*************STARTING BATCH EXECUTION:- " + batchExecutionName + " **********************");
		logger.info("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
	}
	
	@AfterSuite(description = "Execute after all test cases", alwaysRun = true)
	public void afterAllTests(ITestContext context) {
		logger.info("\n\n\n");
		logger.info("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		logger.info("*************END OF BATCH EXECUTION:- " + batchExecutionName + " **********************");
		logger.info("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
	}
	
	@BeforeClass(description = "Execute before a test class", alwaysRun = true)
	public void beforeATestClass() {
		logger.info("\n\n\n");
		logger.info("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		logger.info("*************Before Test Class Execution for:- " + this.getClass().getName() + " **********************");
		logger.info("...........................................................................................................");
		
		ITestResult itr = Reporter.getCurrentTestResult();
		String testClassName = itr.getInstance().getClass().getName();
		extentForTestClass = extent.createTest(testClassName);
		
		// initializing the driver
		driver = WebDriverFactory.getDriver(browserName);
		// initializing for Rest Api validations
		executeRestApi = new ExecuteRestApi();
		readResponse = new ReadResponse();
		
		logger.info("Initialized SoftAssert object");
		completeFlowSoftAssert = new SoftAssert();
	}
	
	@AfterClass(description = "Execute after a test class", alwaysRun = true)
	public void afterATestClass() {
		logger.info("...........................................................................................................");
		logger.info("*************After Test Class Execution for:- " + this.getClass().getName() + " **********************");
		logger.info("}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}");
		
		// quitting the driver
		if(driver != null) {
			driver.quit();
			driver = null;
		}
		executeRestApi = null;
		readResponse = null;
		
		logger.info("Asserting for UI Validation failures for any Test Methods in this test class");
		Reporter.log("Asserting for UI Validation failures for any Test Methods in this test class");
		completeFlowSoftAssert.assertAll();
	}
	
	@BeforeMethod(alwaysRun = true)
	public void beforeATestMethod(ITestContext context, ITestResult result, Method aTestMethod) {
		
		logger.info("[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[");
		logger.info("*************Beginning of Test Method Execution for:- " + aTestMethod.getName() + " **********************");

		logger.info("*************Pre-requisites for the execution of Test Method:- " + aTestMethod.getName() + " **********************");
		
		
	}
	
	@AfterMethod(alwaysRun = true)
	public void afterATestMethod(ITestContext context, ITestResult result, Method aTestMethod) {
		
		logger.info("************* Post-requisites for the execution of the Test Method:- " + aTestMethod.getName() + " **********************");
		
		logger.info("*************End of Test Method Execution for:- " + aTestMethod.getName() + " **********************");
		logger.info("]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]");
		
	}
	
	   @AfterSuite
	    public void afterAllTests() {
	        extent.flush();
	    }
	
	public String getTodaysDateAndTime() {
		String value = "";
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy_HH-mm-ss");
		Date date = new Date();
		value = dateFormat.format(date); //2016/11/16 12:08:43
		return value;
	}
}
