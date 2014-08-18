package com.slaterama.qslib.alpha.support.v4.app;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.SparseArray;

import com.slaterama.qslib.alpha.app.pattern.Pattern;

/**
 * Interface for interacting with {@link Pattern} objects.
 */
@TargetApi(4)
public class PatternManager extends RetainedInstanceManager {

	/**
	 * Private key for registering an instance of {@code RetainedPatternArray} to retain
	 * patterns across Activity re-creation (such as from a configuration change).
	 */
	private static final RetainedSystemInstanceKey KEY = new RetainedSystemInstanceKey();

	/**
	 * Factory method used to create a PatternManager using a {@link FragmentActivity}.
	 *
	 * @param activity The {@link FragmentActivity} used to create the PatternManager.
	 * @return The newly-created PatternManager.
	 */
	public static PatternManager newInstance(FragmentActivity activity) {
		if (activity == null)
			throw new IllegalArgumentException("Activity can not be null");
		return new PatternManager(activity.getSupportFragmentManager());
	}

	/**
	 * Factory method used to create a PatternManager using a {@link Fragment}.
	 *
	 * @param fragment The {@link Fragment} used to create the PatternManager.
	 * @return The newly-created PatternManager.
	 */
	public static PatternManager newInstance(Fragment fragment) {
		if (fragment == null)
			throw new IllegalArgumentException("Fragment can not be null");
		return new PatternManager(fragment.getFragmentManager());
	}

	/**
	 * Factory method used to create a PatternManager using a {@link FragmentManager}.
	 *
	 * @param fragmentManager The {@link FragmentManager} used to create the PatternManager.
	 * @return The newly-created PatternManager.
	 */
	public static PatternManager newInstance(FragmentManager fragmentManager) {
		if (fragmentManager == null)
			throw new IllegalArgumentException("FragmentManager can not be null");
		return new PatternManager(fragmentManager);
	}

	/**
	 * Constructor.
	 *
	 * @param fragmentManager The {@link FragmentManager} that will host the fragment used to
	 *                        store retained {@link Pattern} instances.
	 */
	protected PatternManager(FragmentManager fragmentManager) {
		super(fragmentManager);
	}

	/**
	 * Helper method to get the retained {@link Pattern} array.
	 *
	 * @return The retained {@link Pattern} array.
	 */
	private RetainedPatternArray getRetainedPatternArray() {
		return (RetainedPatternArray) getRetainedFragment().getRetainedSystemInstances().get(KEY);
	}

	/**
	 * Ensures a pattern is initialized and active. If the pattern doesn't already exist,
	 * one is created and (if the activity/fragment is currently started). Otherwise the last
	 * created pattern is re-used.
	 * <p>In either case, the given callback is associated with the pattern, and will be called
	 * as the pattern state changes.</p>
	 *
	 * @param id       A unique identifier for this pattern. Can be whatever you want. Identifiers are scoped
	 *                 to a particular PatternManager instance.
	 * @param args     Optional arguments to supply to the pattern at construction. If a pattern already exists
	 *                 (a new one does not need to be created), this parameter will be ignored and the last
	 *                 arguments continue to be used.
	 * @param callback Interface the PatternManager will call to report about changes in the state
	 *                 of the pattern. Required.
	 * @return
	 */
	public Pattern initPattern(int id, Bundle args, PatternCallbacks callback) {
		RetainedPatternArray patternArray = getRetainedPatternArray();
		if (patternArray == null) {
			patternArray = new RetainedPatternArray();
			getRetainedFragment().getRetainedSystemInstances().put(KEY, patternArray);
		}
		Pattern pattern = patternArray.get(id);
		if (pattern == null) {
			pattern = callback.onCreatePattern(id, args);
			patternArray.put(id, pattern);
		}
		return pattern;
	}

	/**
	 * Return the Pattern with the given id or {@code null} if no matching Pattern is found.
	 *
	 * @param id A unique identifier for this pattern. Can be whatever you want. Identifiers are scoped
	 *           to a particular PatternManager instance.
	 * @return The pattern with the given id or {@code null} if no matching Pattern is found.
	 */
	public Pattern getPattern(int id) {
		RetainedPatternArray patternArray = getRetainedPatternArray();
		return (patternArray == null ? null : patternArray.get(id));
	}

	/**
	 * Stops and removes the pattern with the given ID.
	 * @param id The ID of the pattern you want to remove.
	 */
	public void destroyPattern(int id) {
		RetainedPatternArray patternArray = getRetainedPatternArray();
		if (patternArray != null)
			patternArray.remove(id);
	}

	/**
	 * A {@link SparseArray} of retained {@link Pattern}s by pattern ID.
	 */
	private static class RetainedPatternArray extends SparseArray<Pattern>
			implements RetainedSystemInstanceValue {
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
