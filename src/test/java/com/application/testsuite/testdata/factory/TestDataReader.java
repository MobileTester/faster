package com.application.testsuite.testdata.factory;

import java.util.ArrayList;

public interface TestDataReader {

	public ArrayList<?> getTestData(String aDataSource, String aDataBean);
}
