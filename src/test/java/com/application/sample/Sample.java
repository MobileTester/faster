package com.application.sample;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.application.utils.drivers.factory.WebDriverFactory;

public class Sample {

	public static void main(String[] args) {
		WebDriver chromeDriver = WebDriverFactory.getDriver("Chrome");
		chromeDriver.get("https://www.google.com/");
		

	}
	
	// Setup a Base class with TestNG annotations - DONE
		
	// Setup the Data Provider annotation - DONE
	
	// Create Sample testcase with Screenplay Design pattern
	
	// Create Listeners for proper Object synchronization  

}
