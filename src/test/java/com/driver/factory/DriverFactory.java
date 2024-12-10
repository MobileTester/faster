package com.driver.factory;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

public sealed abstract class DriverFactory permits WebDriverFactory, MobileDriverFactory{

	protected Logger logger;
	
	public abstract WebDriver getDriver(String browserName);
	
	public abstract WebDriver getDriver(String deviceName, String serverUrl, Capabilities capabilities);
	
}
