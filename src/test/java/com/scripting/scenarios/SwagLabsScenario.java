package com.scripting.scenarios;

import java.net.URI;
import java.net.URL;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.driver.locatorengine.UIConstructViewFromJson;
import com.driver.locatorengine.UIView;
import com.scripting.screens.web.GooglePageSearch;
import com.scripting.screens.web.GooglePageSearch2;
import com.scripting.screens.web.SwagLabsLoginPage;
import com.scripting.screens.web.SwagLabsLoginPage2;

public class SwagLabsScenario {

	private Logger logger = LogManager.getLogger(SwagLabsScenario.class.getName());
	private WebDriver webDriverForPage;
	
	public SwagLabsScenario(WebDriver driver) {
		logger.info("ScenarioClass: "+ this.getClass().getName()+ " instantiated");
		webDriverForPage = driver;
	}
	
	/**
	 * @description - Scenario to perform login in Swag Labs Login page
	 * @startpoint -  Swag Labs Login page
	 * @endpoint - Products Page
	 * @param - url & username & password
	 * @return - boolean
	 * @author nikhil_narayanan
	 */
	public boolean s_PerformSwagLabsLogin(String url, String username, String password) {
		logger.info("Scenario: Going to execute s_PerformSwagLabsLogin");
		boolean aReturnValue = false;
		SwagLabsLoginPage labsLoginPage;
		try {
			labsLoginPage = new SwagLabsLoginPage(webDriverForPage);
			webDriverForPage.navigate().to(url); 
			labsLoginPage.usernameInput.sendKeys(username);
			logger.info("Just testing the List size for userNameInput: " + labsLoginPage.usernameInputs.size());
			labsLoginPage.passwordInput.sendKeys(password);
			labsLoginPage.loginButton.click();
			logger.info("PASS: s_PerformSwagLabsLogin");
			// making the scenario as passed
			aReturnValue = true;
		}
		catch(Exception e) {
			logger.error("FAIL: s_PerformSwagLabsLogin: Error occured while executing scenario \n" + e.getMessage());
		}
		logger.info("Scenario: End of s_PerformSwagLabsLogin");
		return aReturnValue;
	}
	
	/**
	 * @description - Scenario to perform login in Swag Labs Login page
	 * @startpoint -  Swag Labs Login page
	 * @endpoint - Products Page
	 * @param - url & username & password
	 * @return - boolean
	 * @author nikhil_narayanan
	 */
	public boolean s_PerformSwagLabsLogin2(String url, String username, String password) {
		logger.info("Scenario: Going to execute s_PerformSwagLabsLogin2");
		boolean aReturnValue = false;
		SwagLabsLoginPage2 labsLoginPage;
		try {
			labsLoginPage = new SwagLabsLoginPage2(webDriverForPage);
			webDriverForPage.navigate().to(url); 
			labsLoginPage.usernameInput.sendKeys(username);
			
			 int size = labsLoginPage.usernameInputs.size();
			 logger.info("Just testing the List size for userNameInput: " + size);
			
			labsLoginPage.passwordInput.sendKeys(password);
			labsLoginPage.loginButton.click();
			logger.info("PASS: s_PerformSwagLabsLogin2");
			// making the scenario as passed
			aReturnValue = true;
		}
		catch(Exception e) {
			logger.error("FAIL: s_PerformSwagLabsLogin2: Error occured while executing scenario \n" + e.getMessage());
		}
		logger.info("Scenario: End of s_PerformSwagLabsLogin2");
		return aReturnValue;
	}
	
	/**
	 * @description - Scenario to get login credentials from Login Page
	 * @startpoint -  Swag Labs Login page
	 * @endpoint - Swag Labs Login page
	 * @param - url 
	 * @return - String
	 * @author nikhil_narayanan
	 */
	public String s_GetLoginUserNames(String url) {
		logger.info("Scenario: Going to execute s_GetLoginUserNames");
		String acceptedUserNames = null;
		SwagLabsLoginPage labsLoginPage;
		try {
			labsLoginPage = new SwagLabsLoginPage(webDriverForPage);
			webDriverForPage.navigate().to(url); 
			acceptedUserNames = labsLoginPage.loginCredentialsText.getText();
			logger.info(acceptedUserNames);
			logger.info("PASS: s_GetLoginUserNames");
		} 
		catch (Exception e) {
			logger.error("FAIL: s_GetLoginUserNames: Error occured while executing scenario \n" + e.getMessage());
		}
		logger.info("Scenario: End of s_GetLoginUserNames");
		return acceptedUserNames;
	}
	
	/**
	 * @description - Scenario to get login credentials from Login Page
	 * @startpoint -  Swag Labs Login page
	 * @endpoint - Swag Labs Login page
	 * @param - url 
	 * @return - String
	 * @author nikhil_narayanan
	 */
	public String s_GetLoginUserNames2(String url) {
		logger.info("Scenario: Going to execute s_GetLoginUserNames2");
		String acceptedUserNames = null;
		SwagLabsLoginPage2 labsLoginPage;
		try {
			labsLoginPage = new SwagLabsLoginPage2(webDriverForPage);
			webDriverForPage.navigate().to(url); 
			acceptedUserNames = labsLoginPage.loginCredentialsText.getText();
			logger.info(acceptedUserNames);
			logger.info("PASS: s_GetLoginUserNames2");
		} 
		catch (Exception e) {
			logger.error("FAIL: s_GetLoginUserNames2: Error occured while executing scenario \n" + e.getMessage());
		}
		logger.info("Scenario: End of s_GetLoginUserNames2");
		return acceptedUserNames;
	}
	
	
	
}
