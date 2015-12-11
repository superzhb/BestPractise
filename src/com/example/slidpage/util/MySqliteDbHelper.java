package com.example.slidpage.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqliteDbHelper extends SQLiteOpenHelper {
	private static MySqliteDbHelper dbHelper;
	private static final String SQL = "create table note(_id integer primary key autoincrement ,content text,date text not null,encode text)";

	private MySqliteDbHelper(Context context) {
		super(context, "mydb.db", null, 1);
	}

	public static MySqliteDbHelper newIntance(Context context) {
		if (dbHelper == null) {
			dbHelper = new MySqliteDbHelper(context);
		}
		return dbHelper;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
