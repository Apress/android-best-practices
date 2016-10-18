package com.logicdrop.todos;

import java.util.List;
import java.util.ArrayList;

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
import android.os.StrictMode;

import com.logicdrop.todos.R;

public class TodoActivity extends Activity
{
	public static final String APP_TAG = "com.logicdrop.todos";

	private ListView taskView;
	private Button btNewTask;
	private EditText etNewTask;
	private TodoProvider provider;

	private final OnClickListener handleNewTaskEvent = new OnClickListener()
	{
		@Override
		public void onClick(final View view)
		{
			Log.d(APP_TAG, "add task click received");

			TodoActivity.this.provider.addTask(TodoActivity.this
					.etNewTask
							.getText()
							.toString());

			TodoActivity.this.renderTodos();
		}
	};

	@Override
	public void onCreate(final Bundle bundle)
	{

		super.onCreate(bundle);
		this.setContentView(R.layout.main);

		this.provider = new TodoProvider(this);
		this.taskView = (ListView) this.findViewById(R.id.tasklist);
		this.btNewTask = (Button) this.findViewById(R.id.btNewTask);
		this.etNewTask = (EditText) this.findViewById(R.id.etNewTask);
		this.btNewTask.setOnClickListener(this.handleNewTaskEvent);

		this.renderTodos();

	}

	private void renderTodos()
	{
		final List<String> beans = this.provider.findAll();

		Log.d(TodoActivity.APP_TAG, String.format("%d beans found", beans.size()));

		this.taskView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1,
				beans.toArray(new String[]
				{})));

		this.taskView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id)
			{
				Log.d(TodoActivity.APP_TAG, String.format("item with id: %d and position: %d", id, position));

				final TextView v = (TextView) view;
				TodoActivity.this.provider.deleteTask(v.getText().toString());
				TodoActivity.this.renderTodos();
			}
		});
	}

}