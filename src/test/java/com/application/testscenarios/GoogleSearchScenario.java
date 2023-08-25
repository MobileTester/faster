package com.application.testscenarios;

import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import com.application.screens.web.GooglePageSearch;

public class GoogleSearchScenario {

	private Logger logger = LogManager.getLogger(GoogleSearchScenario.class.getName());
	private WebDriver webDriverForPage;
	
	public GoogleSearchScenario(WebDriver driver) {
		logger.info("ScenarioClass: "+ this.getClass().getName()+ " instantiated");
		webDriverForPage = driver;
	}
	
	/**
	 * @description - Scenario to search Google Search screen
	 * @startpoint - Google Search screen
	 * @endpoint - Google screen with search results
	 * @param - searchTerm
	 * @return - boolean
	 * @author nikhil_narayanan
	 */
	public boolean s_PerfromGoogleSearch(String url, String searchTerm) {
		logger.info("Scenario: Going to execute s_PerfromGoogleSearch");
		boolean aReturnValue = false;
		GooglePageSearch googlePageSearch = null;
		try {
			// Initializing the required page files
			googlePageSearch = new GooglePageSearch(webDriverForPage);
			logger.info("s_PerfromLogin: OLAMCTRMPortalPageLogin initialized");
			logger.info("Getting the URL");
			webDriverForPage.navigate().to(new URL(url));
			googlePageSearch.searchTextBox.sendKeys(searchTerm);
			googlePageSearch.searchTextBox.sendKeys(Keys.RETURN);
			logger.info("PASS: s_PerfromGoogleSearch");
			// making the scenario as passed
			aReturnValue = true;
		}
		catch(Exception e) {
			logger.error("FAIL: s_PerfromGoogleSearch: Error occured while executing scenario \n" + e.getMessage());
		}
		logger.info("Scenario: End of s_PerfromGoogleSearch");
		return aReturnValue;
	}
	
}
