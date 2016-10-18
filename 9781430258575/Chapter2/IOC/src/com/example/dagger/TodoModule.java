package com.example.dagger;


import dagger.Module;
import dagger.Provides;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

@Module(complete = true, injects = { TodoActivity.class })
public class TodoModule {

	static final String DB_NAME = "tasks";
	static final String TABLE_NAME = "tasks";
	static final int DB_VERSION = 1;
	static final String DB_CREATE_QUERY = "CREATE TABLE "
			+ TodoModule.TABLE_NAME
			+ " (id integer primary key autoincrement, title text not null);";

	private final Context appContext;
	public static boolean sourceToggle = false;
	private TodoApplication parent;

	/** Constructs this module with the application context. */
	public TodoModule(TodoApplication app) {
		this.parent = app;
		this.appContext = app.getApplicationContext();
		
	}

	@Provides
	public Context provideContext() {
		return appContext;
	}
	
	/**
	 * Needed because we need to provide an implementation to an interface not a
	 * class.
	 * 
	 * @return
	 */
	@Provides
	IDataProvider provideDataProvider(final SQLiteDatabase db) {
		//Here I obtain the boolean value for which provider I want
		boolean currentChoice = parent.getCurrentSource();
		if(currentChoice == true){
			//Here is a log message to know which provider has been chosen
			Log.d(TodoActivity.APP_TAG, "Provider2");
			return new TodoProvider2(db);
		}else{
			Log.d(TodoActivity.APP_TAG, "Provider");
			return new TodoProvider(db);
		}
	}

	/**
	 * Needed because we need to configure the helper before injecting it.
	 * 
	 * @return
	 */
	@Provides
	SQLiteOpenHelper provideSqlHelper() {
		final SQLiteOpenHelper helper = new SQLiteOpenHelper(this.appContext,
				TodoModule.DB_NAME, null, TodoModule.DB_VERSION) {
			@Override
			public void onCreate(final SQLiteDatabase db) {
				db.execSQL(TodoModule.DB_CREATE_QUERY);
			}

			@Override
			public void onUpgrade(final SQLiteDatabase db,
					final int oldVersion, final int newVersion) {
				db.execSQL("DROP TABLE IF EXISTS " + TodoModule.TABLE_NAME);
				this.onCreate(db);
			}
		};

		return helper;
	}

	@Provides
	SQLiteDatabase provideDatabase(SQLiteOpenHelper helper) {
		return helper.getWritableDatabase();
	}
}
