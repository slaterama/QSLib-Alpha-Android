package com.slaterama.qslib.alpha.app.pattern;

import java.util.Observable;

public abstract class Model extends Observable {

	public static class ModelEvent {
		private String mAction;
		private Object mWhat;
		private String mProperty;
		private Object mValue;

		public ModelEvent(String action, Object what, String property, Object value) {
			mAction = action;
			mWhat = what;
			mProperty = property;
			mValue = value;
		}

		public ModelEvent(String action, Object value) {
			this(action, null, null, value);
		}

		public String getAction() {
			return mAction;
		}

		public void setAction(String action) {
			mAction = action;
		}

		public Object getWhat() {
			return mWhat;
		}

		public void setWhat(Object what) {
			mWhat = what;
		}

		public String getProperty() {
			return mProperty;
		}

		public void setProperty(String property) {
			mProperty = property;
		}

		public Object getValue() {
			return mValue;
		}

		public void setValue(Object value) {
			mValue = value;
		}
	}
}