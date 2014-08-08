package com.slaterama.qslib.alpha.app.architecture;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.SparseArray;

public class ArchitectureManagerFroyo extends ArchitectureManagerBase {

	/**
	 * The base tag to be used for assigning a tag to fragment(s) created by the ArchitectureManager.
	 */
	protected static String FRAGMENT_TAG_BASE = ArchitectureManager.class.getName();

	public Architecture getArchitecture(FragmentActivity activity, int id, Bundle args, ArchitectureCallbacks callback) {
		if (activity == null)
			throw new IllegalArgumentException("Activity may not be null");

		return getSupportArchitecture(activity.getSupportFragmentManager(), id, args, callback);
	}

	public Architecture getArchitecture(Fragment fragment, int id, Bundle args, ArchitectureCallbacks callback) {
		if (fragment == null)
			throw new IllegalArgumentException("Fragment may not be null");

		if (fragment.getRetainInstance())
			return getArchitectureInternal(fragment, id, args, callback);

		return getSupportArchitecture(fragment.getFragmentManager(), id, args, callback);
	}

	private Architecture getSupportArchitecture(FragmentManager fragmentManager, int id, Bundle args, ArchitectureCallbacks callback) {
		String tag = String.format("%s.%d", FRAGMENT_TAG_BASE, id);
		ArchitectureHolderSupportFragment fragment = (ArchitectureHolderSupportFragment) fragmentManager.findFragmentByTag(tag);
		if (fragment == null) {
			fragment = new ArchitectureHolderSupportFragment();
			fragmentManager.beginTransaction()
					.add(fragment, tag)
					.commit();
			fragmentManager.executePendingTransactions();
		}

		Architecture architecture = fragment.getArchitecture(id);
		if (architecture == null && callback != null) {
			architecture = callback.onCreateArchitecture(id, args);
			fragment.putArchitecture(id, architecture);
		}

		return architecture;
	}

	public static class ArchitectureHolderSupportFragment extends Fragment {

		private SparseArray<Architecture> mArchitectureSparseArray;

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setRetainInstance(true);
		}

		public void putArchitecture(int id, Architecture architecture) {
			if (mArchitectureSparseArray == null)
				mArchitectureSparseArray = new SparseArray<Architecture>();
			mArchitectureSparseArray.put(id, architecture);
		}

		public Architecture getArchitecture(int id) {
			return (mArchitectureSparseArray == null ? null : mArchitectureSparseArray.get(id));
		}

		public void removeArchitecture(int id) {
			if (mArchitectureSparseArray != null)
				mArchitectureSparseArray.remove(id);
		}
	}
}
