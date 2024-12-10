package com.driver.appium.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.data.configurationproperties.ConfigurationProperties;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;


public final class AppiumServerUtils {

	static Logger logger = LogManager.getLogger(AppiumServerUtils.class.getName());
	private static Boolean appiumServerProgrammaticStart = null;
	private static String serverUrl = null;
	private static AppiumDriverLocalService appiumService;
	
	// private constructor - This class should not be instantiated
	private AppiumServerUtils() {}
	
	public static String startAppiumServer() {
		String url = null;
		try {
			logger.info("StartStopAppiumServer -> startAppiumServer(): Going to get the Appium server start / url parameters");
			appiumServerProgrammaticStart = Boolean.parseBoolean(ConfigurationProperties.getProperty("appiumServerProgrammaticStart"));
			if(appiumServerProgrammaticStart) {
				logger.info("StartStopAppiumServer -> startAppiumServer(): appiumServerProgrammaticStart is true. Going to start the Appium server in available port.");
				
				AppiumServiceBuilder builder = new AppiumServiceBuilder().withTimeout(Duration.ofSeconds(500))
													.withIPAddress("127.0.0.1")
													// .withArgument(() -> "--use-plugins", "element-wait")
													.usingAnyFreePort()
													// .usingPort(Integer.parseInt(port)) // You can specify .usingPort(4723) for a
													// fixed port
													// .withLogFile(new File("appium.log")) // Optional: Log output to a file - NOT
													// WORKING. FILE IS BLANK
													.withArgument(() -> "--log-level", "debug")
													// two ways to set the local time stamp for Appium logs
													//.withArgument(() -> "--log-timestamp")
													.withArgument(GeneralServerFlag.LOG_TIMESTAMP)
													.withArgument(GeneralServerFlag.LOCAL_TIMEZONE);
													// Not sure what are the side effects of the below parameter. Hence commenting it out.
													//.withArgument(GeneralServerFlag.RELAXED_SECURITY) // Enable advanced features
				
				appiumService = AppiumDriverLocalService.buildService(builder);
				
				// For Appium logs to file
				File logFile = new File("appium-debug.log");
				// this will append the logs across server stop and start
				FileOutputStream logFileStream = new FileOutputStream(logFile, true); // Append mode
				// Redirect logs - This is working
				appiumService.addOutPutStream(logFileStream);
				
				appiumService.start();
				url = appiumService.getUrl().toString();
				logger.info("StartStopAppiumServer -> startAppiumServer(): Appium server started succesfully at: " + url);
			}
			else {
				logger.info("StartStopAppiumServer -> startAppiumServer(): appiumServerProgrammaticStart is false. Getting appiumServerUrl from config file.");
				// getting url from the properties file
				url = ConfigurationProperties.getProperty("appiumServerUrl");
				logger.info("StartStopAppiumServer -> startAppiumServer(): Appium server should be manually started and available at: " + url);
			}
		}
		catch(Exception e) {
			logger.error("StartStopAppiumServer -> startAppiumServer(): Error occured while starting Appium server");
			logger.error(e.getMessage());
		}
		serverUrl = url;
		return url;
	}
	
	public static boolean stopAppiumServer() {
		boolean status = false;
		try {
			logger.info("StartStopAppiumServer -> stopAppiumServer(): Going to stop Appium server at: " + serverUrl);
			// this will clear the streams and full logs will be available, in the currently provided output stream. 
			appiumService.clearOutPutStreams();
			
			if (appiumService != null && appiumService.isRunning()) {
				appiumService.stop();
				status = true;
				logger.info("StartStopAppiumServer -> stopAppiumServer(): Appium server stopped.");
			}
			else {
				logger.warn("StartStopAppiumServer -> stopAppiumServer(): Appium server already stopped or not reachable. Please check: " + serverUrl);
			}
		}
		catch (Exception e) {
			status = false;
			logger.error("StartStopAppiumServer -> stopAppiumServer(): Exception occured.");
		} 
		catch (Error e) {
			status = false;
			logger.info("StartStopAppiumServer -> stopAppiumServer(): Error occured.");
		}
		return status;
	}
	
	public static int getAppiumServerStatus(String serverUrl) {
		Integer statusCode = null;
		try {
			logger.info("StartStopAppiumServer -> getAppiumServerStatus(): Going to check status for Appium server at: " + serverUrl);
			HttpClient client = HttpClient.newHttpClient();
			// Appium 2 status url - http://0.0.0.0:4723/status
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(serverUrl + "status/")).GET().build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			
			statusCode = response.statusCode();
			logger.info("StartStopAppiumServer -> getAppiumServerStatus(): Appium server status: " + statusCode);
		} 
		catch (Exception e) {
			logger.error("StartStopAppiumServer -> getAppiumServerStatus(): Exception occured while checking server status at: " + serverUrl);
			logger.error(e.getMessage());
		}
		return statusCode;
	}
}
