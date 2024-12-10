package com.data.dataprovider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;

import com.data.annotations.TestData;
import com.data.reader.TestDataReaderFactory;

public final class TestDataProvider {
	
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
				System.out.println(testData.toString()); 
				System.out.println("SIZE: " + testData.size()); 
				returnArray = new Object[testData.size()][1];
				for(int i = 0; i < testData.size(); i++) {
					returnArray[i][0] = testData.get(i);
				}
				
				logger.info("Data file successfully read");
			}
		}
		catch (Exception e) {
			logger.error("Error reading the test data file: " + e.getMessage());
//			System.exit(0);
		}
		return returnArray;
	}

}
