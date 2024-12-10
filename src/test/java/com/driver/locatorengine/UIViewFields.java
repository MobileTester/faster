package com.driver.locatorengine;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Class should be immutable to avoid side effects
public final class UIViewFields {

	private Logger logger = LogManager.getLogger(this.getClass().getName());
	
	// this field should be immutable. 
	private Map<String, UIElementString> fields;
	
	public UIViewFields(Map<String, UIElementString> fields) {
		this.fields = fields;
		logger.info("UIViewFields -> constructor(): UIViewFields, with fields, created successfully");
	}
	
	// creating a deep copy of the value(UIElementString) for the passed key, to prevent side effects. 
	public UIElementString get(String key) {
		UIElementString elementString = null;
		
		try {
			logger.info("UIViewFields -> get(): Going to retrieve the element string for the key: " + key);
			elementString = new UIElementString(fields.get(key));
			if(elementString.getLocator() != null && elementString.getStrategy() != null) {
				logger.info("UIViewFields -> get(): element string successfully retrived for the key: %s, value: %s".formatted(key, elementString)); 
			}
			else {
				elementString = null;
				logger.error("UIViewFields -> get(): Invalid key passed to get the mobile element: key - " + key);
			}
			
		}
		catch (Exception e) {
			elementString = null;
			logger.error("UIViewFields -> get(): Invalid key passed to get the mobile element: key - " + key);
		}
		
		
		return elementString;
	}
	
	// Creating  the deep copy for fields and returning
	public Map<String, UIElementString> getFields() {
		
		Map<String, UIElementString> fieldsCopy = new HashMap<String, UIElementString>();
		try {
			logger.info("UIViewFields -> getFields(): Going to get copy of fields");
			for(Map.Entry<String, UIElementString> entry: fields.entrySet() ) {
				fieldsCopy.put(entry.getKey(), new UIElementString(entry.getValue())); 
			}
			logger.info("UIViewFields -> getFields(): Returning the copy of fields: ");
			logger.info(fieldsCopy);
		} 
		catch (Exception e) {
			fieldsCopy = null;
			logger.error("UIViewFields -> getFields(): Error occured while creating field copy.");
		}
		return fieldsCopy;
	}
}
