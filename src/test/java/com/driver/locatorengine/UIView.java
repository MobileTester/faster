package com.driver.locatorengine;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

// This class is similar to a Web Page or Mobile Screen, with JSON string representation of Elements
public final class UIView {
	
	private static Logger logger = LogManager.getLogger(UIView.class.getName());
	
	// All locator strategies from different platforms(Android, iOS etc) should be here in the array. 
	private static String[] supportedMobileLocatorStrategies = {"id", "accessibilityId", "androidUIAutomator", "name", "xpath"};
	private static String[] supportedWebLocatorStrategies = {"id", "name", "xpath"};
	
	private UIViewFields fields;

    
    public UIView(String path) {
    	fields = new UIViewFields(UIConstructViewFromJson.getView(path));
	}
    
    
    // get the WebElement (can be Web page element or Mobile screen element)
    // optionalString - are for dynamic locators, where the %s in the locator will be replaced with it.
    // Note: Only %s is supported in optionalString
    public WebElement getUIElement(String key, WebDriver driver, String... optionalString) {
    	logger.info("UIView -> getElement(): Entered the function getUIElement");
    	WebElement element = null;
    	UIElementString elementString = null;
    	// checking the validity of the key passed
		if(fields.get(key) == null) {
			logger.error("UIView -> getElement(): Invalid key passed to get the mobile element: key - " + key);
			return null;
		}
		else {
			elementString = fields.get(key);
			
			// Checking the validity of the elementString
			if((elementString.getLocator() != null) && (elementString.getLocator().trim().length() > 0) && (elementString.getStrategy() != null) && (elementString.getStrategy().trim().length() > 0) ) {
				elementString.setLocator(elementString.getLocator().trim());
				elementString.setStrategy(elementString.getStrategy().trim()); 
				// checking whether the passed locator strategy is supported either in Mobile or in Web
				if(List.of(supportedMobileLocatorStrategies).indexOf(elementString.getStrategy()) == -1 && List.of(supportedWebLocatorStrategies).indexOf(elementString.getStrategy()) == -1) {
					logger.error("UIView -> getElement(): Invalid strategy: %s".formatted(elementString.getStrategy()));  
					logger.error("UIView -> getElement(): If the strategy is supported, please add it in the array.");
					return null;
				}
				logger.info("UIView -> getElement(): Going to find UI Element: " + elementString);
				
				
				// replacing the dynamic string parameters if present
				if(optionalString != null && optionalString.length > 0) {
					logger.info("UIView -> getElement(): Dynamic string parameters are sent: " + Arrays.toString(optionalString));
					
					//  now checking the number of place holders in the locator
//					int numberOfPlaceHolders = elementString.getLocator().split("%s", -1).length - 1;
					
					int numberOfPlaceHolders = 0;
			        // Regular expression to match %s
			        Pattern pattern = Pattern.compile("%s");
			        Matcher matcher = pattern.matcher(elementString.getLocator());
			        int count = 0;
			        while (matcher.find()) {
			        	numberOfPlaceHolders++;
			        }
					
					if(numberOfPlaceHolders == optionalString.length) {
						logger.info("UIView -> getElement(): Replacing the place holders with the dynamic strings");
						Object[] optionalStringArray = optionalString;
						elementString.setLocator(elementString.getLocator().formatted(optionalStringArray)); 
					}
					else {
						logger.info("UIView -> getElement(): Incorrect number of var args passed. Expected %2d parameters. But passed %2d parameters.".formatted(numberOfPlaceHolders, optionalString.length));
					}
				}
			}
			else {
				logger.error("UIView -> getElement(): Invalid locator & strategy for the key - %s. Please check corresponding JSON file".formatted(key));
				return null;
			}

			
		}

		// Finding the element with the locator read from the file and varargs replaced if applicable 
    	try {
			// find the instance type of the WebDriver - AppiumDriver, WebDriver etc
    		
    		// this is a mobile driver for screens
    		if(driver instanceof AppiumDriver) {
    			logger.info("UIView -> getElement(): Going to get the Mobile screen element for the key - " + key);
    			AppiumDriver appiumDriver = (AppiumDriver) driver;
    			element = getMobileScreenElement(elementString, appiumDriver);
    		}
    		// this is a web driver for pages
    		else {
    			logger.info("UIView -> getElement(): Going to get the Web page element for the key - " + key);
    			element = getWebPageElement(elementString, driver);
    		}
    		
		} 
    	catch (Exception e) {
    		logger.error("UIView -> getElement(): Exception occured");
    		logger.error(e.getMessage());
		}
    	logger.info("UIView -> getElement(): Exiting the function getUIElement");
    	return element;
    }
    
    // overloaded wrapper function with no varargs
    public WebElement getUIElement(String key, WebDriver driver) {
    	String[] vargargs = null;
    	return getUIElement(key, driver, vargargs); 
    }

    // For web
    private WebElement getWebPageElement(UIElementString elementString, WebDriver driver) {
    	WebElement element = null;
    	try {
    		String locator = elementString.getLocator();
    		String strategy = elementString.getStrategy();
    		logger.info("UIView -> getWebPageElement(): Going to get the Web element for: locator: %s, strategy: %s".formatted(locator, strategy));
    		// inner try block for finding element
    		try {
    			// if more strategy support is required, add a new case here. Please update the array - supportedWebLocatorStrategies also
    			element = switch(strategy) {
    				case "id" -> driver.findElement(By.id(locator));
    				case "name" -> driver.findElement(By.name(locator));
    				case "xpath" -> driver.findElement(By.xpath(locator)); 
    				default -> null;
    			};
			} 
    		catch (Exception e) {
    			element = null;
    			logger.error("UIView -> getWebPageElement(): Web Element NOT found for the locator: %s, strategy %s".formatted(locator, strategy));
			}
		} 
    	catch (Exception e) {
			logger.error("UIView -> getWebPageElement(): Exception occured.");
			logger.error(e.getMessage());
		}
    	return element;
	}

    // Generic function for Mobile Platforms
	private WebElement getMobileScreenElement(UIElementString elementString, AppiumDriver appiumDriver) {
		WebElement element = null;
		
		try { 
			// For Android Platform
			if(appiumDriver instanceof AndroidDriver) {
				element = getAndroidElement(elementString, (AndroidDriver) appiumDriver);
			} // if(appiumDriver instanceof AndroidDriver) 

			/**  
			 * ToDo: Add Other Platform support here 
			 * */
			else {
				logger.error("UIView -> getMobileScreenElement(): Unsupported driver type.");
				return null;
			}
		}
		catch (Exception e) {
			logger.error("UIView -> getMobileScreenElement(): Exception occured.");
			logger.error(e.getMessage());
		}
		return element;
	}
	
	// Currently supported strategies (case sensitive) - id, accessibilityId, androidUIAutomator, name, xpath
    // null will be returned in other cases. 
	private WebElement getAndroidElement(UIElementString elementString, AndroidDriver androidDriver) {
		WebElement element = null;
		String locator = elementString.getLocator();
		String strategy = elementString.getStrategy();
		logger.info("UIView -> getAndroidElement(): Going to get the Android element for: locator: %s, strategy: %s".formatted(locator, strategy));
		// inner try block for finding element
		try {
			// if more strategy support is required, add a new case here. Please update the array - supportedMobileLocatorStrategies also
			element = switch(strategy) {
				case "id" -> androidDriver.findElement(AppiumBy.id(locator));
				case "accessibilityId" -> androidDriver.findElement(AppiumBy.accessibilityId(locator));
				case "androidUIAutomator" -> androidDriver.findElement(AppiumBy.androidUIAutomator(locator));
				case "name" -> androidDriver.findElement(AppiumBy.name(locator));
				case "xpath" -> androidDriver.findElement(AppiumBy.xpath(locator));
				default -> null;
			};
		} 
		catch (Exception e) {
			element = null;
			logger.error("UIView -> getAndroidElement(): Android Element NOT found for the locator: %s, strategy %s".formatted(locator, strategy));
		}
		logger.info("UIView -> getAndroidElement(): Android Element found for the locator: %s, strategy %s".formatted(locator, strategy));
		return element;
	}

	@Override
    public String toString() {
        return "{" +
                "fields=" + fields +
                '}';
    }
    
}
