package com.slaterama.qslib.alpha.app.pattern;

import com.slaterama.qslib.alpha.util.Publisher;
import com.slaterama.qslib.alpha.util.SimplePublisher;
import com.slaterama.qslib.alpha.util.Subscriber;

public abstract class EntityObject implements Publisher {

	private SimplePublisher mPublisher;

	public EntityObject() {
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
}
