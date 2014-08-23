package com.slaterama.qslib.alpha.app.pattern.event;

import java.util.EventObject;

public class UpdateEvent extends EventObject {

	private Object mProperty;
	private Object mOldValue;
	private Object mNewValue;

	public UpdateEvent(Object source, Object property, Object oldValue, Object newValue) {
		super(source);
		mProperty = property;
		mOldValue = oldValue;
		mNewValue = newValue;
	}

	public Object getProperty() {
		return mProperty;
	}

	public Object getOldValue() {
		return mOldValue;
	}

	public Object getNewValue() {
		return mNewValue;
	}
}
