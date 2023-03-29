package com.application.utils.webdriverlistener;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.openqa.selenium.support.events.WebDriverListener;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.application.utils.configurationproperties.ConfigurationProperties;

public class EventHandlerNew implements WebDriverListener {

	private Logger logger = LogManager.getLogger(EventHandlerNew.class.getName());
	private static Integer elementRefreshPollingInterval = null;
	private static Integer elementRefreshTimeout = null;
	private static Integer smallWait = null;
	private static Integer verySmallWait = null;

	static {
		// Getting Event Handler specific properties
		elementRefreshPollingInterval = Integer
				.parseInt(ConfigurationProperties.getProperty("elementRefreshPollingInterval"));
		elementRefreshTimeout = Integer.parseInt(ConfigurationProperties.getProperty("elementRefreshTimeout"));
		smallWait = Integer.parseInt(ConfigurationProperties.getProperty("smallWait"));
		verySmallWait = Integer.parseInt(ConfigurationProperties.getProperty("verySmallWait"));
	}
	
	@Override
	public void beforeFindElement(WebDriver driver, By by) {
		
		// Checking whether the element is available and refreshed
		if (waitForElementToBeRefreshed(by, driver)) {
			// Performing the scroll if the element is available
			jsScrollToAWebElement(by, driver);
		}	
		// TODO Auto-generated method stub
		WebDriverListener.super.beforeFindElement(driver, by);
	}
	
	
	
	
	/**********************************************************************************************************************/
	// Function for waiting for the element to be refreshed.
	private boolean waitForElementToBeRefreshed(By by, WebDriver driver) {
		boolean isElementActive = false;
		String attributeValue = null;
		try {
			// Just to check whether the element is radio button or checkbox
			attributeValue = getAttributeOfAWebElement(by, "type", driver);
			Boolean stalenessStatus = null;
			// Iteration for waiting for the element to be refreshed
			for (int i = 0; i < (elementRefreshTimeout / (elementRefreshPollingInterval * 2)); i++) {
				// checking whether the element is loaded properly.

				// elementRefreshPollingInterval seconds will be spent here
				if (attributeValue != null
						&& (attributeValue.equalsIgnoreCase("radio") || attributeValue.equalsIgnoreCase("checkbox")))
					stalenessStatus = checkStalenessOfRadioButtonOrCheckBox(by, driver, elementRefreshPollingInterval);
				else
					stalenessStatus = checkStalenessOfWebElement(by, driver, elementRefreshPollingInterval);

				if (stalenessStatus != null && stalenessStatus.booleanValue() == false) {
					logger.debug("waitForElementToBeRefreshed(): Element is refreshed and active");
					isElementActive = true;
					break;
				}
				// elementRefreshPollingInterval seconds will be spent here
				else {
					logger.debug("waitForElementToBeRefreshed(): Waiting for element to be refreshed");
					waitForGivenSeconds(elementRefreshPollingInterval);
				}
			} // End for for loop

		} catch (Exception e) {
			logger.debug("waitForElementToBeRefreshed(): Error occured");
		}

		if (!isElementActive) {
			logger.debug("waitForElementToBeRefreshed(): Element not active within " + elementRefreshPollingInterval
					+ " seconds.");
		}
		return isElementActive;
	}

	// Function to get the passed attribute of a WebElement
	private String getAttributeOfAWebElement(By by, String attribute, WebDriver driver) {
		String attributeValue = null;
		try {
			attributeValue = driver.findElement(by).getAttribute(attribute);
			logger.debug("getAttributeOfAWebElement(): Attribute value for the attribute: " + attribute + " is:- "
					+ attributeValue);
		} catch (Exception e) {
			logger.debug("getAttributeOfAWebElement(): Error occured");
		}
		return attributeValue;
	}

	// This function will return false only if the WebElement is live and active
	// Else it will return true or null
	private Boolean checkStalenessOfWebElement(By by, WebDriver driver, int timeOut) {
		Boolean isStale = null;
		WebElement temp = null;
		try {
			// Defining the wait object for the timeout passed
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut)); 
			// A stale Web Element will throw exception here
			temp = wait.until(ExpectedConditions.visibilityOf(driver.findElement(by)));
			// if no exception is thrown, isStale is set as false
			if (temp != null) {
				isStale = false;
				temp = null;
			}
		} catch (StaleElementReferenceException e) {
			logger.debug("checkStalenessOfWebElement: Stale exception thrown for the WebElement: " + by.toString());
			isStale = true;
		} catch (Exception e) {
			logger.debug(
					"checkStalenessOfWebElement: Some other exception thrown for the WebElement: " + by.toString());
			logger.debug("Exception Message: " + e.getMessage());
			isStale = null;
		} finally {
			temp = null;
		}
		return isStale;
	}

	// For checkboxes and radio buttons, ExpectedConditions.visibilityOf wont work.
	// So here ExpectedConditions.presenceOfElementLocated is used
	private Boolean checkStalenessOfRadioButtonOrCheckBox(By by, WebDriver driver, int timeOut) {
		Boolean isStale = null;
		WebElement temp = null;
		try {
			// Defining the wait object for the timeout passed
			WebDriverWait wait = new WebDriverWait(driver,  Duration.ofSeconds(timeOut));
			// A stale Web Element will throw exception here
			temp = wait.until(ExpectedConditions.presenceOfElementLocated(by));
			// if no exception is thrown, isStale is set as false
			if (temp != null) {
				isStale = false;
				temp = null;
			}
		} catch (StaleElementReferenceException e) {
			logger.debug("checkStalenessOfRadioButtonOrCheckBox: Stale exception thrown for the WebElement: "
					+ by.toString());
			isStale = true;
		} catch (Exception e) {
			logger.debug("checkStalenessOfRadioButtonOrCheckBox: Some other exception thrown for the WebElement: "
					+ by.toString());
			isStale = null;
		} finally {
			temp = null;
		}
		return isStale;
	}

	// This function scrolls towards a WebElement using By
	public boolean jsScrollToAWebElement(By by, WebDriver driver) {
		boolean aReturnValue = false;
		JavascriptExecutor js = null;
		try {
			logger.debug("jsScrollToAWebElement(): Going to perform scroll");
			js = (JavascriptExecutor) driver;
			/**
			 * 6 Jun 2018: In documentation it is mentioned that - scrollIntoViewIfNeeded,
			 * will not work for Firefox browser. But when checked, it is working there
			 * also. Hence keeping the below commented line for backup for Firefox browser.
			 * If 'scrollIntoViewIfNeeded' is a problem for Firefox, create a different
			 * Listener for Firefox and allocate the same during the Driver initialization.
			 */
//			js.executeScript("arguments[0].scrollIntoView(false);", driver.findElement(by));
			js.executeScript("arguments[0].scrollIntoViewIfNeeded(false);", driver.findElement(by));
			// 23 Jan 2020: This JS scroll is also added, as sometimes previous scroll is not happening. 
			js.executeScript("arguments[0].scrollIntoView();", driver.findElement(by));
			logger.debug("jsScrollToAWebElement(): Scroll action performed successfully");
			aReturnValue = true;

//			Coordinates cor=((Locatable)driver.findElement(by)).getCoordinates();
//			cor.inViewPort();
		} catch (Exception e) {
			logger.debug("jsScrollToAWebElement(): Error occured" + e.getMessage());
		} finally {
			js = null;
		}
		return aReturnValue;
	}

	// Function to wait for give seconds
	private boolean waitForGivenSeconds(int seconds) {
		boolean aReturnValue = false;
		try {
			logger.debug("waitForGivenSeconds: Going to wait for " + seconds + " seconds");
			TimeUnit.SECONDS.sleep(seconds);
			aReturnValue = true;
			logger.debug("waitForGivenSeconds: " + seconds + " seconds wait over");
		} catch (Exception e) {
			logger.debug("waitForGivenSeconds: Error occured: \n" + e.getMessage());
		}
		return aReturnValue;
	}
	/**********************************************************************************************************************/

	
}
