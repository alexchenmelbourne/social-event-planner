package com.example.android.donebar;

import android.widget.TextView;
import android.app.TimePickerDialog;
import java.util.Calendar;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;

public class TimeDisplayPicker extends TextView implements
		TimePickerDialog.OnTimeSetListener {
	private Context _context;

	public TimeDisplayPicker(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		_context = context;
		init();
	}

	public TimeDisplayPicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		_context = context;
		setAttributes();
		init();
	}

	public TimeDisplayPicker(Context context) {
		super(context);
		_context = context;
		setAttributes();
		init();
	}

	private void setAttributes() {
		setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showTimeDialog();
			}
		});
	}

	private void showTimeDialog() {
		final Calendar c = Calendar.getInstance();
		TimePickerDialog tp = new TimePickerDialog(_context, this,
				c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);
		tp.show();
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		setText(String.format("%s:%s", hourOfDay, minute));
	}

	private void init() {
		if (!isInEditMode()) {
			Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
					"fonts/gothic_0.ttf");
			setTypeface(tf);
		}
	}
}
