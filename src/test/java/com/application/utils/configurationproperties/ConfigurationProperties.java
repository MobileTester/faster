package com.application.utils.configurationproperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ConfigurationProperties {

	static File configFile = null;
	static Properties properties = null;
	static InputStream input = null;
	static Logger logger = LogManager.getLogger(ConfigurationProperties.class.getName());
	
	static {
		try {
			logger.info("Going to create the properties files");
			configFile = new File("src/test/resources/config.properties");
			input = new FileInputStream(configFile);
			properties = new Properties();
			properties.load(input);
			logger.info("Successfully created the config file");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("The file path/name mentioned is not correct");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("I/O exception occured while reading the config file");
			e.printStackTrace();
		}
	}
	
	// Function to read a value from config file based on the key passed
	public static String getProperty(String key) {
		String value = null;
		
		try {
			logger.info("Going to get value for the key - " + key);
			value = properties.getProperty(key);
			logger.info("Key: " + key + " + Value: " + value);
		}
		catch (Exception e) {
			e.printStackTrace(); 
			logger.error("Error occured while reading the key - " + key);
		}
		return value;
	}
	
}
