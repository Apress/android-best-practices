package com.riis.callcenter.agentslist;

import com.riis.callcenter.genericlistadapter.GenericListItem;

public class AgentListItem extends GenericListItem {
	public enum AgentStatus {
		READY(android.R.drawable.presence_online, " Ready", 0xFF00C100),
		RESERVED(android.R.drawable.presence_away, " Reserved", 0xFFFFFF00),
		NOT_READY(android.R.drawable.presence_busy, " Not Ready", 0xFFC10000);

		private int drawableId;
		private String statusText;
		private int textColor;

		private AgentStatus(int drawableId, String statusText, int textColor) {
			this.drawableId = drawableId;
			this.statusText = statusText;
			this.textColor = textColor;
		}

		public int getDrawableId() {
			return drawableId;
		}
		
		public String getStatusText() {
			return statusText;
		}
		
		public int getTextColor() {
			return textColor;
		}
	}

	public String name;
	public AgentStatus status;

	public AgentListItem(String name) {
		this.name = name;
		this.status = AgentStatus.READY;
	}
}
