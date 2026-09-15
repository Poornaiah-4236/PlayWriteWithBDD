package com.poorna.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
	private static ConfigReader instance;
	private Properties properties;

	private ConfigReader() {
		properties = new Properties();
		try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("TestData/config.properties")) {
			if (input == null) {
				throw new RuntimeException("config.properties not found on classpath at TestData/config.properties");
			}
			properties.load(input);
		} catch (IOException e) {
			throw new RuntimeException("Failed to load config.properties", e);
		}
	}

	public static ConfigReader getInstance() {
		if (instance == null) {
			instance = new ConfigReader();
		}
		return instance;
	}

	public String getProperty(String key) {
		String override = System.getProperty(key);
		if (override != null) {
			return override;
		}
		return properties.getProperty(key);
	}
}
