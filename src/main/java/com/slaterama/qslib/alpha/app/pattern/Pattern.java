package com.slaterama.qslib.alpha.app.pattern;

public abstract class Pattern<M extends Model> {

	protected M mModel;

	public Pattern() {
		mModel = onCreateModel();
		if (mModel == null)
			throw new IllegalStateException("onCreateModel must return a valid Model");
	}

	protected abstract M onCreateModel();
}
