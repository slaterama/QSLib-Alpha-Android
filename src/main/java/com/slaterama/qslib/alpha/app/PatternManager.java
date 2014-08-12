package com.slaterama.qslib.alpha.app;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseArray;

import com.slaterama.qslib.alpha.app.pattern.Pattern;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class PatternManager {
	public static final String TAG = PatternManager.class.getName();

	public static PatternManager get(FragmentManager fragmentManager) {
		if (fragmentManager == null)
			throw new IllegalArgumentException("FragmentManager can not be null");
		PatternFragment patternFragment = (PatternFragment) fragmentManager.findFragmentByTag(TAG);
		if (patternFragment == null) {
			patternFragment = new PatternFragment();
			fragmentManager.beginTransaction()
					.add(patternFragment, TAG)
					.commit();
			fragmentManager.executePendingTransactions();
		}
		return patternFragment.getPatternManager();
	}

	private SparseArray<Pattern> mPatternArray;

	public Pattern initPattern(int id, Bundle args, PatternCallbacks callback) {
		if (mPatternArray == null)
			mPatternArray = new SparseArray<Pattern>();
		Pattern pattern = mPatternArray.get(id);
		if (pattern == null) {
			pattern = callback.onCreatePattern(id, args);
			mPatternArray.put(id, pattern);
		}
		return pattern;
	}

	public Pattern getPattern(int id) {
		return (mPatternArray == null ? null : mPatternArray.get(id));
	}

	public static class PatternFragment extends Fragment {
		private PatternManager mPatternManager;

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setRetainInstance(true);
			mPatternManager = new PatternManager();
		}

		public PatternManager getPatternManager() {
			return mPatternManager;
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
