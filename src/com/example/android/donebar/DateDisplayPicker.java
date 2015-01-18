package com.example.android.donebar;

import android.widget.TextView;
import java.util.Calendar;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;

public class DateDisplayPicker extends TextView implements
		DatePickerDialog.OnDateSetListener {
	private Context _context;

	public DateDisplayPicker(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		_context = context;
		init();
	}

	public DateDisplayPicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		_context = context;
		setAttributes();
		init();
	}

	public DateDisplayPicker(Context context) {
		super(context);
		_context = context;
		setAttributes();
		init();
	}

	private void setAttributes() {
		setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDateDialog();
			}
		});
	}

	private void showDateDialog() {
		final Calendar c = Calendar.getInstance();
		DatePickerDialog dp = new DatePickerDialog(_context, this,
				c.get(Calendar.YEAR), c.get(Calendar.MONTH),
				c.get(Calendar.DAY_OF_MONTH));
		dp.show();
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		setText(String.format("%s-%s-%s", monthOfYear + 1, dayOfMonth, year));
	}

	private void init() {
		if (!isInEditMode()) {
			Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
					"fonts/gothic_0.ttf");
			setTypeface(tf);
		}
	}
}
