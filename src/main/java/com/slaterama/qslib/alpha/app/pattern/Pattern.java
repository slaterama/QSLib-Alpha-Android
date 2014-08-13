package com.slaterama.qslib.alpha.app.pattern;

public interface Pattern {

	public void registerPresenter(Presenter presenter);

	public void unregisterPresenter(Presenter presenter);

	public void sendEvent(PatternEvent event, Object data);
}
