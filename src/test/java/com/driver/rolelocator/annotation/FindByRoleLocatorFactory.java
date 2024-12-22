package com.driver.rolelocator.annotation;

import java.lang.reflect.Field;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.pagefactory.Annotations;
import org.openqa.selenium.support.pagefactory.DefaultElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

public class FindByRoleLocatorFactory implements ElementLocatorFactory {

	
	private final SearchContext searchContext;
    public FindByRoleLocatorFactory(SearchContext searchContext) {
        this.searchContext = searchContext;
    }
    
    @Override
    public ElementLocator createLocator(Field field) {
    	

        if (field.isAnnotationPresent(FindByRole.class)) {
            return new FindByRoleElementLocator(searchContext, field);
        }
        
        // Handle standard Selenium annotations using Annotations class
        Annotations annotations = new Annotations(field);
        By by = annotations.buildBy();
        if (by != null) {
            return new DefaultElementLocator(searchContext, field);
        }
        
        // Return null for other cases
        return null;
    }
}
