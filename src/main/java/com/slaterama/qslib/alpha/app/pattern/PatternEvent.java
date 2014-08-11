package com.slaterama.qslib.alpha.app.pattern;

public abstract class PatternEvent {

	private int mAction;

	public PatternEvent(int action) {
		super();
		setAction(action);
	}

	public void setAction(int action) {
		mAction = action;
	}

	public int getAction() {
		return mAction;
	}
}
