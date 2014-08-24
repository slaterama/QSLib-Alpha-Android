package com.slaterama.qslib.alpha.app.pattern;

import com.slaterama.qslib.alpha.app.pattern.event.CreateEvent;
import com.slaterama.qslib.alpha.app.pattern.event.DeleteEvent;
import com.slaterama.qslib.alpha.app.pattern.event.RetrieveEvent;
import com.slaterama.qslib.alpha.app.pattern.event.UpdateEvent;

import java.util.Observable;

public abstract class ObservableVO extends Observable {

	public void notifyCreated() {
		setChanged();
		notifyObservers(new CreateEvent(this));
	}

	public void notifyDeleted() {
		setChanged();
		notifyObservers(new DeleteEvent(this));
	}

	public void notifyRetrieved() {
		setChanged();
		notifyObservers(new RetrieveEvent(this));
	}

	public void notifyUpdated(String property, Object oldValue, Object newValue, boolean callOnUpdated) {
		setChanged();
		notifyObservers(new UpdateEvent(this, property, oldValue, newValue));
		if (callOnUpdated)
			onUpdated(property, oldValue, newValue);
	}

	public void notifyUpdated(String property, Object oldValue, Object newValue) {
		notifyUpdated(property, oldValue, newValue, true);
	}

	public void onUpdated(String property, Object oldValue, Object newValue) {

	}
}
