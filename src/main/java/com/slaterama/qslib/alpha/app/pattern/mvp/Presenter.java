package com.slaterama.qslib.alpha.app.pattern.mvp;

import com.slaterama.qslib.alpha.app.pattern.Model;

import java.util.Observer;

public abstract class Presenter<M extends Model, V> implements Observer {

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
