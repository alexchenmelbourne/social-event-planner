package com.example.android.donebar;

import android.widget.TextView;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class MyTextView extends TextView {
	public MyTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public MyTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public MyTextView(Context context) {
		super(context);
		init();
	}

	private void init() {
		if (!isInEditMode()) {
			Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
					"fonts/gothic_0.ttf");
			setTypeface(tf);
		}
	}
}
