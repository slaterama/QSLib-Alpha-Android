package com.slaterama.qslib.alpha.util;

import java.util.Vector;

public class SimplePublisher implements Publisher {

	private Publisher mWrappedPublisher;

	protected Vector<Subscriber> mSubscribers;

	public SimplePublisher() {
		super();
		initPublisher(this);
	}

	public SimplePublisher(Publisher wrappedPublisher) {
		super();
		initPublisher(wrappedPublisher);
	}

	private void initPublisher(Publisher wrappedPublisher) {
		mWrappedPublisher = wrappedPublisher;
		mSubscribers = new Vector<>();
	}

	public Publisher getWrappedPublisher() {
		return mWrappedPublisher;
	}

	@Override
	public synchronized void addSubscriber(Subscriber subscriber) {
		if (subscriber == null)
			throw new NullPointerException();
		if (!mSubscribers.contains(subscriber)) {
			mSubscribers.addElement(subscriber);
		}
	}

	@Override
	public int countSubscribers() {
		return mSubscribers.size();
	}

	@Override
	public synchronized void deleteSubscriber(Subscriber subscriber) {
		mSubscribers.removeElement(subscriber);
	}

	@Override
	public void deleteSubscribers() {
		mSubscribers.removeAllElements();
	}

	@Override
	public void notifySubscribers() {
		notifySubscribers(null);
	}

	@Override
	public void notifySubscribers(Object data) {
		/*
         * a temporary array buffer, used as a snapshot of the state of
         * current Observers.
         */
		Object[] arrLocal;

		synchronized (this) {
            /* We don't want the Observer doing callbacks into
             * arbitrary code while holding its own Monitor.
             * The code where we extract each Observable from
             * the Vector and store the state of the Observer
             * needs synchronization, but notifying observers
             * does not (should not).  The worst result of any
             * potential race-condition here is that:
             * 1) a newly-added Observer will miss a
             *   notification in progress
             * 2) a recently unregistered Observer will be
             *   wrongly notified when it doesn't care
             */
			arrLocal = mSubscribers.toArray();
		}

		for (int i = arrLocal.length-1; i >= 0; i--)
			((Subscriber)arrLocal[i]).update(mWrappedPublisher, data);
	}
}
