package com.riis.callcenter.broadsoftrequest;

public interface BroadsoftResponseListener {
	abstract public void onRequestCompleted(String response, boolean success, String failureMessage);
}
