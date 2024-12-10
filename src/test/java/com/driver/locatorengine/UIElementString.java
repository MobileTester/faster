package com.driver.locatorengine;

// This class is similar to a WebElement or MobileElement in String format
public final class UIElementString {

    private String strategy;
    private String locator;
    
    // default constructor required for de-serilization
    public UIElementString() {	}
    
    public UIElementString(UIElementString other) {
    	if(other != null) {
    		this.strategy = other.strategy;
    		this.locator = other.locator;
    	}
	}

    // Getters and setters
    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public String getLocator() {
        return locator;
    }

    public void setLocator(String locator) {
        this.locator = locator;
    }

    @Override
    public String toString() {
        return "Locator{" +
                "strategy='" + strategy + '\'' +
                ", locator='" + locator + '\'' +
                '}';
    }
}
