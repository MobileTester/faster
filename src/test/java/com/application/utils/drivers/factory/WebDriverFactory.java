package com.application.utils.drivers.factory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.application.utils.configurationproperties.ConfigurationProperties;
import com.application.utils.webdriverlistener.EventHandler;
import com.application.utils.webdriverlistener.EventHandlerNew;

public class WebDriverFactory {

	private static Logger logger = LogManager.getLogger(WebDriverFactory.class.getName());

	public static WebDriver getDriver(String browserName) {

		WebDriver driver = null;
		EventHandlerNew eventHandler = null;
		

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
						eventHandler = new EventHandlerNew();

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
				logger.error("Unsuppported Browser: " + browserName);
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
}
