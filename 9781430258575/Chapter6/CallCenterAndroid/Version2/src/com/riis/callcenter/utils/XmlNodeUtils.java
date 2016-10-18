package com.riis.callcenter.utils;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlNodeUtils {
	
	public static String getChildTagValue(Node node, String tag) {
		Element element = (Element)node;
		NodeList childrenWithTag = element.getElementsByTagName(tag).item(0).getChildNodes();

		Node valueNode = (Node) childrenWithTag.item(0);

		return valueNode.getNodeValue();
	}
}
