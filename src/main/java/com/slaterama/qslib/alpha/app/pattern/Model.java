package com.slaterama.qslib.alpha.app.pattern;

import com.slaterama.qslib.alpha.app.pattern.event.CreateEvent;
import com.slaterama.qslib.alpha.app.pattern.event.DeleteEvent;
import com.slaterama.qslib.alpha.app.pattern.event.RetrieveEvent;
import com.slaterama.qslib.alpha.app.pattern.event.UpdateEvent;
import com.slaterama.qslib.alpha.util.Publisher;
import com.slaterama.qslib.alpha.util.SimplePublisher;
import com.slaterama.qslib.alpha.util.Subscriber;

public abstract class Model
		implements Publisher, Subscriber {

	private SimplePublisher mPublisher;

	public Model() {
		super();
		mPublisher = new SimplePublisher(this);
	}

	@Override
	public void addSubscriber(Subscriber subscriber) {
		mPublisher.addSubscriber(subscriber);
	}

	@Override
	public int countSubscribers() {
		return mPublisher.countSubscribers();
	}

	@Override
	public void deleteSubscriber(Subscriber subscriber) {
		mPublisher.deleteSubscriber(subscriber);
	}

	@Override
	public void deleteSubscribers() {
		mPublisher.deleteSubscribers();
	}

	@Override
	public void notifySubscribers() {
		mPublisher.notifySubscribers();
	}

	@Override
	public void notifySubscribers(Object data) {
		mPublisher.notifySubscribers(data);
	}

	@Override
	public void update(Object publisher, Object data) {
		// Assuming for now that the only notification we get here is
		// an UpdateEvent from one of the EntityObjects. Just pass it along to any
		// observers.

		if (data instanceof CreateEvent
				|| data instanceof DeleteEvent
				|| data instanceof RetrieveEvent
				|| data instanceof UpdateEvent) {
			notifySubscribers(data);
		}
	}
}