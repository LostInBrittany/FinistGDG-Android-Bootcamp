package org.finistgdg.bootcamp.android.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * in charge of DB lifeCycle
 */
public class BaseSQLite extends SQLiteOpenHelper {
	
	public static final String BDD = "bootcamp.db";
    public static final String NAME = "bootcamp";
    public static final int VERSION = 1;

    private static BaseSQLite sqlite = null;

    private static final String CREATE_TABLE_TWEET = "CREATE TABLE " + TweetTable.TWEET_TABLE_NAME + " ("
	+ TweetTable.COL_ID + " STRING PRIMARY KEY, " 		//1
	+ TweetTable.COL_AUTHOR + " TEXT NOT NULL, "  		//2
	+ TweetTable.COL_MESSAGE + " TEXT NOT NULL, " 		//3
	+ TweetTable.COL_TIMESTAMP + " INTEGER );"; 		//4

    public static BaseSQLite getInstance(Context context){
        if(sqlite == null){
            sqlite = new BaseSQLite(context);
        }
        return sqlite;
    }

    public BaseSQLite(Context context) {
        super(context, NAME, null, VERSION);
    }

    public BaseSQLite(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
	public void onCreate(SQLiteDatabase db) {
		Log.i("bootcamp", CREATE_TABLE_TWEET);
		db.execSQL(CREATE_TABLE_TWEET);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {		
	}
}
