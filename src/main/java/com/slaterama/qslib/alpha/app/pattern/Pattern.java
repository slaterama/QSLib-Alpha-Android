package com.slaterama.qslib.alpha.app.pattern;

import java.util.Map;
import java.util.WeakHashMap;

public abstract class Pattern {

	protected Map<Presenter, Pattern> mPresenterMap;

	public Pattern() {
		mPresenterMap = new WeakHashMap<Presenter, Pattern>();
	}

	public void registerPresenter(Presenter presenter) {
		mPresenterMap.put(presenter, this);
	}

	public void unregisterPresenter(Presenter presenter) {
		mPresenterMap.remove(presenter);
	}
}
