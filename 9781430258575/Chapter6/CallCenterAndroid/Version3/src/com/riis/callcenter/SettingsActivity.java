package com.riis.callcenter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;
import android.widget.TextView;

public class SettingsActivity extends Activity {
	public static final String LAST_USERNAME_KEY = "lastUsername";
	public static final String LAST_URL_KEY = "lastURL";
	public static final String SHARED_PREF_NAME = "mySharedPrefs";
	
	private TextView usernameView;
	private TextView urlView;
	
	private SharedPreferences sharedPrefs;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    	setTheme(R.style.CustomTheme);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.settings_screen);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_titlebar);
		((TextView)findViewById(R.id.title)).setText("Supervisor");

		sharedPrefs = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

		usernameView = (TextView) findViewById(R.id.usernameField);
		urlView = (TextView) findViewById(R.id.urlField);
		
		usernameView.setText(sharedPrefs.getString(LAST_USERNAME_KEY, ""));
		urlView.setText(sharedPrefs.getString(LAST_URL_KEY, ""));
		
		setOnChangeListeners();
		
	}

	private void setOnChangeListeners() {
		usernameView.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				String username = usernameView.getText().toString();
				SharedPreferences.Editor editor = sharedPrefs.edit();
				editor.putString(LAST_USERNAME_KEY, username);
				editor.commit();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
		});
		urlView.addTextChangedListener(new TextWatcher() { 
			@Override
			public void afterTextChanged(Editable s) {
				String url = urlView.getText().toString();
				SharedPreferences.Editor editor = sharedPrefs.edit();
				editor.putString(LAST_URL_KEY, url);
				editor.commit();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
		});
	}
}
