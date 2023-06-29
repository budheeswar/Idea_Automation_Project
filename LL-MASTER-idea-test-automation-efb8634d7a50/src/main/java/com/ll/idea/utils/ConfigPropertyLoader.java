package com.ll.idea.utils;


import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.testng.Reporter;


public class ConfigPropertyLoader {

	static Properties properties = null;
	static String resourcePath = null;


	/**
	 * Prevent anyone to create an instance of this class. All constant variables
	 * should have accessed through static way.
	 */
	private ConfigPropertyLoader() {
		//Do nothing here
	}

	static {
		try {
			properties = new Properties();
			resourcePath = System.getProperty("user.dir") + File.separator + "src/main/resources/config.properties";
			FileInputStream ins = new FileInputStream(resourcePath);
			properties.load(ins);
		}catch (Exception e) {				
			Reporter.log("Error occured while loading config.properties.Please check the property file is available "+e.getMessage());
		}	
	}
	/**
	 * This method is used to return the value from config properties file
	 * 
	 * @param key Holds the String Value
	 * @return Returns the String value
	 */
	public static String getConfigValue(String key) {
		String value = null;
		value = properties.getProperty(key);
		return value;
	}
		
}
