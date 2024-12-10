package com.data.listener;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.IConfigurationAnnotation;
import org.testng.annotations.IDataProviderAnnotation;
import org.testng.annotations.IFactoryAnnotation;
import org.testng.annotations.ITestAnnotation;

import com.data.annotations.TestData;
import com.data.dataprovider.TestDataProvider;

public final class ConfigurationAnnotationTransformerListener implements IAnnotationTransformer {

	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		
		if(annotation != null && annotation instanceof ITestAnnotation) {
			if(testMethod.getAnnotation(TestData.class) != null) {
				annotation.setDataProvider("getTestData");
				annotation.setDataProviderClass(TestDataProvider.class);
			}
		}
	}

	@Override
	public void transform(IConfigurationAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		// TODO Auto-generated method stub

	}

	@Override
	public void transform(IDataProviderAnnotation annotation, Method method) {
		// TODO Auto-generated method stub

	}

	@Override
	public void transform(IFactoryAnnotation annotation, Method method) {
		// TODO Auto-generated method stub

	}

}
