package com.application.utils.appconfig;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.application.utils.configurationproperties.ConfigurationProperties;

public class CommonConfigParameters {

	private static Logger logger = LogManager.getLogger(CommonConfigParameters.class.getName());
	public static Integer verySmallWait = null;
	public static Integer smallWait = null;	
	public static Integer mediumWait = null;	
	public static Integer longWait = null;	
	public static Integer veryLongWait = null;	
	public static Integer timeOutForElementPolling = null;	
	public static Integer maxRetryCount = null;	
	public static String testDataFolder = null;	

	// to prevent instantiation
	private CommonConfigParameters() {}
	
	static {
		try{
			// small wait value
			verySmallWait = Integer.parseInt(ConfigurationProperties.getProperty("verySmallWait"));
			smallWait = Integer.parseInt(ConfigurationProperties.getProperty("smallWait"));
			mediumWait = Integer.parseInt(ConfigurationProperties.getProperty("mediumWait"));
			longWait = Integer.parseInt(ConfigurationProperties.getProperty("longWait"));
			veryLongWait = Integer.parseInt(ConfigurationProperties.getProperty("veryLongWait"));
			timeOutForElementPolling = Integer.parseInt(ConfigurationProperties.getProperty("timeOutForElementPolling"));
			maxRetryCount = Integer.parseInt(ConfigurationProperties.getProperty("maxRetryCount"));
			// Getting Test Data folder
			testDataFolder = ConfigurationProperties.getProperty("testDataFolder");
		}
		catch(Exception e) {
			logger.error("Error occured while reading config values: \n" + e.getMessage());
			logger.error("Quitting the execution");
			System.exit(0);
		}
	}
	
}
