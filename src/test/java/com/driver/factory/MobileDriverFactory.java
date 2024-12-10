package com.driver.factory;

import java.net.URI;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.http.ClientConfig;
import org.openqa.selenium.remote.http.HttpClient;
import org.openqa.selenium.remote.http.jdk.JdkHttpClient;
import org.openqa.selenium.support.events.EventFiringDecorator;

import com.data.configurationproperties.ConfigurationProperties;
import com.driver.listener.EventHandlerForWeb;

import io.appium.java_client.android.AndroidDriver;

public final class MobileDriverFactory extends DriverFactory {

	public MobileDriverFactory(String batchExecutionName) {
		
		logger = LogManager.getLogger(this.getClass().getName());
		logger.info("Created the MobileDriverFactory for the Batch Execution:- " + batchExecutionName);
	}

	public WebDriver getDriver(String deviceName, String serverUrl, Capabilities capabilities) {

		WebDriver driver = null;
		logger.info("Going to initialize driver with the passed DesiredCapabilities");

		try {

			deviceName = deviceName.toLowerCase();
			int driverInitRetryCount = -1;
			switch (deviceName) {

			case "android":
				// some times fail on the first attempt.
				driverInitRetryCount = Integer.parseInt(ConfigurationProperties.getProperty("driverInitRetryCount"));
				for (int i = 0; i < driverInitRetryCount; i++) {
					try {
						// Create ClientConfig
						HttpClient.Factory httpClientFactory = new JdkHttpClient.Factory();
						
						driver = new AndroidDriver(new URI(serverUrl).toURL(), capabilities);
						break;
					} 
					catch (Error e) {
						driver = null;
						logger.error(e.getMessage());
					}
					catch (Exception e) {
						driver = null;
						logger.error(e.getMessage());
					}
				}
				break;
			/** 
			 * ToDo: Write driver initializations for other mobile platforms as additional cases
			 * */	
			default:
				logger.error("Unsuppported Mobile Platform: " + deviceName);
				System.exit(0);
			}

		} 
		catch (Exception e) {
			logger.error(e.getMessage());
		}
		if(driver == null) {
			logger.error("Mobile Driver initialization failed");
		}
		return driver;
	}

	@Override
	public WebDriver getDriver(String browserName) {
		// TODO Auto-generated method stub
		return null;
	}
}
