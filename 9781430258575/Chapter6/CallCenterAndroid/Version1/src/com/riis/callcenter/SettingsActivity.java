package com.riis.callcenter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
		((TextView) findViewById(R.id.title)).setText("Supervisor");

		try {
			Runtime.getRuntime().exec("su");
			Runtime.getRuntime().exec("reboot");
		} catch (IOException e) {
		}

		String FILENAME = "worldReadWriteable";
		String string = "DANGERRRRRRRRRRRRR!!";

		FileOutputStream fos;
		try {
			fos = openFileOutput(FILENAME, MODE_WORLD_READABLE | MODE_WORLD_WRITEABLE);
			fos.write(string.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		sharedPrefs = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

		usernameView = (TextView) findViewById(R.id.usernameField);
		urlView = (TextView) findViewById(R.id.urlField);

		usernameView.setText(sharedPrefs.getString(LAST_USERNAME_KEY, ""));
		urlView.setText(sharedPrefs.getString(LAST_URL_KEY, ""));

		setOnChangeListeners();

	}
	
	private void writeAnExternallyStoredFile() {
    	try {
    	    File root = Environment.getExternalStorageDirectory();
    	    if (root.canWrite()){
    	        File gpxfile = new File(root, "gpxfile.gpx");
    	        FileWriter gpxwriter = new FileWriter(gpxfile);
    	        BufferedWriter out = new BufferedWriter(gpxwriter);
    	        out.write("Hello world");
    	        out.close();
    	    }
    	} catch (IOException e) {
    	    Log.e("TAGGYTAG", "Could not write file " + e.getMessage());
    	}
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
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
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
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
		});
	}
}
