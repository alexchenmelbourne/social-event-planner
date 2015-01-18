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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.android.donebar.FeedReaderContract.FeedEvent;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

/**
 * A sample activity demonstrating the "done button" alternative action bar
 * presentation. For a more detailed description see
 * {@link R.string.done_button_description}.
 */
public class DoneButtonActivity extends ListActivity {
	AlertDialog.Builder alert;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		alert = new AlertDialog.Builder(this);

		// BEGIN_INCLUDE (inflate_set_custom_view)
		// Inflate a "Done" custom action bar view to serve as the "Up"
		// affordance.
		/*
		 * final LayoutInflater inflater = (LayoutInflater) getActionBar()
		 * .getThemedContext().getSystemService(LAYOUT_INFLATER_SERVICE); final
		 * View customActionBarView = inflater.inflate(
		 * R.layout.actionbar_custom_view_done, null);
		 * customActionBarView.findViewById(R.id.actionbar_done)
		 * .setOnClickListener(new View.OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // "Done" finish(); } });
		 * 
		 * // Show the custom action bar view and hide the normal Home icon and
		 * // title. final ActionBar actionBar = getActionBar();
		 * actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM,
		 * ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME |
		 * ActionBar.DISPLAY_SHOW_TITLE);
		 * actionBar.setCustomView(customActionBarView); // END_INCLUDE
		 * (inflate_set_custom_view)
		 * 
		 * setContentView(R.layout.activity_done_button);
		 */

		final DatabaseHelper db = new DatabaseHelper(this);

		ArrayList<HashMap<String, String>> Events = new ArrayList<HashMap<String, String>>();
		List<Event> event = db.getEvents();

		for (Event val : event) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("title", val.getTitle());
			map.put("venue", val.getVenue());
			map.put("date", val.getDateString());
			map.put("note", val.getNote());
			map.put("attendees", val.getAttendees());

			Events.add(map);
		}

		ListAdapter adapter = new SimpleAdapter(this, Events,
				R.layout.event_list_view, new String[] {
						FeedEvent.COLUMN_NAME_TITLE,
						FeedEvent.COLUMN_NAME_VENUE,
						FeedEvent.COLUMN_NAME_DATE, FeedEvent.COLUMN_NAME_NOTE,
						FeedEvent.COLUMN_NAME_ATTENDEES }, new int[] {
						R.id.list_view_title, R.id.list_view_venue,
						R.id.list_view_date, R.id.list_view_note,
						R.id.list_view_attendees });
		setListAdapter(adapter);

		ListView lv = getListView();
		lv.setLongClickable(true);

		lv.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int position, final long rowId) {
				alert.setTitle("Confirm Delete");

				alert.setMessage("Do you want to DELETE this entry?")
						.setCancelable(false)
						.setPositiveButton("Do it!",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// if this button is clicked, close
										// current activity
										db.deleteValues(position);
										Intent i = new Intent(
												getApplicationContext(),
												DoneButtonActivity.class);
										startActivity(i);
										finish();

									}
								})
						.setNegativeButton("I need this",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// if this button is clicked, just close
										// the dialog box and do nothing
										dialog.cancel();
									}
								});

				// create alert dialog
				AlertDialog alertDialog = alert.create();

				// show it
				alertDialog.show	();
				return true;
			}
		});
		
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, final int pos, long id) {
				Intent intent = new Intent(DoneButtonActivity.this, DoneBarActivity.class);
				startActivity(intent);
			}
		});
	}

	// BEGIN_INCLUDE (handle_cancel)
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.cancel, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.cancel:
			// "Cancel"
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	// END_INCLUDE (handle_cancel)
}
