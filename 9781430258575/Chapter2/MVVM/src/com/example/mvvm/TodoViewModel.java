package com.example.mvvm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TodoViewModel implements TodoActivity.TaskListManager
{
    /*The ViewModel acts as a delegate between the ToDoActivity (View)
     *and the ToDoProvider (Model).
     * The ViewModel receives references from the View and uses them
     * to update the UI.
     */

    private TodoModel db_model;
    private List<String> tasks;
    private Context main_activity;
    private ListView taskView;
    private EditText newTask;

    public TodoViewModel(Context app_context)
    {
        tasks = new ArrayList<String>();
        main_activity = app_context;
        db_model = new TodoModel(app_context);
    }

    //Overrides to handle View specifics and keep Model straightforward.

    private void deleteTask(View view)
    {
        db_model.deleteEntry("title='" + ((TextView)view).getText().toString() + "'");
    }

    private void addTask(View view)
    {
        final ContentValues data = new ContentValues();
        
        data.put("title", ((TextView)view).getText().toString());
        db_model.addEntry(data);
    }

    private void deleteAll()
    {
        db_model.deleteEntry(null);
    }

    private List<String> getTasks()
    {
        final Cursor c = db_model.findAll();
        tasks.clear();

        if (c != null)
        {
            c.moveToFirst();

            while (c.isAfterLast() == false)
            {
                tasks.add(c.getString(0));
                c.moveToNext();
            }
            
            c.close();
        }

        return tasks;
    }

    private void renderTodos()
    {
        //The ViewModel handles rendering and changes to the view's
        //data. The View simply provides a reference to its
        //elements.
        taskView.setAdapter(new ArrayAdapter<String>(main_activity,
                android.R.layout.simple_list_item_1,
                getTasks().toArray(new String[]
                        {})));
    }

    public void registerTaskList(ListView list)
    {
        this.taskView = list; //Keep reference for rendering later
        if (list.getAdapter() == null) //Show items at startup
        {
            renderTodos();
        }

        list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id)
            { //Tapping on any item in the list will delete that item from the database and re-render the list
                deleteTask(view);
                renderTodos();
            }
        });
    }

    public void registerTaskAdder(View button, EditText input)
    {
        this.newTask = input;
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View view)
            { //Add task to database, re-render list, and clear the input
                addTask(newTask);
                renderTodos();
                newTask.setText("");
            }
        });
    }
}
