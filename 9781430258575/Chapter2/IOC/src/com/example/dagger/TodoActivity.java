package com.example.dagger;

import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dagger.R;

import javax.inject.Inject;

public class TodoActivity extends Activity {	
	public static final String APP_TAG = "com.example.dagger";

	private ListView taskView;
	private Button btNewTask;
	private EditText etNewTask;
	private TodoApplication app;
	
	@Inject
	IDataProvider provider;
	
	private final OnClickListener handleNewTaskEvent = new OnClickListener() {
		@Override
		public void onClick(final View view) {
			Log.d(APP_TAG, "add task click received");
			
			TodoActivity.this.provider.addTask(TodoActivity.this.etNewTask
					.getText().toString());

			TodoActivity.this.renderTodos();
		}
	};

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	public void onCreate(final Bundle bundle) {
		super.onCreate(bundle);

		// So we can inject in the activity
		this.app = (TodoApplication) getApplication();
		app.getObjectGraph().inject(this);
		
		//Sets up the settings
		Editor editor = this.app.settings.edit();
		editor.putBoolean("CurrentChoice", TodoModule.sourceToggle);
		editor.commit();
		
		this.setContentView(R.layout.main);

		this.taskView = (ListView) this.findViewById(R.id.tasklist);
		this.btNewTask = (Button) this.findViewById(R.id.btNewTask);
		this.etNewTask = (EditText) this.findViewById(R.id.etNewTask);
		this.btNewTask.setOnClickListener(this.handleNewTaskEvent);
		
		this.renderTodos();
	}
	
	//The menu option appears when the menu phone button is pressed
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.todo, menu);
		return true;
	}
	
	//This handles the menu option select
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
			case R.id.debug:
				switchSource();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	//This method toggles between the providers
	private void switchSource(){
		TodoModule.sourceToggle = !TodoModule.sourceToggle;
		
		//Here it calls a method to update the setting
		this.app.updateSetting(TodoModule.sourceToggle);
	}
	
	

	private void renderTodos() {
		final List<String> beans = this.provider.findAll();

		Log.d(TodoActivity.APP_TAG,
				String.format("%d beans found", beans.size()));

		this.taskView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, beans
						.toArray(new String[] {})));

		this.taskView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(final AdapterView<?> parent,
					final View view, final int position, final long id) {
				Log.d(TodoActivity.APP_TAG, String.format(
						"item with id: %d and position: %d", id, position));

				final TextView v = (TextView) view;
				TodoActivity.this.provider.deleteTask(v.getText().toString());
				TodoActivity.this.renderTodos();
			}
		});
	}
}