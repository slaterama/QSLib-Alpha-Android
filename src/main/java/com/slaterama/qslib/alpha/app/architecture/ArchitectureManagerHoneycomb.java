package com.slaterama.qslib.alpha.app.architecture;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ArchitectureManagerHoneycomb extends ArchitectureManager {

	public ArchitectureManagerHoneycomb() {}

	@Override
	public Architecture getArchitecture(Activity activity, int id, Bundle args, ArchitectureCallbacks callback) {
		return getArchitecture(activity.getFragmentManager(), id, args, callback);
	}

	@Override
	public Architecture getArchitecture(Fragment fragment, int id, Bundle args, ArchitectureCallbacks callback) {
		return getArchitecture(fragment.getFragmentManager(), id, args, callback);
	}

	private Architecture getArchitecture(FragmentManager fragmentManager,
										 int id, Bundle args, ArchitectureCallbacks callback) {
		String tag = String.format("%s.%d", FRAGMENT_TAG_BASE, id);
		RetainedFragment fragment = (RetainedFragment) fragmentManager.findFragmentByTag(tag);
		if (fragment == null) {
			fragment = new RetainedFragment();
			fragmentManager.beginTransaction().add(fragment, tag).commit();
			fragmentManager.executePendingTransactions();
		}
		return mEngine.getArchitecture(fragment, id, args, callback);
	}

	public static class RetainedFragment extends Fragment {
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setRetainInstance(true);
		}

		@Override
		public void onDestroy() {
			super.onDestroy();
			getInstance().destroyArchitectures(this);
		}
	}
}
