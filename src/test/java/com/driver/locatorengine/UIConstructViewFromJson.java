package com.driver.locatorengine;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class UIConstructViewFromJson {

	private static Logger logger = LogManager.getLogger(UIConstructViewFromJson.class.getName());
	
	private static ObjectMapper mapper = new ObjectMapper();

	// path of the json file, without extension should be passed.  
	// function is package private
	static Map<String, UIElementString> getView(String path) {
		UIView uiView = null;
		String completeViewPath = "views/" + path + ".json";
		InputStream jsonStream = null;
		Map<String, UIElementString> uiElements = null; 
		try {
			logger.info("UIConstructViewFromJson -> getView(): Going to read the view: " + completeViewPath);
			jsonStream = UIConstructViewFromJson.class.getClassLoader().getResourceAsStream(completeViewPath);
			uiElements = mapper.readValue(jsonStream, mapper.getTypeFactory().constructMapType(Map.class, String.class, UIElementString.class));
			logger.info("UIConstructViewFromJson -> getView(): Read the JSON file");
			// checking for duplicate keys in the file. Checking it here, as the above statements will take care of the required format checking of the JSON.
			if(!duplicateKeyChecker(completeViewPath)) {
				logger.info("UIConstructViewFromJson -> getView(): Successfully constructed the View for the " + completeViewPath);
			}
			else {
				uiElements = null;
			}
		} 
		catch (Exception e) {
			logger.error("UIConstructViewFromJson -> getView(): Exception occured while reading the json from path: " + path);
			logger.error("UIConstructViewFromJson -> getView(): Please check the passed path: %s. If path is correct, please check the contents of the file".formatted(path)); 
			logger.error(e.getMessage());
			return null;
		} 
		catch (Error e) {
			logger.error("UIConstructViewFromJson -> getView(): Error occured while reading the json from path: " + path);
			logger.error(e.getMessage());
			return null;
		}
		finally {
			logger.info("UIConstructViewFromJson -> getView(): Finally block");
			completeViewPath = null;
			jsonStream = null;
//			uiElements = null;
		}

		return uiElements;
	}
	
	// Checking whether duplicate key entries are there in the json file. Json file structure is not validated in this function. 
	// returns false only in case of absence of duplicate keys, and keys with error.  
    private static boolean duplicateKeyChecker(String path) {
    	boolean duplicateKeyPresentInFile = true;
    	Set<String> duplicateKeys = null;
    	try {
    		ClassLoader classLoader = UIConstructViewFromJson.class.getClassLoader();
    		URL resource = classLoader.getResource(path);
    		File jsonFile = new File(resource.getFile());
    		
    		duplicateKeys = new HashSet<>();
    		
    		JsonFactory factory = new JsonFactory();
            JsonParser parser = factory.createParser(jsonFile);
            Set<String> keys = new HashSet<>();
            String currentField = null;
            logger.info("UIConstructViewFromJson -> duplicateKeyChecker(): Going to check for the duplicate keys in the file: " + path);
            
            String firstlevelKey = null;
            String secondLevelKey1 = null;
            String secondLevelKey2 = null;
            while (!parser.isClosed()) {
            	JsonToken token = parser.nextToken();
            	if (token == JsonToken.FIELD_NAME) {
            		currentField = parser.getCurrentName();
            		
            		// The keys structure in a ROW will be - firstlevelKey -> secondLevelKey1, secondLevelKey2
            		// If all are null, then the currentFiled will be firstLevelKey
            		if(firstlevelKey == null && secondLevelKey1 == null && secondLevelKey2 == null) {
            			firstlevelKey = currentField;
                		if (!keys.add(firstlevelKey)) {
                			duplicateKeys.add(firstlevelKey);
                			logger.error("UIConstructViewFromJson -> duplicateKeyChecker(): Duplicate key found: " + firstlevelKey); 
                		}
            		}
            		// Note - no need to check for the existence of 'strategy' and 'locator' 2nd level keys, as they will be taken care during Jackson de-serialization 
            		// if firsLevelKey != null and both secondLevelKeys are null, then current field is secondLevelKey1
            		else if(firstlevelKey != null && secondLevelKey1 == null && secondLevelKey2 == null) {
            			secondLevelKey1 = currentField;
            		}
            		// if it is not above 2 cases, then currentField will be secondLevelKey2
            		else {
            			secondLevelKey2 = currentField;
            			// End of the row, hence resetting for the next row of keys
            			firstlevelKey = null;
            			secondLevelKey1 = null;
            			secondLevelKey2 = null;
            		}
            		
            	}
//            	else if (token == JsonToken.START_OBJECT) {
//                    keys.clear(); // Clear keys for nested objects
//                }
            }
            // checking for duplicate keys or keys with error
            if(duplicateKeys.size() > 0) {
            	logger.error("UIConstructViewFromJson -> duplicateKeyChecker(): Invalid JSON file. PFB erros. Correct it to proceed forward.");
            	logger.error("UIConstructViewFromJson -> duplicateKeyChecker(): Duplicate keys found: " + duplicateKeys); 
            	
            }
            else {
            	duplicateKeyPresentInFile = false;
            	logger.info("UIConstructViewFromJson -> duplicateKeyChecker(): No duplicate keys in the file: " + path);
            }
		} 
    	catch (Exception e) {
    		logger.error("UIConstructViewFromJson -> duplicateKeyChecker(): Exception occured while reading the json from path: " + path);
			logger.error(e.getMessage());
		}
    	catch (Error e) {
    		logger.error("UIConstructViewFromJson -> duplicateKeyChecker(): Error occured while reading the json from path: " + path);
			logger.error(e.getMessage());
		}
    	return duplicateKeyPresentInFile;
    }
    
    private static boolean duplicateKeyChecker2(String path) {
    	boolean duplicateKeyPresentInFile = true;
    	try {
    		ClassLoader classLoader = UIConstructViewFromJson.class.getClassLoader();
    		URL resource = classLoader.getResource(path);
    		File jsonFile = new File(resource.getFile());
    		
    		ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(jsonFile);
            System.out.println("ROOT NODE: " + rootNode); 
         // Iterate through the field names at the root level
            Iterator<String> fieldNames = rootNode.fieldNames();
            while (fieldNames.hasNext()) {
                System.out.println(fieldNames.next());
            }
		} 
    	catch (Exception e) {
			// TODO: handle exception
		}
    	
    	return duplicateKeyPresentInFile;
    }
}
