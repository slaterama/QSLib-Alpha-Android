package com.slaterama.qslib.alpha.app.pattern.mvp;

import com.slaterama.qslib.alpha.app.pattern.Model;
import com.slaterama.qslib.alpha.util.Subscriber;

public abstract class Presenter<M extends Model, V> implements Subscriber {

	protected V mView;
	protected M mModel;

	public Presenter(V view) {
		super();
		mView = view;
	}

	protected void setModel(M model) {
		mModel = model;
	}
}
