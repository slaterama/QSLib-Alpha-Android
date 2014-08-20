package com.slaterama.qslib.alpha.app.pattern.mvp;

import com.slaterama.qslib.alpha.app.pattern.Pattern;

import java.util.Map;
import java.util.WeakHashMap;

public abstract class Mvp extends Pattern {

//	protected Map<Presenter, Mvp> mPresenterMap;

	public Mvp() {
//		mPresenterMap = new WeakHashMap<Presenter, Mvp>();
	}

	public void registerPresenter(Presenter presenter) {
//		mPresenterMap.put(presenter, this);
		presenter.setModel(mModel);
		mModel.addObserver(presenter);
	}

	public void unregisterPresenter(Presenter presenter) {
		presenter.setModel(null);
//		mPresenterMap.remove(presenter);
		mModel.deleteObserver(presenter);
	}
}
