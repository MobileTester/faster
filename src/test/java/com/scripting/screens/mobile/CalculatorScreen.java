package com.scripting.screens.mobile;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import com.data.configparameters.CommonConfigParameters;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class CalculatorScreen {
	
	
	public CalculatorScreen(AppiumDriver appiumDriver) {
		logger.info("Going to initialize the screen objects");
		this.appiumDriver = appiumDriver;
		PageFactory.initElements(new AppiumFieldDecorator(appiumDriver, Duration.ofSeconds(CommonConfigParameters.timeOutForElementPolling)), this);  
		logger.info("Initialized the screen objects");
	}
	

	Logger logger = LogManager.getLogger(CalculatorScreen.class.getName());
	AppiumDriver appiumDriver;
	
	@AndroidFindBy(accessibility = "clear")
	public WebElement clearButton;
	
//	@AndroidFindBy(xpath = "//android.widget.ImageView[@content-desc=\"clear\"]")
//	public WebElement clearButton;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.miui.calculator:id/op_add\")")
	public WebElement plusButton;
	
	@AndroidFindBy(id = "com.miui.calculator:id/result")
	public WebElement result;
	
	public String digitButtonIdLocator = "com.miui.calculator:id/digit_%s";
	
	

	
}
