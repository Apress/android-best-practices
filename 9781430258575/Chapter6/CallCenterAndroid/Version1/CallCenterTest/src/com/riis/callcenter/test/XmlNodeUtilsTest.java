package com.riis.callcenter.test;

import junit.framework.TestCase;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.riis.callcenter.utils.XmlNodeUtils;
import com.riis.callcenter.xmlparsers.XmlStringReader;

public class XmlNodeUtilsTest extends TestCase {
	
	public void testgetChildTagValue() {
		String mockResponse = "<Test xmlns=\"http://schema.broadsoft.com/xsi\">\n" + 
				"	<TestList>\n" + 
				"		<element0>testElementName0</element0>\n" + 
				"		<element1>testElementName1</element1>\n" + 
				"       <element2>testElementName2</element2>\n" + 
				"   </TestList>\n" + 
				"	<TestList>\n" + 
				"		<element0>testElementName3</element0>\n" + 
				"       <element1>testElementName4</element1>\n" + 
				"       <element2>testElementName5</element2>\n" + 
				"   </TestList>\n" + 
				"</Test>";
		
		String list0element0 = "";
		String list0element1 = "";
		String list0element2 = "";
		String list1element0 = "";
		String list1element1 = "";
		String list1element2 = "";
		
		try {
			XmlStringReader xmlReader = new XmlStringReader(mockResponse);
			Document xmlDoc = xmlReader.readStringAsDocument();
			
			Node list0 = xmlDoc.getDocumentElement().getElementsByTagName("TestList").item(0);
			list0element0 = XmlNodeUtils.getChildTagValue(list0, "element0");
			list0element1 = XmlNodeUtils.getChildTagValue(list0, "element1");
			list0element2 = XmlNodeUtils.getChildTagValue(list0, "element2");
			
			Node list1 = xmlDoc.getDocumentElement().getElementsByTagName("TestList").item(1);
			list1element0 = XmlNodeUtils.getChildTagValue(list1, "element0");
			list1element1 = XmlNodeUtils.getChildTagValue(list1, "element1");
			list1element2 = XmlNodeUtils.getChildTagValue(list1, "element2");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		assertTrue(list0element0.equals("testElementName0"));
		assertTrue(list0element0.equals("testElementName1"));
		assertTrue(list0element0.equals("testElementName2"));
		assertTrue(list0element0.equals("testElementName3"));
		assertTrue(list0element0.equals("testElementName4"));
		assertTrue(list0element0.equals("testElementName5"));
	}
	
}
