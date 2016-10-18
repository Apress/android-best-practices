package com.example.dagger;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import dagger.ObjectGraph;

public class TodoApplication extends Application {

	private ObjectGraph objectGraph;
	SharedPreferences settings;

	@Override
	public void onCreate() 
	{
		super.onCreate();
		
		//Initializes the settings variable
		this.settings = getSharedPreferences("Settings", MODE_PRIVATE);
		Object[] modules = new Object[] {
				new TodoModule(this)
		};
		
		objectGraph = ObjectGraph.create(modules);
	}
 
	public ObjectGraph getObjectGraph() {
		return this.objectGraph;
	}
	
	//Method to update the settings
	public void updateSetting(boolean newChoice){
		Editor editor = this.settings.edit();
		editor.putBoolean("CurrentChoice", TodoModule.sourceToggle);
		editor.commit();
	}
	
	//Method to obtain the value of the provider setting
	public boolean getCurrentSource(){
		return this.settings.getBoolean("CurrentChoice", false);
	}
}
