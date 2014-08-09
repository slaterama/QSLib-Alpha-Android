package com.slaterama.qslib.alpha.support.v4.app.architecture;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.slaterama.qslib.alpha.app.architecture.AbsArchitectureManager;
import com.slaterama.qslib.alpha.app.architecture.Architecture;
import com.slaterama.qslib.alpha.app.architecture.ArchitectureManager;

@TargetApi(Build.VERSION_CODES.DONUT)
public class SupportArchitectureManager extends AbsArchitectureManager {

	/**
	 * Gets the singleton instance of Engine.
	 * @return The singleton instance.
	 */
	public static SupportArchitectureManager getInstance() {
		return LazyHolder.INSTANCE;
	}

	/**
	 * A lazily-instantiated instance holder.
	 */
	private static class LazyHolder {
		private static final SupportArchitectureManager INSTANCE = new SupportArchitectureManager();
	}

	private SupportArchitectureManager() {}

	@Override
	public Architecture getArchitecture(Activity activity, int id, Bundle args, ArchitectureCallbacks callback) {
		throw new UnsupportedOperationException("SupportArchitectureManager supports FragmentActivity only; use ArchitectureManager instead");
	}

	public Architecture getArchitecture(FragmentActivity activity, int id, Bundle args, ArchitectureCallbacks callback) {
		return getArchitecture(activity.getSupportFragmentManager(), id, args, callback);
	}

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
			ArchitectureManager.getInstance().destroyArchitectures(this);
		}
	}
}
