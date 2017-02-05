package com.tesis.autentic.ui;

import android.app.Activity;
import android.os.Bundle;

import java.util.Stack;

public class MyActivity extends Activity {
	protected final static String TAG = "Autentic";
	private final static Stack<MyActivity> _stack = new Stack<MyActivity>();


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_stack.push(this);
	}

	public AutenticApp getApp() {
		return (AutenticApp) getApplication();
	}

	public void finish() {
		if (!_stack.isEmpty() && _stack.peek() == this)
			_stack.pop();
		super.finish();
	}

	public void finishAll() {
		MyActivity activity;
		while (!_stack.isEmpty()) {
			activity = _stack.pop();
			activity.finish();
		}
	}
}