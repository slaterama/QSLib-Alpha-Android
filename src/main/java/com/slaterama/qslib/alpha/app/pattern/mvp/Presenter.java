package com.slaterama.qslib.alpha.app.pattern.mvp;

import com.slaterama.qslib.alpha.app.pattern.Model;
import com.slaterama.qslib.alpha.app.pattern.Model.ModelEvent;

import java.util.Observable;
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

	@Override
	public void update(Observable observable, Object data) {
		if (observable instanceof Model && data instanceof ModelEvent)
			update((Model) observable, (ModelEvent) data);
	}

	public void update(Model model, ModelEvent event) {

	}

	public static abstract interface IView {

	}
}
