package com.application.utils.drivers.factory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.application.utils.configurationproperties.ConfigurationProperties;

public class WebDriverFactory {

	private static Logger logger = LogManager.getLogger(WebDriverFactory.class.getName());

	public static WebDriver getDriver(String browserName) {

		WebDriver driver = null;

		logger.info("Going to initialize driver with the passed DesiredCapabilities");

		try {

			browserName = browserName.toLowerCase();
			int driverInitRetryCount = -1;
			switch (browserName) {

			case "chrome":
				// some times fail on the first attempt.
				driverInitRetryCount = Integer.parseInt(ConfigurationProperties.getProperty("driverInitRetryCount"));

				for (int i = 0; i < driverInitRetryCount; i++) {
					try {
						driver = new ChromeDriver();
						break;
					} catch (Error e) {

					} catch (Exception e) {

					}
				}
				break;
			default:
				logger.error("Unsuppported Browser: " + browserName);
				System.exit(0);
			}

		} 
		catch (Exception e) {

		}
		if(driver == null) {
			logger.error("Driver initialization failed");
		}
		return driver;
	}
}
