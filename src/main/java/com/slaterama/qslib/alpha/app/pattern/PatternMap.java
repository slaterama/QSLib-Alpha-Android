package com.slaterama.qslib.alpha.app.pattern;

import android.util.SparseArray;

import java.util.WeakHashMap;

public class PatternMap {

	/**
	 * Gets the singleton instance of PatternMap.
	 *
	 * @return The singleton instance.
	 */
	public static PatternMap getInstance() {
		return LazyHolder.INSTANCE;
	}

	/**
	 * A lazily-instantiated instance holder.
	 */
	private static class LazyHolder {
		private static final PatternMap INSTANCE = new PatternMap();
	}

	/**
	 * A mapping of objects to sparse arrays of {@link Pattern}s.
	 */
	protected WeakHashMap<Object, SparseArray<Pattern>> mPatternMap;

}
