package com.riis.callcenter;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.riis.callcenter.agentslist.AgentItemViewFactory;
import com.riis.callcenter.agentslist.AgentListItem;
import com.riis.callcenter.agentslist.AgentListItem.AgentStatus;
import com.riis.callcenter.dialogs.AlertDialogs;
import com.riis.callcenter.genericlistadapter.GenericListAdapter;
import com.riis.callcenter.genericlistadapter.GenericListItem;
import com.riis.callcenter.xmlparsers.AgentListXmlParser;
import com.riis.callcenter.xmlparsers.XmlStringReader;

public class AgentsActivity extends ListActivity {

	private Button settingsButton;
	
	private GenericListAdapter agentsAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    	setTheme(R.style.CustomTheme);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.queues_screen);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_titlebar_with_settings);
		((TextView)findViewById(R.id.title)).setText("CCQ Detail");

		findViewById(R.id.refreshButton).setVisibility(View.INVISIBLE);

		setUpTheSettingsButton();
		
		agentsAdapter = new GenericListAdapter(new AgentItemViewFactory());
		setListAdapter(agentsAdapter);
		
		populateListFromResponse(getIntent().getStringExtra("response"));
	}
	
	private void setUpTheSettingsButton() {
		settingsButton = (Button) findViewById(R.id.settings);
		settingsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AgentsActivity.this, SettingsActivity.class);
				startActivity(intent);
			}
		});
	}
	
	private void populateListFromResponse(String response) {
		try {
			XmlStringReader xmlReader = new XmlStringReader(response);
			loadAgentsAndPopulateList(xmlReader.readStringAsDocument(), getIntent().getStringExtra("callCenterName"));
		} catch(Exception e) {
			e.printStackTrace();
			AlertDialogs.showLoadingFailedAlertDialog(this, "Couldn't load agents.");
		}
	}
	
	private void loadAgentsAndPopulateList(Document xmlResponse, String callCenterName) throws UnsupportedEncodingException, TransformerException {
		AgentListXmlParser agentsParser = new AgentListXmlParser(xmlResponse, callCenterName);
		ArrayList<GenericListItem> agents = agentsParser.parseAgentsFromXmlDoc();
		
		for(int i = 0; i < agents.size(); i++) {
			agentsAdapter.addItem(agents.get(i), new View.OnClickListener() {
				@Override
				public void onClick(View v) {			
					AgentListItem agentClicked = (AgentListItem) agentsAdapter.getItemAssociateWithClickListener(this);
					
					if(agentClicked != null) {
						createChangeStateAlertDialog(agentClicked);
					}
				}
			});
		}
	}
	
	private void createChangeStateAlertDialog(final AgentListItem agentClicked) {
		AlertDialog.Builder builder = new AlertDialog.Builder(AgentsActivity.this);
		
		builder.setTitle("Change status of " + agentClicked.name);
		builder.setItems(new CharSequence[] {"Ready", "Reserved", "Not Ready"}, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				agentClicked.status = AgentStatus.values()[which];
				
				agentsAdapter.forceRefresh();
				dialog.cancel();
			}
		});
		
		
		builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		
		builder.create().show();
	}
}
