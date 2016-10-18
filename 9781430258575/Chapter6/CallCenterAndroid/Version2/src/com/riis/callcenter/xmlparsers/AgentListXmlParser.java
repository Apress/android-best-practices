package com.riis.callcenter.xmlparsers;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.riis.callcenter.agentslist.AgentListItem;
import com.riis.callcenter.genericlistadapter.GenericListItem;
import com.riis.callcenter.utils.XmlNodeUtils;

public class AgentListXmlParser {
	private Document xmlDoc;
	private ArrayList<GenericListItem> agents;
	private String callCenterName;

	public AgentListXmlParser(Document xmlDoc, String callCenterName) {
		this.xmlDoc = xmlDoc;
		this.agents = new ArrayList<GenericListItem>();
		this.callCenterName = callCenterName;
	}

	public ArrayList<GenericListItem> parseAgentsFromXmlDoc() {
		NodeList callCenterList = xmlDoc.getDocumentElement().getElementsByTagName("callCenter");

		for(int i = 0; i < callCenterList.getLength(); i++) {
			String centerName = XmlNodeUtils.getChildTagValue(callCenterList.item(i), "serviceUserID");

			if(centerName.equals(callCenterName)) {
				NodeList agentsList = ((Element) callCenterList.item(i)).getElementsByTagName("userDetails");

				if(agentsList != null) {
					for(int j = 0; j < agentsList.getLength(); j++) {
						String agentName = XmlNodeUtils.getChildTagValue(agentsList.item(j), "firstName");
						agentName += " " + XmlNodeUtils.getChildTagValue(agentsList.item(j), "lastName");
						AgentListItem newAgent = new AgentListItem(agentName);
						agents.add(newAgent);
					}
				}
			}
		}

		return agents;
	}
}
