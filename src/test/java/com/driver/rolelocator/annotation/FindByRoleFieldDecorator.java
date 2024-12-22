package com.driver.rolelocator.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

// This class is not required. Keeping it for reference. 
// DefaultFieldDecorator is used instead
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
    public Object decorate(ClassLoader loader, Field field) {
      if (!(WebElement.class.isAssignableFrom(field.getType()) || isDecoratableList(field))) {
        return null;
      }

      ElementLocator locator = factory.createLocator(field);
      if (locator == null) {
        return null;
      }

      if (WebElement.class.isAssignableFrom(field.getType())) {
        return proxyForLocator(loader, locator);
      } else if (List.class.isAssignableFrom(field.getType())) {
        return proxyForListLocator(loader, locator);
      } else {
        return null;
      }
    }
    
    @Override
    protected boolean isDecoratableList(Field field) {
        if (!List.class.isAssignableFrom(field.getType())) {
            return false;
          }

          // Type erasure in Java isn't complete. Attempt to discover the generic
          // type of the list.
          Type genericType = field.getGenericType();
          if (!(genericType instanceof ParameterizedType)) {
            return false;
          }

          Type listType = ((ParameterizedType) genericType).getActualTypeArguments()[0];

          if (!WebElement.class.equals(listType)) {
            return false;
          }

          return field.getAnnotation(FindBy.class) != null
              || field.getAnnotation(FindBys.class) != null
              || field.getAnnotation(FindAll.class) != null
              // This is required for decorating fields of type -  List<FindByRole>
              || field.getAnnotation(FindByRole.class) != null;
    }

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

