package com.driver.rolelocator.annotation;

import java.lang.reflect.Field;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

public class FindByRoleElementLocator implements  ElementLocator {

    private final SearchContext searchContext;
    private final By by;
    
    public FindByRoleElementLocator(SearchContext searchContext, Field field) {
        this.searchContext = searchContext;
        this.by = buildByFromField(field);
    }
    
    // Custom logic to build the By locator from the field annotations
    private By buildByFromField(Field field) {
        // Check if @FindByRole annotation is present
        if (field.isAnnotationPresent(FindByRole.class)) {
        	try {
        		FindByRole findByRole = field.getAnnotation(FindByRole.class);
                String tag = findByRole.tag();
                String attribute = findByRole.attribute();
                String value = findByRole.value();
                int index = findByRole.index();
                
        		// Adding @ for attributes if required
        		// only text() does not require @ prepended
        		if(!attribute.equals("text()") &&  attribute.charAt(0) != '@') {
        			attribute = '@' + attribute;
        		}

                // Generate the XPath to target the element using exact match and index
                
                // String xpath = String.format("//%s[contains(@%s, '%s')][%d]", tag, attribute, value, index);
                String xpathString = null;
                if(index == -1) {
                	xpathString = getXpathString(tag, attribute, value);
                }
                else {
                	xpathString = getXpathString(tag, attribute, value, index);
                }
                // String xpath = String.format("(//%s[@%s='%s'])[%d]", tag, attribute, value, index);
                // System.out.println("CustomElementLocator: Built XPath - " + xpath);
                return By.xpath(xpathString);
        	}
        	catch(Exception e) {
        		e.printStackTrace();
        	}
        }

        throw new IllegalArgumentException("Field must be annotated with @FindByRole: " + field.getName());
    }
    
    @Override
    public WebElement findElement() {
        return searchContext.findElement(by);
    }
    
    @Override
    public List<WebElement> findElements() {
        return searchContext.findElements(by);
    }
    
	// if attribute is text(), just pass text() as the attribute without @ prepended
    // for all other attributes @ is required at beginning which will be prepended if not present.  
	// if value is expected to be an exact match, send value as it is
	//		value can also be sent as - contains('value') OR starts-with('value')
    private String getXpathString(String tag, String attribute, String value) {
    	String xpathString = null;
    	
        String regex1 = "contains\\(['\"](.*)['\"]\\)";
        String regex2 = "starts-with\\(['\"](.*)['\"]\\)";
        // selenium won't support the below 2 regex. Keeping it for reference. 
//      String regex3 = "ends-with\\(['\"](.*)['\"]\\)";
//      String regex4 = "matches\\(['\"](.*)['\"]\\)";
        
		Pattern pattern = null;
		Matcher matcher = null;
		
		try {
			
			if(value.matches(regex1)) {
				pattern = Pattern.compile(regex1);
				matcher = pattern.matcher(value);
				// extracting the required part only
				if(matcher.find()) {
					String extractedValue = matcher.group(1);
					xpathString = "//%s[contains(%s, '%s')]".formatted(tag, attribute, extractedValue);
				}

			}
			else if(value.matches(regex2)) {
				pattern = Pattern.compile(regex2);
				matcher = pattern.matcher(value);
				// extracting the required part only
				if(matcher.find()) {
					String extractedValue = matcher.group(1);
					xpathString = "//%s[starts-with(%s, '%s')]".formatted(tag, attribute, extractedValue);
				}
			}
			// It seems Selenium won't support end-with. Keeping code for reference
//			else if(value.matches(regex3)) {
//				pattern = Pattern.compile(regex3);
//				matcher = pattern.matcher(value);
//				// extracting the required part only
//				if(matcher.find()) {
//					String extractedValue = matcher.group(1);
//					xpathString = "//%s[ends-with(%s, '%s')][%d]".formatted(tag, attribute, extractedValue, index);
//				}
//
//			}
			// It seems Selenium won't support matches. Keeping code for reference
//			else if(value.matches(regex4)) {
//				pattern = Pattern.compile(regex4);
//				matcher = pattern.matcher(value);
//				// extracting the required part only
//				if(matcher.find()) {
//					String extractedValue = matcher.group(1);
//					xpathString = "//%s[matches(%s, '%s')][%d]".formatted(tag, attribute, extractedValue, index);
//				}
//			}
			else {
				xpathString = "//%s[%s='%s']".formatted(tag, attribute, value);
			}
			
		} 
    	catch (Exception e) {
			e.printStackTrace();
		}
		
        return xpathString;
    	
    }
    
    // index will also be added along with the xpath at end. 
    private String getXpathString(String tag, String attribute, String value, int index) {
    	String xpathString = null;
    	try {
			
    		xpathString = getXpathString(tag, attribute, value);
    		// appending passed index at the end of the xpath. 
    		xpathString = xpathString + "[%d]".formatted(index); 
    		
		} 
    	catch (Exception e) {
    		e.printStackTrace();
		}
    	return xpathString;
    }
    

}
