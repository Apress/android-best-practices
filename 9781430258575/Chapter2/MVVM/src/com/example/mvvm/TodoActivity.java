package com.example.mvvm;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class TodoActivity extends Activity
{
	public static final String APP_TAG = "com.logicdrop.todos";

	private ListView taskView;
	private Button btNewTask;
	private EditText etNewTask;
    private TaskListManager delegate;

    /*The View handles UI setup only. All event logic and delegation
     *is handled by the ViewModel.
     */

    public static interface TaskListManager
    {
        //Through this interface the event logic is
        //passed off to the ViewModel.
        void registerTaskList(ListView list);
        void registerTaskAdder(View button, EditText input);
    }

	@Override
	protected void onStop()
	{
		super.onStop();
	}

	@Override
	protected void onStart()
	{
		super.onStart();
	}

	@Override
	public void onCreate(final Bundle bundle)
	{
		super.onCreate(bundle);

		this.setContentView(R.layout.main);

        this.delegate = new TodoViewModel(this);
		this.taskView = (ListView) this.findViewById(R.id.tasklist);
		this.btNewTask = (Button) this.findViewById(R.id.btNewTask);
		this.etNewTask = (EditText) this.findViewById(R.id.etNewTask);
        this.delegate.registerTaskList(taskView);
        this.delegate.registerTaskAdder(btNewTask, etNewTask);
	}
}