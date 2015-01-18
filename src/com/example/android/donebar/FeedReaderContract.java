package com.example.android.donebar;

import android.provider.BaseColumns;

public class FeedReaderContract {
	public FeedReaderContract() {
	}

	public static abstract class FeedEvent implements BaseColumns {
		public static final String TABLE_NAME = "event";
		public static final String _ROWID ="_id";
		public static final String COLUMN_NAME_EVENT_ID = "id";
		public static final String COLUMN_NAME_TITLE = "title";
		public static final String COLUMN_NAME_VENUE = "venue";
		public static final String COLUMN_NAME_DATE = "date";
		public static final String COLUMN_NAME_NOTE = "note";
		public static final String COLUMN_NAME_ATTENDEES = "attendees";
		public static final String COLUMN_NAME_NULLABLE = "note";
	}
}
