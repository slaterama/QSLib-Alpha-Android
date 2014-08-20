package com.slaterama.qslib.alpha.app.pattern.event;

import java.util.EventObject;

public class UpdateEvent extends EventObject {

	private String mPropertyName;
	private Object mOldValue;
	private Object mNewValue;

	public UpdateEvent(Object source, String propertyName, Object oldValue, Object newValue) {
		super(source);
		mPropertyName = propertyName;
		mOldValue = oldValue;
		mNewValue = newValue;
	}

	public String getPropertyName() {
		return mPropertyName;
	}

	public Object getOldValue() {
		return mOldValue;
	}

	public Object getNewValue() {
		return mNewValue;
	}
}
