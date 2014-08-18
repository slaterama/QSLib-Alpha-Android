package com.slaterama.qslib.alpha.app.pattern.mvp;

import com.slaterama.qslib.alpha.app.pattern.Model;

public abstract class Presenter {

	protected IView mView;
	protected Model mModel;

	public Presenter(IView view) {
		super();
		mView = view;
	}

	protected void setModel(Model model) {
		mModel = model;
	}

	public static abstract interface IView {

	}
}
