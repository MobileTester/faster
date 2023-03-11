package com.application.utils.dataprovider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;

import com.application.testsuite.testdata.factory.TestDataReaderFactory;
import com.application.utils.annotations.TestData;

public class TestDataProvider {
	
	public static Logger logger = LogManager.getLogger(TestDataProvider.class.getName()); 
	
	
	@DataProvider(name = "getTestData")
	public static Object[][] getTestData(Method aMethod) {
		Object[][] returnArray = null;
		try {
			Annotation testMethodAnnotations[] = aMethod.getAnnotations();
			String source = "";
			String dataBean = "";
			String type = "";
			
			for(Annotation annotation: testMethodAnnotations) {
				if(annotation instanceof TestData) {
					TestData testDataAnnotation = (TestData) annotation;
					source = testDataAnnotation.source();
					dataBean = testDataAnnotation.dataBean();
					type = testDataAnnotation.type();
				}
			}
			
			if("".equals(source) || "".equals(dataBean)) {
				logger.warn("Data Source / Bean not defined");
			}
			else {
				ArrayList<?> testData = TestDataReaderFactory.getTestDataReader(type).getTestData(source, dataBean);
				returnArray = new Object[testData.size()][1];
				for(int i = 0; i < testData.size(); i++) {
					returnArray[i][0] = testData.get(i);
				}
				
				logger.info("Data file successfully read");
			}
		}
		catch (Exception e) {
			logger.error("Error reading the test data file: " + e.getMessage());
		}
		return returnArray;
	}

}
