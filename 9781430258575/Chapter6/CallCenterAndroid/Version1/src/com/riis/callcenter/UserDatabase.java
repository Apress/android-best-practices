 package com.riis.callcenter;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabase extends SQLiteOpenHelper {

    // All Static variables
    // Database Name
    private static final String DATABASE_NAME = "Data_Manager.db";
 
    // Table names
    private static final String TABLE = "testTable";

    // Table Columns names
    private static final String KEY_DATE = "date";
    private static final String KEY_LOC = "loc";
    
    private SQLiteDatabase db;

 
    public UserDatabase(Context context) {
    	super(context, DATABASE_NAME, null, 1);
    	
    	String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE + " ("
        		+ KEY_DATE + " INTEGER PRIMARY KEY, "
                + KEY_LOC + " TEXT NOT NULL)";
        db.execSQL(CREATE_TABLE);
    }
 
	public void addData(Data data) {
	    ContentValues values = new ContentValues();
	    values.put(KEY_DATE, data.date);
	    values.put(KEY_LOC, data.loc);
	 
	    // Inserting Row
	    db.insert(TABLE, null, values);
	    db.close(); // Closing database connection
	}
	
    String getData(int date) {
        Cursor cursor = db.query(TABLE, new String[] { KEY_DATE, KEY_LOC}, KEY_DATE + "=?",
                new String[] { String.valueOf(date) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        String loc = cursor.getString(1);
        
        return loc;
    }
    
    public List<Data> getAllData() {
        List<Data> dataList = new ArrayList<Data>();
        String selectQuery = "SELECT  * FROM " + TABLE;
 
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	Data data = new Data();
                data.date = Integer.parseInt(cursor.getString(0));
                data.loc = cursor.getString(1);
                // Adding contact to list
                dataList.add(data);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return dataList;
    }
    
    public void deleteData(int date) {
        String DELETE_USERINFO = "DELETE FROM" + TABLE + " " +
        						 "WHERE " + KEY_DATE + "='" + date + "'";
    	db.execSQL(DELETE_USERINFO);
    	
    	String DELETE_DATA = "DELETE FROM" + TABLE + " " +
				 "WHERE " + KEY_DATE + "='" + date + "'";
    	db.execSQL(DELETE_DATA);
        db.close();
    }
 
    public int getUserCount() {
        String countQuery = "SELECT  * FROM " + TABLE;
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
