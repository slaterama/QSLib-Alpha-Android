package com.slaterama.qslib.alpha.app.pattern;

import com.slaterama.qslib.alpha.app.pattern.event.CreateEvent;
import com.slaterama.qslib.alpha.app.pattern.event.DeleteEvent;
import com.slaterama.qslib.alpha.app.pattern.event.RetrieveEvent;
import com.slaterama.qslib.alpha.app.pattern.event.UpdateEvent;

import java.util.Observable;
import java.util.Observer;

public abstract class Model extends Observable
		implements Observer {

	@Override
	public void update(Observable observable, Object data) {
		// Assuming for now that the only notification we get here is
		// an UpdateEvent from one of the VOs. Just pass it along to any
		// observers.

		if (data instanceof CreateEvent
				|| data instanceof DeleteEvent
				|| data instanceof RetrieveEvent
				|| data instanceof UpdateEvent) {
			setChanged();
			notifyObservers(data);
		}
	}
}