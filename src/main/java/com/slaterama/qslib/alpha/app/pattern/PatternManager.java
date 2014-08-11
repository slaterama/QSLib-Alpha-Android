package com.slaterama.qslib.alpha.app.pattern;

import android.os.Bundle;
import android.util.SparseArray;

import java.util.WeakHashMap;

public abstract class PatternManager {

	/**
	 * The base tag to be used for assigning a tag to fragment(s) created by the PatternManager.
	 */
	protected static String FRAGMENT_TAG = PatternManager.class.getName();

	public static PatternManager newInstance(Object owner) {
		return PatternManagerFactory.newInstance().newPatternManager(owner);
	}

	/* package */ Object mOwner;

	/* package */ PatternManager(Object owner) {
		mOwner = owner;
	}

	public Pattern initPattern(int id, Bundle args, PatternCallbacks callback) {
		return PatternMap.getInstance().initPattern(mOwner, id, args, callback);
	}

	public Pattern getPattern(int id) {
		return PatternMap.getInstance().getPattern(mOwner, id);
	}

	// TODO public void registerListener ??

	/* package */ static class PatternMap extends WeakHashMap<Object, SparseArray<Pattern>> {

		/**
		 * A lazily-instantiated instance holder.
		 */
		/* package */ static class LazyHolder {
			/* package */ static final PatternMap INSTANCE = new PatternMap();
		}

		/* package */ static PatternMap getInstance() {
			return LazyHolder.INSTANCE;
		}

		public Pattern initPattern(Object owner, int id, Bundle args, PatternCallbacks callback) {
			SparseArray<Pattern> array = get(owner);
			if (array == null) {
				array = new SparseArray<Pattern>();
				put(owner, array);
			}
			Pattern pattern = array.get(id);
			if (pattern == null) {
				pattern = callback.onCreatePattern(id, args);
				array.put(id, pattern);
			}
			return pattern;
		}

		public Pattern getPattern(Object owner, int id) {
			SparseArray<Pattern> array = get(owner);
			return (array == null ? null : array.get(id));
		}

		public void destroyPattern(Object owner, int id) {
			SparseArray<Pattern> array = get(owner);
			if (array != null)
				array.remove(id);
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
