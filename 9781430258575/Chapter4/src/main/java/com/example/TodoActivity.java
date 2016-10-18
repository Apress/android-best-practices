package com.example;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class TodoActivity extends Activity
{
	public static final String APP_ID = "TODO";
	private ListView taskView;
	private Button newTaskButton;
	private EditText newTaskTextbox;
	private TodoProvider dataProvider;

	private final OnClickListener handleNewTaskEvent = new OnClickListener()
	{
		@Override
		public void onClick(final View view)
		{
			TodoActivity.this.dataProvider.addTask(TodoActivity.this.newTaskTextbox.getText().toString());
			TodoActivity.this.render();
		}
	};

	EditText getEditableTextbox()
	{
		return this.newTaskTextbox;
	}

	TodoProvider getProvider()
	{
		return TodoActivity.this.dataProvider;
	}

	Button getSaveTaskButton()
	{
		return this.newTaskButton;
	}

	ListView getTaskListView()
	{
		return this.taskView;
	}

	@Override
	public void onCreate(final Bundle bundle)
	{
		super.onCreate(bundle);
		this.setContentView(R.layout.main);
		this.dataProvider = new TodoProvider(this);
		this.taskView = (ListView) this.findViewById(R.id.tasklist);
		this.newTaskButton = (Button) this.findViewById(R.id.btNewTask);
		this.newTaskTextbox = (EditText) this.findViewById(R.id.etNewTask);
		this.newTaskButton.setOnClickListener(this.handleNewTaskEvent);
		this.render();
	}

	private void render()
	{
		final List<String> results = this.dataProvider.findAll();

		if (!results.isEmpty())
		{
			Log.d(TodoActivity.APP_ID, String.format("%d tasks found", results.size()));

			this.taskView.setAdapter(new ArrayAdapter<String>(
					this,
					android.R.layout.simple_list_item_1,
					results.toArray(new String[] {})));

			this.taskView.setOnItemClickListener(
					new OnItemClickListener()
					{
						@Override
						public void onItemClick(
								final AdapterView<?> parent,
								final View view,
								final int position,
								final long id)
						{
							final TextView v = (TextView) view;
							TodoActivity.this.dataProvider.deleteTask(
									v.getText()
											.toString());
							TodoActivity.this.render();

						}
					});
		}
		else
		{
			Log.d(TodoActivity.APP_ID, "No tasks found");
		}
	}
}
