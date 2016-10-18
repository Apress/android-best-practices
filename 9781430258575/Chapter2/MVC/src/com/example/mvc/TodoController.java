package com.example.mvc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class TodoController {
    /*The Controller provides data from the Model for the View
     *to bind to the UI.
     */

    private TodoModel db_model;
    private List<String> tasks;

    public TodoController(Context app_context)
    {
        tasks = new ArrayList<String>();
        db_model = new TodoModel(app_context);
    }

    public void addTask(final String title)
    {
        final ContentValues data = new ContentValues();
        data.put("title", title);
        db_model.addEntry(data);
    }

    //Overrides to handle View specifics and keep Model straightforward.
    public void deleteTask(final String title)
    {
        db_model.deleteEntry("title='" + title + "'");
    }

    public void deleteTask(final long id)
    {
        db_model.deleteEntry("id='" + id + "'");
    }

    public void deleteAll()
    {
        db_model.deleteEntry(null);
    }

    public List<String> getTasks()
    {
        Cursor c = db_model.findAll();
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
}

