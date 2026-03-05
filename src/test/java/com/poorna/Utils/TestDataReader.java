package com.poorna.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestDataReader {
	private static TestDataReader instance;
	private Properties properties;

	private TestDataReader() {
		properties = new Properties();
		try (InputStream input = new FileInputStream("src/test/resources/TestData/config.properties")) {
			properties.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	public static TestDataReader getInstance() {
		if (instance == null) {
			instance = new TestDataReader();
		}
		return instance;
	}
	public String getProperty(String key) {
		return properties.getProperty(key);
	}
	
}
