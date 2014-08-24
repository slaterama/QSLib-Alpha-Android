package com.slaterama.qslib.alpha.app.pattern.mvp;

import com.slaterama.qslib.alpha.app.pattern.Model;
import com.slaterama.qslib.alpha.app.pattern.Pattern;

public abstract class Mvp<M extends Model> extends Pattern<M> {

	public void registerPresenter(Presenter<M, ?> presenter) {
		presenter.setModel(mModel);
		mModel.addObserver(presenter);
	}

	public void unregisterPresenter(Presenter<M, ?> presenter) {
		presenter.setModel(null);
		mModel.deleteObserver(presenter);
	}
}
