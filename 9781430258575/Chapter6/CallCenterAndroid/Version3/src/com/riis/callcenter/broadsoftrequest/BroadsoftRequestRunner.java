package com.riis.callcenter.broadsoftrequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Base64;

import com.riis.callcenter.broadsoftrequest.BroadsoftRequests.BroadsoftRequest;

public class BroadsoftRequestRunner {
	private static final String ERROR_CODE = "ERROR";
	
	private String username;
	private String broadsoftUrl;
	private String authorizationString;
	
	private BroadsoftResponseListener responseListener;
	private AsyncTask<String, String, String> requestTask;

	public BroadsoftRequestRunner(String username, String password, String broadsoftUrl) {
		this.username = username;
		this.broadsoftUrl = broadsoftUrl;

		authorizationString = generateAuthorizationString(username, password);
	}

	private String generateAuthorizationString(String username, String password) {
		String authorizationString = username + ":" + password;

		authorizationString = Base64.encodeToString(authorizationString.getBytes(), Base64.DEFAULT);
		authorizationString = "Basic " + authorizationString;

		return authorizationString;
	}

	public String runRequest(BroadsoftRequest request, BroadsoftResponseListener responseListener, String... parameters) {
		this.responseListener = responseListener;
		
		String completedRequestUrl = completeUrl(request.getRequestUrl(), parameters);
		requestTask = new BroadsoftRequestTask().execute(completedRequestUrl);
		
		return null;
	}
	
	public void cancelRequest() {
		requestTask.cancel(true);
	}
	
	private String completeUrl(String url, String... parameters) {
		String completedUrl = url;
		
		completedUrl = completedUrl.replace(BroadsoftRequests.URL_TAG, broadsoftUrl);
		completedUrl = completedUrl.replace(BroadsoftRequests.USERNAME_TAG, username);
		
		if(parameters.length > 0) {
			//Only supporting one parameter for now
			completedUrl = completedUrl.replace(BroadsoftRequests.PARAMETER_TAG, parameters[0]);
		}
		
		return completedUrl;
	}
	

	class BroadsoftRequestTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... uri) {
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response;

			String responseString = null;

			try {
				HttpGet httpRequest = new HttpGet(uri[0]);
				httpRequest.addHeader("Host", formatUrlForHostHeader(broadsoftUrl));
				httpRequest.addHeader("Authorization", authorizationString);
				httpRequest.addHeader("Cache-Control", "no-cache");

				response = httpclient.execute(httpRequest);
				StatusLine statusLine = response.getStatusLine();

				if(statusLine.getStatusCode() == HttpStatus.SC_OK) {
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					response.getEntity().writeTo(out);
					out.close();
					responseString = out.toString();
				} else {
					// Closes the connection.
					response.getEntity().getContent().close();
					throw new IOException(statusLine.getReasonPhrase());
				}
			} catch(Exception e) {
				responseString = ERROR_CODE + "~" + e.getMessage();
			}

			return responseString;
		}
		
		private String formatUrlForHostHeader(String unformattedUrl) {
			String formattedUrl = unformattedUrl;
			formattedUrl = formattedUrl.replace("http://", "");
			
			if(formattedUrl.endsWith("/")) {
				formattedUrl = formattedUrl.substring(0, formattedUrl.length() - 1);
			}
			
			return formattedUrl;
		}

		@Override
		protected void onPostExecute(String responseString) {
			super.onPostExecute(responseString);
			
			boolean success = !responseString.contains(ERROR_CODE);
			
			if(success) {
				responseListener.onRequestCompleted(responseString, success, "");
			} else {
				String[] responseParts = responseString.split("~");
				responseListener.onRequestCompleted(responseParts[0], success, responseParts[1]);
			}
		}
	}
}
