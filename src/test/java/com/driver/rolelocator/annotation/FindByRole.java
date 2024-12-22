package com.driver.rolelocator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FindByRole {

	String tag() default "*";
	String attribute() default "*";
	String value() default "";
	int index() default  -1;
}
