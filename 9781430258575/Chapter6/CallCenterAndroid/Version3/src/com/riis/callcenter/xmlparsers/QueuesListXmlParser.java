package com.riis.callcenter.xmlparsers;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.riis.callcenter.genericlistadapter.GenericListItem;
import com.riis.callcenter.queueslist.QueueListItem;
import com.riis.callcenter.utils.XmlNodeUtils;

public class QueuesListXmlParser {
	private String xmlString;
	private ArrayList<GenericListItem> queues;
	
	public QueuesListXmlParser(String xmlString) {
		this.xmlString = xmlString;
		this.queues = new ArrayList<GenericListItem>();
	}
	
	public ArrayList<GenericListItem> parseQueuesFromXmlDoc() throws ParserConfigurationException, SAXException, IOException {
		XmlStringReader xmlStringReader = new XmlStringReader(xmlString);
		Document xmlDoc = xmlStringReader.readStringAsDocument();
		
		Node callCenterListNode = xmlDoc.getDocumentElement().getElementsByTagName("callCenterList").item(0);

		for(int i = 0; i < callCenterListNode.getChildNodes().getLength(); i++) {
			Node callCenterNode = callCenterListNode.getChildNodes().item(i);
			String callCenterName = XmlNodeUtils.getChildTagValue(callCenterNode, "serviceUserId");

			QueueListItem newQueue = new QueueListItem(callCenterName);
			queues.add(newQueue);
		}
		
		return queues;
	}
}
