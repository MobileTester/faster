package com.data.reader;

import java.util.ArrayList;

public sealed interface TestDataReader permits JsonTestDataReader{

	public ArrayList<?> getTestData(String aDataSource, String aDataBean);
}
