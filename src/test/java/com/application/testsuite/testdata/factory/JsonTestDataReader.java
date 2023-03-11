package com.application.testsuite.testdata.factory;

import java.io.InputStream;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;


public class JsonTestDataReader implements TestDataReader {
	
	private static Logger logger = LogManager.getLogger(JsonTestDataReader.class.getName());
	
	private static ObjectMapper mapper = new ObjectMapper();

	@Override
	public ArrayList<?> getTestData(String aDataSource, String aDataBean) {
		ArrayList<?> testData = null;
		try {
			InputStream jsonStream = JsonTestDataReader.class.getClassLoader().getResourceAsStream("testdata/" + aDataSource + ".json");
			testData = getFromJSONCollection(jsonStream, Class.forName(aDataBean));
		}
		catch (ClassNotFoundException e) {
			logger.error(e.getMessage());
		}
		catch (Exception e) {
			logger.error(e.getMessage());
		}
		return testData;
	}

	private static<T> ArrayList<T> getFromJSONCollection(InputStream jsonStream, final Class<T> type) {
		try {
			return mapper.readValue(jsonStream, TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, type));
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
}
