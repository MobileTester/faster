package com.scripting.screens.web;

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

public class GooglePageSearch2 {

	Logger logger = LogManager.getLogger(this.getClass().getName());
	WebDriver pageDriver;
	
	@FindByRole(tag = "textarea", index = 1) 
	public WebElement searchTextBox;
	
	@FindByRole(attribute = "text()", value = "Sign in")
	public WebElement signInButton;
	
	public GooglePageSearch2(WebDriver driver) throws NoSuchFieldException, SecurityException {
		logger.info("Going to initialize the page objects");
		pageDriver = driver;
		// PageFactory.initElements(new AjaxElementLocatorFactory(driver, CommonConfigParameters.timeOutForElementPolling), this);
		// new FindByRoleLocatorFactory(driver).createLocator(searchTextBox.getClass().getDeclaredField("searchTextBox"));
		PageFactory.initElements(new FindByRoleFieldDecorator(new FindByRoleLocatorFactory(driver)), this);
		// PageFactory.initElements(new DefaultFieldDecorator(new FindByRoleLocatorFactory(driver)), this);
		
		logger.info("Initialized the page objects");
	}
	
}
