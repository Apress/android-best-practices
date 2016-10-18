package com.riis.callcenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.riis.callcenter.broadsoftrequest.BroadsoftRequestRunner;
import com.riis.callcenter.broadsoftrequest.BroadsoftRequests.BroadsoftRequest;
import com.riis.callcenter.broadsoftrequest.BroadsoftResponseListener;
import com.riis.callcenter.dialogs.AlertDialogs;

public class LoginActivity extends Activity {
	
	public static BroadsoftRequestRunner requestRunner;
	
	private Button loginButton;
	private SharedPreferences sharedPrefs;
	private ProgressDialog loadingDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    	setTheme(R.style.CustomTheme);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.login_screen);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_titlebar);
		((TextView)findViewById(R.id.title)).setText("Mobile Supervisor");
		UserDatabase udh = new UserDatabase(this);
		sharedPrefs = getSharedPreferences(SettingsActivity.SHARED_PREF_NAME, MODE_PRIVATE);
		
		loadLastSuccessfulCreds();
		initializeViews();
		bindListenersToViews();
	}

	private void loadLastSuccessfulCreds() {
		String lastUsername = sharedPrefs.getString(SettingsActivity.LAST_USERNAME_KEY, "");
		String lastUrl = sharedPrefs.getString(SettingsActivity.LAST_URL_KEY, "");

		((EditText) findViewById(R.id.username_field)).setText(lastUsername);
		((EditText) findViewById(R.id.url_field)).setText(lastUrl);
	}

	private void initializeViews() {
		loginButton = (Button) findViewById(R.id.login_button);
	}

	private void bindListenersToViews() {
		loginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				loadingDialog = AlertDialogs.createAndShowLoadingDialog(LoginActivity.this, new OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						requestRunner.cancelRequest();
					}
				});
				
				loginToBroadsoftAndLoadQueuesList();
			}
		});
	}

	private void loginToBroadsoftAndLoadQueuesList() {
		EditText usernameField = (EditText) findViewById(R.id.username_field);
		EditText passwordField = (EditText) findViewById(R.id.password_field);
		EditText urlField = (EditText) findViewById(R.id.url_field);
		
		if(areFieldsEmpty(usernameField, passwordField, urlField)) {
			AlertDialogs.showEmptyFieldsAlertDialog(this);
			return;
		}

		requestRunner = new BroadsoftRequestRunner(usernameField.getText().toString(),
												   passwordField.getText().toString(),
												   urlField.getText().toString());
		
		requestRunner.runRequest(BroadsoftRequest.QUEUES_REQUEST, responseListener);
	}

	private boolean areFieldsEmpty(EditText... fields) {
		for(int i = 0; i < fields.length; i++) {
			if(fields[i].getText().toString().matches("")) {
				return true;
			}
		}

		return false;
	}

	private BroadsoftResponseListener responseListener = new BroadsoftResponseListener() {
		@Override
		public void onRequestCompleted(String response, boolean success, String failureMessage) {
			loadingDialog.dismiss();
			
			if(!success) {
				AlertDialogs.showLoginFailedAlertDialog(LoginActivity.this, failureMessage);
			} else {
				saveLastSuccessfulCreds();
				Intent intent = new Intent(LoginActivity.this, QueuesActivity.class);
				intent.putExtra("response", response);
				
				startActivity(intent);
			}
		}
	};
	
	private void saveLastSuccessfulCreds() {
		String username = ((EditText) findViewById(R.id.username_field)).getText().toString();
		String url = ((EditText) findViewById(R.id.url_field)).getText().toString();

		SharedPreferences.Editor editor = sharedPrefs.edit();
		editor.putString(SettingsActivity.LAST_USERNAME_KEY, username);
		editor.putString(SettingsActivity.LAST_URL_KEY, url);
		editor.commit();
	}
}
