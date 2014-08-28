package com.slaterama.qslib.alpha.util;

public interface Publisher {

	public void addSubscriber(Subscriber subscriber);

	public int countSubscribers();

	public void deleteSubscriber(Subscriber subscriber);

	public void deleteSubscribers();

	public void notifySubscribers();

	public void notifySubscribers(Object data);

}
