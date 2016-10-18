package com.riis.callcenter.xmlparsers;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class CallsXmlReader {
	private String xmlString;

	public CallsXmlReader(String xmlString) {
		this.xmlString = xmlString;
	}

	public int parseNumberOfCalls() throws ParserConfigurationException, SAXException, IOException {
		XmlStringReader xmlStringReader = new XmlStringReader(xmlString);
		Document xmlDoc = xmlStringReader.readStringAsDocument();

		Element callsListElement = (Element) xmlDoc.getDocumentElement().getElementsByTagName("queueEntries").item(0);

		if(callsListElement != null) {
			return callsListElement.getElementsByTagName("queueEntry").getLength();
		} else {
			return 0;
		}
	}
}
