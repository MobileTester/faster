package com.driver.rolelocator.annotation;

import java.lang.reflect.Proxy;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsElement;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

public class FindByRoleFieldDecorator extends DefaultFieldDecorator {

    public FindByRoleFieldDecorator(ElementLocatorFactory factory) {
        super(factory);
    }
    
//    @Override
//    protected WebElement proxyForLocator(ClassLoader loader, ElementLocator locator) {
//        if (WebElement.class.isAssignableFrom(loader.getClass())) {
//            return super.proxyForLocator(loader, locator);
//        } else if (List.class.isAssignableFrom(loader.getClass())) {
//            return super.proxyForLocator(loader, locator);
//        } else {
//            throw new RuntimeException("Unsupported type: " + loader); 
//        }
//    }
    

    @Override
    protected WebElement proxyForLocator(ClassLoader loader, ElementLocator locator) {
        return (WebElement) Proxy.newProxyInstance(loader, 
        				new Class[]{WebElement.class, WrapsElement.class}, 
        				(proxy, method, args) -> method.invoke(locator.findElement(), args));
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected List<WebElement> proxyForListLocator(ClassLoader loader, ElementLocator locator) {
        return (List<WebElement>) Proxy.newProxyInstance(
            loader,
            new Class[]{List.class},
            (proxy, method, args) -> method.invoke(locator.findElements(), args)
        );
    }

}

