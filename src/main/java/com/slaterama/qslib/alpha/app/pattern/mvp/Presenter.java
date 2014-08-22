package com.slaterama.qslib.alpha.app.pattern.mvp;

import com.slaterama.qslib.alpha.app.pattern.Model;

import java.util.Observer;

public abstract class Presenter implements Observer {

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
