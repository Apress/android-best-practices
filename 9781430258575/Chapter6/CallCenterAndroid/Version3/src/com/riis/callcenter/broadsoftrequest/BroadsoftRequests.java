package com.riis.callcenter.broadsoftrequest;

public class BroadsoftRequests {
	public static final String URL_TAG = "%URL%";
	public static final String USERNAME_TAG = "%USERNAME%";
	public static final String PARAMETER_TAG = "%PARAMETER%";
	
	public enum BroadsoftRequest {
		QUEUES_REQUEST(URL_TAG + "com.broadsoft.xsi-actions/v2.0/user/" + USERNAME_TAG + "/services/callcenter"),
		AGENTS_REQUEST(URL_TAG + "com.broadsoft.xsi-actions/v2.0/user/" + USERNAME_TAG + "/directories/Agents?callCenter=" + PARAMETER_TAG),
		CALLS_REQUEST(URL_TAG + "com.broadsoft.xsi-actions/v1.0/user/" + PARAMETER_TAG + "/queue");
		
		private String requestUrl;

		private BroadsoftRequest(String requestUrl) {
			this.requestUrl = requestUrl;
		}

		public String getRequestUrl() {
			return requestUrl;
		}
	}
}
