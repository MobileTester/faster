package com.driver.factory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;

import com.data.configurationproperties.ConfigurationProperties;
import com.driver.listener.EventHandlerForWeb;

public final class WebDriverFactory extends DriverFactory {
	
	public WebDriverFactory(String batchExecutionName) {
		
		logger = LogManager.getLogger(this.getClass().getName());
		logger.info("Created the WebDriverFactory for the Batch Execution:- " + batchExecutionName);
	}


	public WebDriver getDriver(String browserName) {

		WebDriver driver = null;
		EventHandlerForWeb eventHandler = null;
		

		logger.info("Going to initialize driver with the passed DesiredCapabilities");

		try {

			browserName = browserName.toLowerCase();
			int driverInitRetryCount = -1;
			switch (browserName) {

			case "chrome":
				// some times fail on the first attempt.
				driverInitRetryCount = Integer.parseInt(ConfigurationProperties.getProperty("driverInitRetryCount"));
				WebDriver originalDriver = null;
				for (int i = 0; i < driverInitRetryCount; i++) {
					try {
						System.setProperty("webdriver.http.factory", "jdk-http-client");
						originalDriver = new ChromeDriver();
						eventHandler = new EventHandlerForWeb();
						driver = new EventFiringDecorator<WebDriver>(eventHandler).decorate(originalDriver); 
						break;
					} 
					catch (Error e) {
						originalDriver = null;
						eventHandler = null;
						driver = null;
						logger.error(e.getMessage());
					}
					catch (Exception e) {
						originalDriver = null;
						eventHandler = null;
						driver = null;
						logger.error(e.getMessage());
					}
				}
				break;
			default:
				logger.error("Web Unsuppported Browser: " + browserName);
				System.exit(0);
			}

		} 
		catch (Exception e) {
			logger.error(e.getMessage());
		}
		if(driver == null) {
			logger.error("Driver initialization failed");
		}
		return driver;
	}

	@Override
	public WebDriver getDriver(String deviceName, String serverUrl, Capabilities capabilities) {
		// TODO Auto-generated method stub
		return null;
	}

}
