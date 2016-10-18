package com.riis.callcenter.test;

import com.riis.callcenter.utils.TimeFormatter;

import junit.framework.TestCase;

public class TimeFormatterTest extends TestCase {

	public void testCreateTimeStringFromSeconds() {
		int seconds = (3600 * 1) + (60 * 50) + (42 * 1);
		
		String formattedTime = TimeFormatter.createTimeStringFromSeconds(seconds);
		
		assertEquals("01:50:42", formattedTime);
	}

}
