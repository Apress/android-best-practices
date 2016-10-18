package com.riis.callcenter.utils;

public class TimeFormatter {

	public static String createTimeStringFromSeconds(int seconds) {
		int displayHours = (seconds / 3600);
		seconds -= displayHours * 3600;
		
		int displayMinutes = (seconds / 60);
		seconds -= displayMinutes * 60;
		
		int displaySeconds = seconds;
		
		StringBuilder timeStringBuilder = new StringBuilder();
		
		appendTwoDigitNumberToStringBuilder(timeStringBuilder, displayHours).append(":");
		appendTwoDigitNumberToStringBuilder(timeStringBuilder, displayMinutes).append(":");
		appendTwoDigitNumberToStringBuilder(timeStringBuilder, displaySeconds);
		
		return timeStringBuilder.toString();
	}
	
	private static StringBuilder appendTwoDigitNumberToStringBuilder(StringBuilder stringBuilder, int number) {
		if(number < 10) {
			stringBuilder.append("0");
		}
		stringBuilder.append(number);
		
		return stringBuilder;
	}
}
