package com.logicdrop.todos.data;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.logicdrop.todos.ToDo;

public final class ToDoProvider
{
	private static final String DB_NAME = "tasks";
	private static final String TABLE_NAME = "tasks";
	private static final int DB_VERSION = 1;
	private static final String DB_CREATE_QUERY = "CREATE TABLE " + ToDoProvider.TABLE_NAME + " (id integer primary key, title text not null);";

	private final SQLiteDatabase storage;
	private final SQLiteOpenHelper helper;

	public ToDoProvider(final Context ctx)
	{
		this.helper = new SQLiteOpenHelper(ctx, ToDoProvider.DB_NAME, null, ToDoProvider.DB_VERSION)
		{
			@Override
			public void onCreate(final SQLiteDatabase db)
			{
				db.execSQL(ToDoProvider.DB_CREATE_QUERY);
			}

			@Override
			public void onUpgrade(final SQLiteDatabase db, final int oldVersion,
					final int newVersion)
			{
				db.execSQL("DROP TABLE IF EXISTS " + ToDoProvider.TABLE_NAME);
				this.onCreate(db);
			}
		};

		this.storage = this.helper.getWritableDatabase();
	}

	public void addTask(final long id, final String title)
	{
		final ContentValues data = new ContentValues();
		data.put("title", title);
        data.put("id", id);

		this.storage.insert(ToDoProvider.TABLE_NAME, null, data);
	}

    public void deleteTask(final long id)
   	{
   		this.storage.delete(ToDoProvider.TABLE_NAME, "id=" + id, null);
   	}

   	public void deleteTask(final String title)
   	{
   		this.storage.delete(ToDoProvider.TABLE_NAME, "title='" + title + "'", null);
   	}

	public void deleteAll()
	{
		this.storage.delete(ToDoProvider.TABLE_NAME, null, null);
	}

	public ArrayList<ToDo> findAll()
	{
		final ArrayList<ToDo> tasks = new ArrayList<ToDo>();

		final Cursor c = this.storage.query(ToDoProvider.TABLE_NAME, new String[]
		{ "id", "title" }, null, null, null, null, null);

		if (c != null)
		{
			c.moveToFirst();

			while (c.isAfterLast() == false)
			{
                ToDo item = new ToDo();
                item.setId(c.getLong(0));
                item.setTitle(c.getString(1));
				tasks.add(item);
				c.moveToNext();
			}

			c.close();
		}

		return tasks;
	}
}

