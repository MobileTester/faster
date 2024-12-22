package com.scripting.screens.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import com.data.configparameters.CommonConfigParameters;
import com.driver.rolelocator.annotation.FindByRole;

import io.appium.java_client.pagefactory.AndroidBy;

public class SwagLabsLoginPage {

	Logger logger = LogManager.getLogger(SwagLabsLoginPage.class.getName());
	WebDriver pageDriver;
	
	@FindBy(id="user-name")
	public WebElement usernameInput;
	
	@FindBy(id = "password")
	public WebElement passwordInput;
	
	@FindBy(id = "login-button")
	public WebElement loginButton;
	
	@FindBy(id = "login_credentials")
	public WebElement loginCredentialsText;
	
	public SwagLabsLoginPage(WebDriver driver) {
		logger.info("Going to initialize the page objects");
		pageDriver = driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, CommonConfigParameters.timeOutForElementPolling), this);
		logger.info("Initialized the page objects");
	}
	
}
