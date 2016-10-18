package com.logicdrop.todos;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

final class TodoProvider
{
	private static final String DB_NAME = "tasks";
	private static final String TABLE_NAME = "tasks";
	private static final int DB_VERSION = 1;
	private static final String DB_CREATE_QUERY = "CREATE TABLE " + TodoProvider.TABLE_NAME + " (id integer primary key autoincrement, title text not null);";

	// TIP: Use final wherever possible (DONE)
	private final SQLiteDatabase storage;
	private final SQLiteOpenHelper helper;

	public TodoProvider(final Context ctx)
	{
		this.helper = new SQLiteOpenHelper(ctx, TodoProvider.DB_NAME, null, TodoProvider.DB_VERSION)
		{
			@Override
			public void onCreate(final SQLiteDatabase db)
			{
				db.execSQL(TodoProvider.DB_CREATE_QUERY);
			}

			@Override
			public void onUpgrade(final SQLiteDatabase db, final int oldVersion,
					final int newVersion)
			{
				db.execSQL("DROP TABLE IF EXISTS " + TodoProvider.TABLE_NAME);
				this.onCreate(db);
			}
		};

		this.storage = this.helper.getWritableDatabase();
	}

	// TIP: Avoid synchronization (DONE)
	public void addTask(final String title)
	{
		final ContentValues data = new ContentValues();
		data.put("title", title);

		this.storage.insert(TodoProvider.TABLE_NAME, null, data);
	}

	public void deleteAll()
	{
		this.storage.delete(TodoProvider.TABLE_NAME, null, null);
	}

	public void deleteTask(final long id)
	{
		this.storage.delete(TodoProvider.TABLE_NAME, "id=" + id, null);
	}

	public void deleteTask(final String title)
	{
		this.storage.delete(TodoProvider.TABLE_NAME, "title='" + title + "'", null);
	}

	// TIP: Don't return the entire table of data. (DONE)
    // Unused
	public List<String> findAll()
	{
		Log.d(TodoActivity.APP_TAG, "findAll triggered");

		final List<String> tasks = new ArrayList<String>();

		final Cursor c = this.storage.query(TodoProvider.TABLE_NAME, new String[]
		{ "title" }, null, null, null, null, null);

		if (c != null)
		{
			c.moveToFirst();

			while (c.isAfterLast() == false)
			{
				tasks.add(c.getString(0));
				c.moveToNext();
			}

			// TIP: Close resources (DONE)
			c.close();
		}

		return tasks;
	}
}