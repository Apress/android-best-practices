package com.example.mvc;

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

import com.example.mvc.R;

public class TodoActivity extends Activity
{
    public static final String APP_TAG = "com.example.mvc";

    private ListView taskView;
    private Button btNewTask;
    private EditText etNewTask;

    /*Controller changes are transparent to the View. UI changes won't
     *affect logic, and vice-versa. See below: the TodoModel has
     * been replaced with the TodoController, and the View persists
     * without knowledge that the implementation has changed.
     */
    private TodoController provider;

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

        this.provider = new TodoController(this);
        this.taskView = (ListView) this.findViewById(R.id.tasklist);
        this.btNewTask = (Button) this.findViewById(R.id.btNewTask);
        this.etNewTask = (EditText) this.findViewById(R.id.etNewTask);
        this.btNewTask.setOnClickListener(this.handleNewTaskEvent);

        this.renderTodos();
    }

    private void renderTodos()
    {
        final List<String> beans = this.provider.getTasks();

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
