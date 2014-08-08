package com.slaterama.qslib.alpha.app.architecture;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseArray;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ArchitectureManagerHoneycomb extends ArchitectureManagerFroyo {

	public Architecture getArchitecture(Activity activity, int id, Bundle args, ArchitectureCallbacks callback) {
		if (activity == null)
			throw new IllegalArgumentException("Activity may not be null");

		return getArchitecture(activity.getFragmentManager(), id, args, callback);
	}

	public Architecture getArchitecture(Fragment fragment, int id, Bundle args, ArchitectureCallbacks callback) {
		if (fragment == null)
			throw new IllegalArgumentException("Fragment may not be null");

		if (fragment.getRetainInstance())
			return getArchitectureInternal(fragment, id, args, callback);

		return getArchitecture(fragment.getFragmentManager(), id, args, callback);
	}

	private Architecture getArchitecture(FragmentManager fragmentManager, int id, Bundle args, ArchitectureCallbacks callback) {
		String tag = String.format("%s.%d", FRAGMENT_TAG_BASE, id);
		ArchitectureHolderFragment fragment = (ArchitectureHolderFragment) fragmentManager.findFragmentByTag(tag);
		if (fragment == null) {
			fragment = new ArchitectureHolderFragment();
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

	public static class ArchitectureHolderFragment extends Fragment {

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
