package com.example;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

final class TodoProvider
{
	private static final int DB_VERSION = 1;
	private static final String DB_NAME = "examples";
	private static final String DB_TABLE = "todos";
	private static final String DB_TABLE_CREATE = "CREATE TABLE " +
			TodoProvider.DB_TABLE
			+ " (id integer primary key autoincrement, title text not null);";

	private final SQLiteDatabase database;
	private final SQLiteOpenHelper helper;

	public TodoProvider(final Context ctx)
	{
		this.helper = new SQLiteOpenHelper(ctx, TodoProvider.DB_NAME, null, TodoProvider.DB_VERSION)
		{
			@Override
			public void onCreate(final SQLiteDatabase db)
			{
				db.execSQL(TodoProvider.DB_TABLE_CREATE);
			}

			@Override
			public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion)
			{
				db.execSQL("DROP TABLE IF EXISTS " + TodoProvider.DB_TABLE);
				this.onCreate(db);
			}
		};

		this.database = this.helper.getWritableDatabase();
	}

	public void addTask(final String title)
	{
		final ContentValues data = new ContentValues();
		data.put("title", title);
		this.database.insert(TodoProvider.DB_TABLE, null, data);
	}

	public void deleteTask(final long id)
	{
		this.database.delete(TodoProvider.DB_TABLE, "id=" + id, null);
	}

	public void deleteTask(final String title)
	{
		this.database.delete(TodoProvider.DB_TABLE, "title='" + title + "'", null);
	}

	public List<String> findAll()
	{
		final List<String> results = new ArrayList<String>();

		final Cursor c = this.database
				.query(TodoProvider.DB_TABLE,
						new String[] { "title" },
						null,
						null,
						null,
						null,
						null);
		if (c != null)
		{
			c.moveToFirst();

			while (c.isAfterLast() == false)
			{
				results.add(c.getString(0));
				c.moveToNext();
			}

			c.close();
		}

		return results;
	}
}
