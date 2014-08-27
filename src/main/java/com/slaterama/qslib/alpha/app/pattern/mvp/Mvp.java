package com.slaterama.qslib.alpha.app.pattern.mvp;

import com.slaterama.qslib.alpha.app.pattern.Model;
import com.slaterama.qslib.alpha.app.pattern.Pattern;

import java.util.HashSet;
import java.util.Set;

public abstract class Mvp<M extends Model> extends Pattern<M> {

	public Set<Presenter<M, ?>> mRegisteredPresenters;

	public void registerPresenter(Presenter<M, ?> presenter) {
		if (mRegisteredPresenters == null)
			mRegisteredPresenters = new HashSet<>();
		mRegisteredPresenters.add(presenter);
		presenter.setModel(mModel);
		mModel.addObserver(presenter);
	}

	public void unregisterPresenter(Presenter<M, ?> presenter) {
		if (mRegisteredPresenters != null)
			mRegisteredPresenters.remove(presenter);
		presenter.setModel(null);
		mModel.deleteObserver(presenter);
	}

	public boolean isPresenterRegistered(Presenter<M, ?> presenter) {
		return (mRegisteredPresenters != null && mRegisteredPresenters.contains(presenter));
	}
}
