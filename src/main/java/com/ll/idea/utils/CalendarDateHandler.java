package com.ll.idea.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarDateHandler {
	
	private CalendarDateHandler() {
		//prevent instantiation from outside the class
	}

	/**
	 * This method formats the current date into given date format. For example, if user wants
	 * to convert the current date into MMddYYYYHHmm, the format would be passed as a parameter
	 * and this method will get it converted into string value in expected format
	 * <code>022620221036</code> which is equivalent to 26th Feb 2022 at 10 AM 36 Minutes
	 * @return String current into string format. 
	 */
	public static String getFormatedDate(String format) {
		String sDateFormat;
		SimpleDateFormat sformat = new SimpleDateFormat(format);
		Calendar currentDate = Calendar.getInstance();
		sDateFormat = sformat.format(currentDate.getTime());
		return sDateFormat;
	}
}
