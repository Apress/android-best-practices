package com.riis.callcenter;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.riis.callcenter.broadsoftrequest.BroadsoftRequests.BroadsoftRequest;
import com.riis.callcenter.broadsoftrequest.BroadsoftResponseListener;
import com.riis.callcenter.dialogs.AlertDialogs;
import com.riis.callcenter.genericlistadapter.GenericListAdapter;
import com.riis.callcenter.genericlistadapter.GenericListItem;
import com.riis.callcenter.queueslist.QueueItemViewFactory;
import com.riis.callcenter.queueslist.QueueListItem;
import com.riis.callcenter.xmlparsers.CallsXmlReader;
import com.riis.callcenter.xmlparsers.QueuesListXmlParser;

public class QueuesActivity extends ListActivity {
	private GenericListAdapter queuesAdapter;

	private Button refreshButton;
	private Button settingsButton;
	private ProgressDialog loadingDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    	setTheme(R.style.CustomTheme);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.queues_screen);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_titlebar_with_settings);
		((TextView)findViewById(R.id.title)).setText("CCQ'S");

		setUpTheListAdapter();
		setUpTheRefreshButton();
		setUpTheSettingsButton();

		populateListFromResponse(getIntent().getStringExtra("response"));
	}
	
	private void setUpTheListAdapter() {
		queuesAdapter = new GenericListAdapter(new QueueItemViewFactory());
		setListAdapter(queuesAdapter);
	}

	private void setUpTheRefreshButton() {
		refreshButton = (Button) findViewById(R.id.refreshButton);
		refreshButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadingDialog = AlertDialogs.createAndShowLoadingDialog(QueuesActivity.this, new OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						LoginActivity.requestRunner.cancelRequest();
					}
				});

				LoginActivity.requestRunner.runRequest(BroadsoftRequest.QUEUES_REQUEST, responseListener);
			}
		});
	}
	
	private void setUpTheSettingsButton() {
		settingsButton = (Button) findViewById(R.id.settings);
		settingsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(QueuesActivity.this, SettingsActivity.class);
				startActivity(intent);
			}
		});
	}

	private BroadsoftResponseListener responseListener = new BroadsoftResponseListener() {
		@Override
		public void onRequestCompleted(String response, boolean success, String failureMessage) {
			loadingDialog.dismiss();

			if(!success) {
				AlertDialogs.showLoadingFailedAlertDialog(QueuesActivity.this, failureMessage);
			} else {
				populateListFromResponse(response);
			}
		}
	};

	private void populateListFromResponse(String response) {
		try {
			loadQueuesAndPopulateList(response);
		} catch(Exception e) {
			AlertDialogs.showLoadingFailedAlertDialog(this, "Couldn't load queues.");
		}
	}

	private void loadQueuesAndPopulateList(String response) throws TransformerException, ParserConfigurationException, SAXException, IOException {
		QueuesListXmlParser queuesParser = new QueuesListXmlParser(response);
		ArrayList<GenericListItem> queues = queuesParser.parseQueuesFromXmlDoc();
		
		loadCallsInQueueForList(queues);
		
		queuesAdapter.setDataSetWithClickListenerForAll(queues, queueClickListener);
	}
	
	private void loadCallsInQueueForList(ArrayList<GenericListItem> queues) {
		for(GenericListItem queue : queues) {
			loadCallsInQueueForQueue((QueueListItem)queue);
		}
	}
	
	private void loadCallsInQueueForQueue(final QueueListItem queue) {
		LoginActivity.requestRunner.runRequest(BroadsoftRequest.CALLS_REQUEST, new BroadsoftResponseListener() {
			@Override
			public void onRequestCompleted(String response, boolean success, String failureMessage) {
				CallsXmlReader callsReader = new CallsXmlReader(response);
				
				try {
					queue.callsInQueue = callsReader.parseNumberOfCalls();
				} catch(Exception e) {
					queue.callsInQueue = -1;
				}
				
				queuesAdapter.forceRefresh();
			}
		}, queue.name);
	}

	private View.OnClickListener queueClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			TextView queueNameView = (TextView) v.findViewById(R.id.queueTitle);

			loadAndShowAgentListForQueue(queueNameView.getText().toString());
		}
	};

	private void loadAndShowAgentListForQueue(final String queueName) {
		loadingDialog = AlertDialogs.createAndShowLoadingDialog(QueuesActivity.this, new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				LoginActivity.requestRunner.cancelRequest();
			}
		});
		
		LoginActivity.requestRunner.runRequest(BroadsoftRequest.AGENTS_REQUEST, new BroadsoftResponseListener() {
			@Override
			public void onRequestCompleted(String response, boolean success, String failureMessage) {
				loadingDialog.dismiss();

				if(!success) {
					AlertDialogs.showLoadingFailedAlertDialog(QueuesActivity.this, failureMessage);
				} else {
					Intent intent = new Intent(QueuesActivity.this, AgentsActivity.class);
					intent.putExtra("response", response);
					intent.putExtra("callCenterName", queueName);
					startActivity(intent);
				}
			}
		}, queueName);
	}
}
