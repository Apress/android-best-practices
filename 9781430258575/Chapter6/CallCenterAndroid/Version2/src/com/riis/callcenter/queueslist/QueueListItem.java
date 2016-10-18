package com.riis.callcenter.queueslist;

import com.riis.callcenter.genericlistadapter.GenericListItem;

public class QueueListItem extends GenericListItem {
	public String name;
	
	public int callsInQueue;
	public int oldestCallInSeconds;
	
	public QueueListItem(String name) {
		this.name = name;
		
		callsInQueue = 0;
		oldestCallInSeconds = 0;
	}
}
