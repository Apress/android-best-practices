package com.example.mvc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

final class TodoModel
{
    private static final String DB_NAME = "tasks";
    private static final String TABLE_NAME = "tasks";
    private static final int DB_VERSION = 1;
    private static final String DB_CREATE_QUERY = "CREATE TABLE " + TodoModel.TABLE_NAME + " (id integer primary key autoincrement, title text not null);";

    private final SQLiteDatabase storage;
    private final SQLiteOpenHelper helper;

    public TodoModel(final Context ctx)
    {
        this.helper = new SQLiteOpenHelper(ctx, TodoModel.DB_NAME, null, TodoModel.DB_VERSION)
        {
            @Override
            public void onCreate(final SQLiteDatabase db)
            {
                db.execSQL(TodoModel.DB_CREATE_QUERY);
            }

            @Override
            public void onUpgrade(final SQLiteDatabase db, final int oldVersion,
                                  final int newVersion)
            {
                db.execSQL("DROP TABLE IF EXISTS " + TodoModel.TABLE_NAME);
                this.onCreate(db);
            }
        };

        this.storage = this.helper.getWritableDatabase();
    }

    public void addEntry(ContentValues data)
    {
        this.storage.insert(TodoModel.TABLE_NAME, null, data);
    }

    public void deleteEntry(final String field_params)
    {
        this.storage.delete(TodoModel.TABLE_NAME, field_params, null);
    }

    public Cursor findAll()
    {
        Log.d(TodoActivity.APP_TAG, "findAll triggered");

        final Cursor c = this.storage.query(TodoModel.TABLE_NAME, new String[]
                { "title" }, null, null, null, null, null);

        return c;
    }
}