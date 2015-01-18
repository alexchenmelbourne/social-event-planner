package com.example.android.donebar;

import java.util.ArrayList;
import java.util.List;

import com.example.android.donebar.FeedReaderContract.FeedEvent;

import android.content.Context;import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	public static final String TEXT_TYPE = " TEXT";
	public static final String DATE_TYPE = " DATE";
	private static final String COMMA_SEP = ",";

	private static final String SQL_CREATE_EVENTS = "CREATE TABLE "
			+ FeedEvent.TABLE_NAME + " (" + FeedEvent._ROWID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT," + FeedEvent.COLUMN_NAME_EVENT_ID + TEXT_TYPE
			+ COMMA_SEP + FeedEvent.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP
			+ FeedEvent.COLUMN_NAME_VENUE + TEXT_TYPE + COMMA_SEP
			+ FeedEvent.COLUMN_NAME_DATE + DATE_TYPE + COMMA_SEP
			+ FeedEvent.COLUMN_NAME_NOTE + TEXT_TYPE + COMMA_SEP
			+ FeedEvent.COLUMN_NAME_ATTENDEES + TEXT_TYPE + " )";

	private static final String SQL_DELETE_EVENTS = "DROP TABLE IF EXISTS "
			+ FeedEvent.TABLE_NAME;

	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "FeedReader";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		// TODO Auto-generated method stub
		db.execSQL(SQL_CREATE_EVENTS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL(SQL_DELETE_EVENTS);
		onCreate(db);

	}

	public List<Event> getEvents() {
		List<Event> eventList = new ArrayList<Event>();

		String selectQuery = "SELECT * FROM " + FeedEvent.TABLE_NAME;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Event evt = new Event();
				Log.i("rowId", cursor.getString(0));
				evt.setTitle(cursor.getString(2));
				evt.setVenue(cursor.getString(3));;
				evt.setDateString(cursor.getString(4));
				evt.setNote(cursor.getString(5));
				evt.setAttendees(cursor.getString(6));
				eventList.add(evt);
			} while (cursor.moveToNext());
		}
		return eventList;
	}
	
	public void deleteValues(int position) {
		SQLiteDatabase db = this.getWritableDatabase();
		position = position + 1;
		String positionStr = String.valueOf(position);
		Log.i("positionStr", positionStr);
		Log.i("_id", FeedEvent._ID);
		db.delete(FeedEvent.TABLE_NAME, FeedEvent._ROWID + "=" + positionStr, null );
		
		String CREATE_TABLE_COPY = "CREATE TABLE " + "COPIED_TABLE" +"("+ FeedEvent._ROWID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + FeedEvent.COLUMN_NAME_EVENT_ID + TEXT_TYPE
				+ COMMA_SEP + FeedEvent.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP
				+ FeedEvent.COLUMN_NAME_VENUE + TEXT_TYPE + COMMA_SEP
				+ FeedEvent.COLUMN_NAME_DATE + DATE_TYPE + COMMA_SEP
				+ FeedEvent.COLUMN_NAME_NOTE + TEXT_TYPE + COMMA_SEP
				+ FeedEvent.COLUMN_NAME_ATTENDEES + TEXT_TYPE + " )";
		db.execSQL(CREATE_TABLE_COPY);
		
		String db_insert_command = "INSERT INTO COPIED_TABLE (" + FeedEvent.COLUMN_NAME_TITLE +", " + FeedEvent.COLUMN_NAME_VENUE + ", " + FeedEvent.COLUMN_NAME_DATE + ", " + FeedEvent.COLUMN_NAME_NOTE + ", " + FeedEvent.COLUMN_NAME_ATTENDEES + ") SELECT " + FeedEvent.COLUMN_NAME_TITLE +", " + FeedEvent.COLUMN_NAME_VENUE + ", " + FeedEvent.COLUMN_NAME_DATE + ", " + FeedEvent.COLUMN_NAME_NOTE + ", " + FeedEvent.COLUMN_NAME_ATTENDEES + " FROM "+ FeedEvent.TABLE_NAME;
		Log.i("db_insert_command", db_insert_command);
		db.execSQL(db_insert_command);
		
		db.execSQL("DROP TABLE " + FeedEvent.TABLE_NAME);
		
		String SQL_CREATE_EVENTS = "CREATE TABLE "
				+ FeedEvent.TABLE_NAME + " (" + FeedEvent._ROWID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + FeedEvent.COLUMN_NAME_EVENT_ID + TEXT_TYPE
				+ COMMA_SEP + FeedEvent.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP
				+ FeedEvent.COLUMN_NAME_VENUE + TEXT_TYPE + COMMA_SEP
				+ FeedEvent.COLUMN_NAME_DATE + DATE_TYPE + COMMA_SEP
				+ FeedEvent.COLUMN_NAME_NOTE + TEXT_TYPE + COMMA_SEP
				+ FeedEvent.COLUMN_NAME_ATTENDEES + TEXT_TYPE + " )";
		db.execSQL(SQL_CREATE_EVENTS);
		
		db.execSQL("INSERT INTO " + FeedEvent.TABLE_NAME + " SELECT * FROM COPIED_TABLE");
        db.execSQL("DROP TABLE COPIED_TABLE");
        
        db.close();
	}
}
