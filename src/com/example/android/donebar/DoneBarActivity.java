/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.donebar;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.example.android.donebar.FeedReaderContract.FeedEvent;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * A sample activity demonstrating the "done bar" alternative action bar
 * presentation. For a more detailed description see
 * {@link R.string.done_bar_description}.
 */
public class DoneBarActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// BEGIN_INCLUDE (inflate_set_custom_view)
		// Inflate a "Done/Cancel" custom action bar view.
		final LayoutInflater inflater = (LayoutInflater) getActionBar()
				.getThemedContext().getSystemService(LAYOUT_INFLATER_SERVICE);
		final View customActionBarView = inflater.inflate(
				R.layout.actionbar_custom_view_done_cancel, null);
		customActionBarView.findViewById(R.id.actionbar_done)
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// "Done"
						final MyEditText mEditTitle = (MyEditText) findViewById(R.id.event_title);
						final MyEditText mEditVenue = (MyEditText) findViewById(R.id.event_venue);
						final MyEditText mEditNote = (MyEditText) findViewById(R.id.event_note);
						final MyEditText mEditAttendees = (MyEditText) findViewById(R.id.event_attendees);
						DateDisplayPicker mDate = (DateDisplayPicker) findViewById(R.id.date_picker);
						TimeDisplayPicker mTime = (TimeDisplayPicker) findViewById(R.id.time_picker_from);
						final MyTextView mEventLocation = (MyTextView) findViewById(R.id.event_location);
						
						String sD = mDate.getText().toString() + ' '
								+ mTime.getText().toString();
						SimpleDateFormat df = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm");
						Date result = null;
						try {
							result = df.parse(sD);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						final Date mResult = result;

						Event evt = new Event(mEditTitle.getText().toString(),
								mResult, mEditVenue.getText().toString(),
								mEditNote.getText().toString(), mEditAttendees
										.getText().toString());

						

						DatabaseHelper mDbHelper = new DatabaseHelper(
								getBaseContext());
						SQLiteDatabase db = mDbHelper.getWritableDatabase();

						ContentValues values = new ContentValues();
						values.put(FeedEvent.COLUMN_NAME_EVENT_ID, evt.generateId());
						values.put(FeedEvent.COLUMN_NAME_TITLE, evt.getTitle());
						values.put(FeedEvent.COLUMN_NAME_VENUE, evt.getVenue());
						values.put(FeedEvent.COLUMN_NAME_DATE, evt.getDate());
						values.put(FeedEvent.COLUMN_NAME_NOTE, evt.getNote());
						values.put(FeedEvent.COLUMN_NAME_ATTENDEES,
								evt.getAttendees());
						
						db.insert(FeedEvent.TABLE_NAME,
								FeedEvent.COLUMN_NAME_NULLABLE, values);

						Toast.makeText(getApplicationContext(),
								"The event has been saved.", Toast.LENGTH_LONG)
								.show();
						
						finish();
					}
				});
		customActionBarView.findViewById(R.id.actionbar_cancel)
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// "Cancel"
						finish();
					}
				});

		// Show the custom action bar view and hide the normal Home icon and
		// title.
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM,
				ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME
						| ActionBar.DISPLAY_SHOW_TITLE);
		actionBar.setCustomView(customActionBarView,
				new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
						ViewGroup.LayoutParams.MATCH_PARENT));
		// END_INCLUDE (inflate_set_custom_view)

		setContentView(R.layout.activity_done_bar);
	}
}
