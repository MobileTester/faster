package com.scripting.screens.web;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;

import com.data.configparameters.CommonConfigParameters;
import com.driver.rolelocator.annotation.FindByRole;
import com.driver.rolelocator.annotation.FindByRoleFieldDecorator;
import com.driver.rolelocator.annotation.FindByRoleLocatorFactory;

import io.appium.java_client.pagefactory.AndroidBy;

public class SwagLabsLoginPage2 {

	Logger logger = LogManager.getLogger(SwagLabsLoginPage2.class.getName());
	WebDriver pageDriver;
	
	@FindByRole(tag = "input", attribute="id", value = "user-name")
	public WebElement usernameInput;
	
	// For Testing - This is not working - The field is getting ignored. 
	@FindByRole(tag = "input", attribute="id", value = "user-name")
	public List<WebElement> usernameInputs;
	
	// Testing whether the standard Selenium Page Factory annotations will work in this class.
//	@FindBy(xpath = "//input[@id='user-name']") 
//	public WebElement usernameInput;
	
//	@FindByRole(value = "password")
//	public WebElement passwordInput;
	
	// if contains or starts-with is used, attribute needs to be given. Other wise it is not a valid xpath
	@FindByRole(attribute = "id", value = "contains('password')")
	public WebElement passwordInput;
	
	// Invalid field
	@FindByRole(value = "password_INVALID")
	public WebElement passwordInput2;
	
	@FindByRole(value = "Login")
	public WebElement loginButton;
	
	@FindByRole(attribute = "text()", value = "standard_user")
	public WebElement loginCredentialsText;
	
	public SwagLabsLoginPage2(WebDriver driver) {
		logger.info("Going to initialize the page objects");
		pageDriver = driver;
		PageFactory.initElements(new FindByRoleFieldDecorator(new FindByRoleLocatorFactory(driver)), this);
		// PageFactory.initElements(new DefaultFieldDecorator(new FindByRoleLocatorFactory(driver)), this);
		logger.info("Initialized the page objects");
	}
	
}
