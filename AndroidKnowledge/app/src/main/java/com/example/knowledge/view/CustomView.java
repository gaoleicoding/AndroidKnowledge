package com.example.knowledge.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.knowledge.R;

public class CustomView extends View {
	
	static final String LOG_TAG = "CustomView";
	
	public CustomView(Context context) {
		this(context, null);
	}

	public CustomView(Context context, AttributeSet attrs) {
		this(context, attrs, R.attr.customViewStyle);
	}

	public CustomView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
//		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomView, defStyle, 0);
//		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomView, defStyle, R.style.default_view_style);
//		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomView, 0, R.style.default_view_style);
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomView, 0, 0);
		
		Log.d(LOG_TAG, "attr1 => " + array.getString(R.styleable.CustomView_attr1));
		Log.d(LOG_TAG, "attr2 => " + array.getString(R.styleable.CustomView_attr2));
		Log.d(LOG_TAG, "attr3 => " + array.getString(R.styleable.CustomView_attr3));
		Log.d(LOG_TAG, "attr4 => " + array.getString(R.styleable.CustomView_attr4));
		Log.d(LOG_TAG, "attr5 => " + array.getString(R.styleable.CustomView_attr5));
		Log.d(LOG_TAG, "attr6 => " + array.getString(R.styleable.CustomView_attr6));
		
		array.recycle();
	}
	
}
