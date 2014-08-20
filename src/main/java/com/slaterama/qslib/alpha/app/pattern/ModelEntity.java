package com.slaterama.qslib.alpha.app.pattern;

import com.slaterama.qslib.alpha.app.pattern.event.UpdateEvent;

import java.util.Observable;

public abstract class ModelEntity extends Observable {

	private Model mModel;

	public Model getModel() {
		return mModel;
	}

	public void setModel(Model model) {
		mModel = model;
	}

	protected void notifyUpdated(UpdateEvent event) {
		setChanged();
		notifyObservers(event);
		// updated at ??
	}
}
