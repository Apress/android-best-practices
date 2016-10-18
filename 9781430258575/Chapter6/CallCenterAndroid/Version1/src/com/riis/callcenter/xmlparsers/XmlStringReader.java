package com.riis.callcenter.xmlparsers;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XmlStringReader {
	private String xmlString;
	
	public XmlStringReader(String xmlString) {
		this.xmlString = xmlString;
	}

	public Document readStringAsDocument() throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document xmlDocument = dBuilder.parse(new ByteArrayInputStream(xmlString.getBytes()));
		
		xmlDocument.getDocumentElement().normalize();
		
		return xmlDocument;
	}
}
