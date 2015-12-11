package com.example.slidpage.contentprovider;

import com.example.slidpage.util.MySqliteDbHelper;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class NoteContentProvider extends ContentProvider {
	private MySqliteDbHelper dbHelper;
	private static final UriMatcher sURIMatcher = new UriMatcher(
			UriMatcher.NO_MATCH);
	private static final int NOTE = 0;
	private static final int NOTE_ID = 1;
	static {
		sURIMatcher.addURI("com.example.slidpage.contentprovider", "note", NOTE);
		sURIMatcher.addURI("com.example.slidpage.contentprovider", "note/#", NOTE_ID);
	}

	@Override
	public boolean onCreate() {
		try {
			dbHelper = MySqliteDbHelper.newIntance(getContext());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		Cursor cursor = null;
		switch (sURIMatcher.match(uri)) {
		case NOTE:
			cursor = database.query("note", projection, selection,
					selectionArgs, null, null, sortOrder);
			break;
		case NOTE_ID:
			long id = ContentUris.parseId(uri);
			cursor = database.query("note", projection, "_id=?",
					new String[] { id + "" }, null, null, null);
			break;

		default:
			break;
		}
		return cursor;
	}

	@Override
	public String getType(Uri uri) {
		int match = sURIMatcher.match(uri);
		switch (match) {
		case NOTE:
			return "vnd.android.cursor.dir/note";
		case NOTE_ID:
			return "vnd.android.cursor.item/note";
		default:
			return null;
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		switch (sURIMatcher.match(uri)) {
		case NOTE:
			long id = database.insert("note", "name", values);
			uri = ContentUris.withAppendedId(uri, id);
			return uri;
		default:
			return null;
		}
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		switch (sURIMatcher.match(uri)) {
		case NOTE:
			return database.delete("note", selection, selectionArgs);
		case NOTE_ID:
			long id = ContentUris.parseId(uri);
			return database.delete("note", "_id=?", new String[] { id + "" });
		default:
		}
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		switch (sURIMatcher.match(uri)) {
		case NOTE:
			return database.update("note", values, selection, selectionArgs);
		case NOTE_ID:
			long id = ContentUris.parseId(uri);
			return database.update("note", values, "id=?", new String[] { id
					+ "" });
		default:
			return 0;
		}
	}

}
