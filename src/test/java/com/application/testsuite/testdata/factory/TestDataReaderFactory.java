package com.application.testsuite.testdata.factory;

public class TestDataReaderFactory {

	public static final String JSON = "JSON";
	
	public static TestDataReader getTestDataReader(String aDataSourceType) {
		TestDataReader testDataReader = null;
		if(aDataSourceType.equals(TestDataReaderFactory.JSON)) {
			testDataReader = new JsonTestDataReader();
		}
		// implement other types here
		return testDataReader;
	}
}
