package com.driver.rolelocator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;


enum DriverType {DESKTOP, MOBILE};
enum MobileDriverType {ANDROID, IOS}



public final class FindByRole {
	
	private DriverType driverTypePassed; 
	private MobileDriverType mobileDriverTypePassed; 
	
	Logger logger = LogManager.getLogger(this.getClass().getName()); 
	private WebDriver driver = null;
	
	public FindByRole(WebDriver driver) {
		
		if(driver != null) {
			this.driver = driver;
			if(this.driver instanceof AppiumDriver) {
				driverTypePassed = DriverType.MOBILE;
				if(this.driver instanceof AndroidDriver) {
					mobileDriverTypePassed = MobileDriverType.ANDROID;
					logger.info("FindByRole: Android Driver passed");
				}
				else if(this.driver instanceof IOSDriver){
					mobileDriverTypePassed = MobileDriverType.IOS;
					logger.info("FindByRole: IOS Driver passed");
				}
			}
			// Can explicitly check for the supported browser drivers
			else if(this.driver instanceof WebDriver) {
				driverTypePassed = DriverType.DESKTOP;
				mobileDriverTypePassed = null;
				logger.info("FindByRole: WebDriver passed");
			}
		}
	}
	
	// For Web & Mobile (native & web) - 
	// attribute is expected to be prepended with @ for valid attributes
	// 		if it is text(), just pass text() as the attribute without @ prepended
	// if value is expected to be an exact match, send value as it is
	//		value can also be sent as - contains('value') OR starts-with('value') OR ends-with('value') OR matches('regex-expression')
	public WebElement with(String tag, String attribute, String value, int index) {
		
        String regex1 = "contains\\(['\"](.*)['\"]\\)";
        String regex2 = "starts-with\\(['\"](.*)['\"]\\)";
        String regex3 = "ends-with\\(['\"](.*)['\"]\\)";
        String regex4 = "matches\\(['\"](.*)['\"]\\)";
		
		WebElement element = null;
		By by = null;
		String locatorString = null;
		Pattern pattern = null;
		Matcher matcher = null;
		try {
			
			if(value.matches(regex1)) {
				pattern = Pattern.compile(regex1);
				matcher = pattern.matcher(value);
				// extracting the required part only
				String extractedValue = matcher.group(1);
				locatorString = "//%s[contains(%s, '%s')][%d]".formatted(tag, attribute, extractedValue);
			}
			else if(value.matches(regex2)) {
				pattern = Pattern.compile(regex2);
				matcher = pattern.matcher(value);
				// extracting the required part only
				String extractedValue = matcher.group(1);
				locatorString = "//%s[starts-with(%s, '%s')][%d]".formatted(tag, attribute, extractedValue);
			}
			else if(value.matches(regex3)) {
				pattern = Pattern.compile(regex3);
				matcher = pattern.matcher(value);
				// extracting the required part only
				String extractedValue = matcher.group(1);
				locatorString = "//%s[ends-with(%s, '%s')][%d]".formatted(tag, attribute, extractedValue);

			}
			else if(value.matches(regex4)) {
				pattern = Pattern.compile(regex4);
				matcher = pattern.matcher(value);
				// extracting the required part only
				String extractedValue = matcher.group(1);
				locatorString = "//%s[matches(%s, '%s')][%d]".formatted(tag, attribute, extractedValue);
			}
			else {
				locatorString = "//%s[@%s='%s'][%d]".formatted(tag, attribute, value, index);
			}
			
			by = By.xpath(locatorString);
			logger.info("FindByRole: with(): Going to find element with locator - ", locatorString); 
			element = driver.findElement(by);
			logger.info("FindByRole: with(): Successfully found element");
		} 
		catch (Exception e) {
			logger.error("FindByRole: with(): Error Occured while finding the element with locator- ", locatorString);
		}
		return element;
	}
	
	// For Web & Mobile (native & web)
	public WebElement withAttribute(String attribute, String value) {
		return with("*", attribute, value, 1);
	}
	// For Web & Mobile (native & web)
	public WebElement withTag(String tag, String value) {
		return with(tag, "*", value, 1);
	}
	
	// For Web & Mobile (native & web)
	public WebElement withId(String tag, String value) {
		return with(tag, "id", value, 1);
	}
	// For Web & Mobile (native & web)
	public WebElement withId(String value) {
		return withAttribute("id", value);
	}
	
	// For Web & Mobile (native & web)
	public WebElement withName(String tag, String value) {
		return with(tag, "name", value, 1);
	}
	// For Web & Mobile (native & web)
	public WebElement withName(String value) {
		return withAttribute("name", value);
	}
	
	// For Web & Mobile (native & web)
	public WebElement withClass(String tag, String value) {
		return with(tag, "class", value, 1);
	}
	// For Web & Mobile (native & web)
	public WebElement withClass(String value) {
		return withAttribute("class", value);
	}
	
	// For Web (Desktop and Mobile)
	public WebElement withVisibleText(String tag, String value) {
		return with(tag, "text()", value, 1);
	}
	
	// For Web (Desktop and Mobile)
	public WebElement withVisibleText(String value) {
		return withVisibleText("*", value);
	}
	
	// For Android (native)
	public WebElement withResourceId(String tag, String value) {
		if(mobileDriverTypePassed == MobileDriverType.ANDROID) {
			return with(tag, "resource-id", value, 1);
		}
		else {
			logger.error("FindByRole: withResourceId() function only supported in Android");
			return null;
		}
	}
	// For Android (native)
	public WebElement withResourceId(String value) {
		return withResourceId("*", value);
	}
	
	// For Android (native)
	public WebElement withContentDesc(String tag, String value) {
		if(mobileDriverTypePassed == MobileDriverType.ANDROID) {
			return with(tag, "content-desc", value, 1);
		}
		else {
			logger.error("FindByRole: withContentDesc() function only supported in Android");
			return null;
		}
	}
	// For Android (native)
	public WebElement withContentDesc(String value) {
		return withContentDesc("*", value);
	}
	
	// For Android (native)
	public WebElement withVisibleTextForAndroid(String tag, String value) {
		if(mobileDriverTypePassed == MobileDriverType.ANDROID) {
			return with(tag, "text", value, 1);
		}
		else {
			logger.error("FindByRole: withVisibleTextForAndroid() function only supported in Android");
			return null;
		}
	}
	// For Android (native)
	public WebElement withVisibleTextForAndroid(String value) {
		return withVisibleTextForAndroid("*", value);
	}
	
	
	// For IOS (native)
	public WebElement withVisibleTextForIOS(String tag, String value) {
		if(mobileDriverTypePassed == MobileDriverType.IOS) {
			return with(tag, "label", value, 1);
		}
		else {
			logger.error("FindByRole: withVisibleTextForIOS() function only supported in IOS");
			return null;
		}
		
	}
	// For IOS (native) 
	public WebElement withVisibleTextForIOS(String value) { 
		return withVisibleTextForIOS("*", value);
	}
	
}
