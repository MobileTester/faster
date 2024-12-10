package com.scripting.scenarios;

import java.net.URI;
import java.net.URL;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.driver.locatorengine.UIConstructViewFromJson;
import com.driver.locatorengine.UIView;
import com.scripting.screens.mobile.CalculatorScreen;
import com.scripting.screens.web.GooglePageSearch;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;

public class CalculatorScenario {

	private Logger logger = LogManager.getLogger(this.getClass().getName());
	private AppiumDriver appiumDriverForScenario;
	
	public CalculatorScenario(AppiumDriver appiumDriver) {
		logger.info("ScenarioClass: "+ this.getClass().getName()+ " instantiated"); 
		appiumDriverForScenario = appiumDriver;
		
	}
	
	/**
	 * @description - Scenario to perform addition in calculator screen
	 * @startpoint - Calculator screen
	 * @endpoint - Calculator with addition result
	 * @param1 - number1
	 * @param2 - number2
	 * @return - result
	 * @author nikhil_narayanan
	 */
	public Integer s_PerformAdditionOfTwoIntegers(Integer number1, Integer number2) {
		logger.info("Scenario: Going to execute s_PerformAdditionOfTwoIntegers: %-5d, %-5d".formatted(number1, number2)); 
		int result = -1;
		CalculatorScreen calculatorScreen = null;
		try {
			// Initializing the required page files
			calculatorScreen = new CalculatorScreen(appiumDriverForScenario);
			logger.info("s_PerformAdditionOfTwoIntegers: CalculatorScreen initialized");
			logger.info("s_PerformAdditionOfTwoIntegers: Going to perform addition of numbers");
			// tapping the clear button
			// calculatorScreen.clearButton.click();
			appiumDriverForScenario.findElement(AppiumBy.accessibilityId("clear")).click();
			// typing number1 to the keypad
			String[] number1Split = number1.toString().split("");
			for(String x: number1Split) {
				// tapping on a digit
				appiumDriverForScenario.findElement(AppiumBy.id(calculatorScreen.digitButtonIdLocator.formatted(x))).click();
			}
			// tapping on + button
			calculatorScreen.plusButton.click();
			String[] number2Split = number2.toString().split("");
			for(String x: number2Split) {
				// tapping on a digit
				appiumDriverForScenario.findElement(AppiumBy.id(calculatorScreen.digitButtonIdLocator.formatted(x))).click();
			}
			String resultString = calculatorScreen.result.getAttribute("content-desc").split(" ")[1];
			result = Integer.parseInt(resultString);
			
			logger.info("PASS: s_PerformAdditionOfTwoIntegers: %-5d".formatted(result)); 
		}
		catch(Exception e) {
			logger.error("FAIL: s_PerformAdditionOfTwoIntegers: Error occured while executing scenario \n" + e.getMessage());
		}
		logger.info("Scenario: End of s_PerformAdditionOfTwoIntegers");
		return result;
	}
	
	/**
	 * @description - Scenario to perform addition in calculator screen
	 * @startpoint - Calculator screen
	 * @endpoint - Calculator with addition result
	 * @param1 - number1
	 * @param2 - number2
	 * @return - result
	 * @author nikhil_narayanan
	 */
	public Integer s_PerformAdditionOfTwoIntegers2(Integer number1, Integer number2) {
		logger.info("Scenario: Going to execute s_PerformAdditionOfTwoIntegers2: %-5d, %-5d".formatted(number1, number2)); 
		int result = -1;
		try {
			// Declare the required pages for the scenario
			UIView calculatorScreen = new UIView("mobile-screens/calculator-screen");
			logger.info("s_PerformAdditionOfTwoIntegers2: CalculatorScreen initialized");
			logger.info("s_PerformAdditionOfTwoIntegers2: Going to perform addition of numbers");
			WebElement clearButton = calculatorScreen.getUIElement("clearButton", appiumDriverForScenario);
			clearButton.click();
			// typing number1 to the keypad
			String[] number1Split = number1.toString().split("");
			for(String x: number1Split) {
				calculatorScreen.getUIElement("digitButtonIdLocator", appiumDriverForScenario, x).click(); 
			}
			
			WebElement plusButton = calculatorScreen.getUIElement("plusButton", appiumDriverForScenario);
			plusButton.click();
			
			String[] number2Split = number2.toString().split("");
			for(String x: number2Split) {
				calculatorScreen.getUIElement("digitButtonIdLocator", appiumDriverForScenario, x).click(); 
			}
			String resultString = calculatorScreen.getUIElement("result", appiumDriverForScenario).getAttribute("content-desc").split(" ")[1];
			result = Integer.parseInt(resultString);
			
			logger.info("PASS: s_PerformAdditionOfTwoIntegers2: %-5d".formatted(result)); 
			
		}
		catch(Exception e) {
			logger.error("FAIL: s_PerformAdditionOfTwoIntegers2: Error occured while executing scenario \n" + e.getMessage());
		}
		logger.info("Scenario: End of s_PerformAdditionOfTwoIntegers2");
		return result;
	}
	
	public static void main(String[] args) {
		Integer number = 12345;
		List.of(number.toString().split("")).forEach(x -> System.out.println(" " + x));  
	}
	
	
}
