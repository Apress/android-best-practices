package com.example.dagger;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

class TodoProvider implements IDataProvider {

	private final SQLiteDatabase storage;

	@Inject
	public TodoProvider(SQLiteDatabase db)
	{
		this.storage = db;
	}
	
	@Override
	public void addTask(final String title) {
		final ContentValues data = new ContentValues();
		data.put("title", title);

		this.storage.insert(TodoModule.TABLE_NAME, null, data);
	}

	@Override
	public void deleteAll() {
		this.storage.delete(TodoModule.TABLE_NAME, null, null);
	}

	@Override
	public void deleteTask(final long id) {
		this.storage.delete(TodoModule.TABLE_NAME, "id=" + id, null);
	}

	@Override
	public void deleteTask(final String title) {
		this.storage.delete(TodoModule.TABLE_NAME, "title='" + title + "'",
				null);
	}

	@Override
	public List<String> findAll() {
		Log.d(TodoActivity.APP_TAG, "findAll triggered");

		final List<String> tasks = new ArrayList<String>();

		final Cursor c = this.storage.query(TodoModule.TABLE_NAME,
				new String[] { "title" }, null, null, null, null, null);

		if (c != null) {
			c.moveToFirst();

			while (c.isAfterLast() == false) {
				tasks.add(c.getString(0));
				c.moveToNext();
			}

			c.close();
		}

		return tasks;
	}

}