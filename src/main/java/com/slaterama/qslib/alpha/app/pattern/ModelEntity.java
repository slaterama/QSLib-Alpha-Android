package com.slaterama.qslib.alpha.app.pattern;

import com.slaterama.qslib.alpha.app.pattern.event.UpdateEvent;

import java.util.Observable;

public abstract class ModelEntity extends Observable {

	protected void notifyUpdated(UpdateEvent event) {
		setChanged();
		notifyObservers(event);
	}
}
