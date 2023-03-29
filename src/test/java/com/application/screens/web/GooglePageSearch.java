package com.application.screens.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import com.application.utils.appconfig.CommonConfigParameters;

public class GooglePageSearch {

	Logger logger = LogManager.getLogger(GooglePageSearch.class.getName());
	WebDriver pageDriver;
	
	@FindBy(name="q")
	public WebElement searchTextBox;
	
//	@FindBy(xpath="//*[@name='q']")
//	public WebElement searchTextBox;
	
	public GooglePageSearch(WebDriver driver) {
		logger.info("Going to initialize the page objects");
		pageDriver = driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, CommonConfigParameters.timeOutForElementPolling), this);
		logger.info("Initialized the page objects");
	}
	
}
