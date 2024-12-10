package com.scripting.base;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.http.jdk.JdkHttpClient;
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

import com.api.restapi.ExecuteRestApi;
import com.api.restapi.ReadResponse;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.model.Test;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.data.configurationproperties.ConfigurationProperties;
import com.driver.appium.server.AppiumServerUtils;
import com.driver.factory.MobileDriverFactory;
import com.driver.factory.WebDriverFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
public class UIAutomationBaseTestClass {

	static Logger logger = LogManager.getLogger(UIAutomationBaseTestClass.class.getName());
	private static WebDriverFactory webDriverFactory = null;
	private static MobileDriverFactory mobileDriverFactory = null;
	
	private static String batchExecutionName = null;
	/** Soft Assert object for a Test Class*/
	protected SoftAssert completeFlowSoftAssert = null;
	private static Boolean webAutomationRequired = null; 
	private static String webBrowser = null;
	private static Boolean mobileAutomationRequired = null; 
	private static String mobilePlatform = null;
	// protected AndroidDriver androidDriver = null;
	protected AppiumDriver appiumDriver = null;
	protected String appPackageName = null;
	protected WebDriver webDriver = null;
	protected ExecuteRestApi executeRestApi = null;
	protected ReadResponse readResponse = null;
	protected static ExtentReports extent = null;
	protected ExtentTest extentForTestClass = null;
	
	@BeforeSuite(description = "Execute before all test cases", alwaysRun = true)
	public void beforeAllTests(ITestContext context) {
		
		batchExecutionName = "UI_Execution_" + getTodaysDateAndTime();
		
		webDriverFactory = new WebDriverFactory(batchExecutionName);
		mobileDriverFactory = new MobileDriverFactory(batchExecutionName);
		
		// Web browser properties
		webAutomationRequired = Boolean.parseBoolean(ConfigurationProperties.getProperty("webAutomationRequired"));
		webBrowser = ConfigurationProperties.getProperty("webBrowser");
		// Mobile platform properties
		mobileAutomationRequired = Boolean.parseBoolean(ConfigurationProperties.getProperty("mobileAutomationRequired")); 
		mobilePlatform = ConfigurationProperties.getProperty("mobilePlatform");
		
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
		if(webAutomationRequired) {
			webDriver = getWebDriverRequired(webBrowser); 
		}
		if(mobileAutomationRequired) {
			switch(mobilePlatform.toLowerCase()) {
				case "android" -> {
					appiumDriver = getAndroidDriverRequired();
					// getting app package name	
					appPackageName = appiumDriver.getCapabilities().getCapability("appPackage").toString();
					((AndroidDriver)appiumDriver).activateApp(appPackageName);
				}
				/** 
				 * Add other platform driver initializations here
				 * */ 
				default -> {
					logger.error("%s not supported currently!!!".formatted(mobilePlatform)); 
					}
				}

		}
		
		
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
		if(webDriver != null) {
			webDriver.quit();
			webDriver = null;
		}
		if(appiumDriver != null) { 
			switch(mobilePlatform.toLowerCase()) {
				case "android" -> {
					((AndroidDriver)appiumDriver).terminateApp(appPackageName);
					appiumDriver.quit();
					appiumDriver = null;
				}
				/** 
				 * Add other platform driver initializations here
				 * */ 
				default -> {
					logger.error("%s not supported currently!!!".formatted(mobilePlatform)); 
				}
			}

		}
		AppiumServerUtils.stopAppiumServer();
		
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
   
   // For Android Driver
   private UiAutomator2Options getUiAutomator2Options() {
	    
		UiAutomator2Options options = new UiAutomator2Options();
		try {
			logger.info("getUiAutomator2Options(): Going to read the Properties file to get the Android Driver Options");
			options.setUdid(ConfigurationProperties.getProperty("appiumUdid"));
			options.setAppPackage(ConfigurationProperties.getProperty("appiumAppPackage"));
			options.setAppActivity(ConfigurationProperties.getProperty("appiumAppActivity"));
			options.setNewCommandTimeout(Duration.ofSeconds(Integer.parseInt(ConfigurationProperties.getProperty("appiumNewCommandTimeout")))); 
			options.setCapability("noReset", Boolean.parseBoolean(ConfigurationProperties.getProperty("appiumNoReset")));
			logger.info("getUiAutomator2Options(): Read the below options from the configuration file");
			logger.info(options.toString());
		} 
		catch (Exception e) {
			options = null;
			logger.error("getUiAutomator2Options(): Error occured while creating UiAutomator2Options");
			logger.error(e.getMessage());
		}
		return options;
   }
   
	private AndroidDriver getAndroidDriverRequired() {
		AndroidDriver temp = null;
		try {
			String serverUrl = AppiumServerUtils.startAppiumServer();
			UiAutomator2Options options = getUiAutomator2Options();
			// type casting to AndroidDriver
			temp = (AndroidDriver) mobileDriverFactory.getDriver("Android", serverUrl, options);

		} 
		catch (Exception e) {
			logger.error("getAndroidDriverRequired() -> Exception occured while creating Android Driver");
			logger.error(e.getMessage());
		}
		return temp;
		
	}

	private WebDriver getWebDriverRequired(String webBrowser2) {
		return webDriverFactory.getDriver(webBrowser);
	}
	
	public String getTodaysDateAndTime() {
		String value = "";
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy_HH-mm-ss");
		Date date = new Date();
		value = dateFormat.format(date); //2016/11/16 12:08:43
		return value;
	}
}
