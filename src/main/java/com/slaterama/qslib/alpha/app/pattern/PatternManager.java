package com.slaterama.qslib.alpha.app.pattern;

import android.os.Bundle;
import android.util.SparseArray;

import java.util.Map;
import java.util.WeakHashMap;

public abstract class PatternManager {

	public static PatternManager newInstance(Object owner) {
		return PatternManagerFactory.newInstance().newPatternManager(owner);
	}

	/**
	 * A lazily-instantiated PatternMap instance holder.
	 */
	private static class LazyPatternMapHolder {
		private static final PatternMap INSTANCE = new PatternMap();
	}

	/* package */ Object mOwner;

	/* package */ PatternManager(Object owner) {
		mOwner = owner;
	}

	public Pattern initPattern(int id, Bundle args, PatternCallbacks callback) {
		return null;
	}

	/* package */ PatternMap getPatternMap() {
		return LazyPatternMapHolder.INSTANCE;
	}

	/* package */ static class PatternMap {

		/**
		 * A mapping of objects to sparse arrays of {@link Pattern}s.
		 */
		protected Map<Object, SparseArray<Pattern>> mPatternMap;

		/* package */ PatternMap() {
			mPatternMap = new WeakHashMap<Object, SparseArray<Pattern>>();
		}
	}

	/**
	 * Callback interface for a client to interact with the manager.
	 */
	public static interface PatternCallbacks {

		/**
		 * Instantiate and return a new Pattern for the given ID.
		 *
		 * @param id   The ID whose pattern is to be created.
		 * @param args Any arguments supplied by the caller.
		 * @return Return a new pattern that is ready to respond to events.
		 */
		public Pattern onCreatePattern(int id, Bundle args);
	}
}
