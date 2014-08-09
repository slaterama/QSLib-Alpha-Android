package com.slaterama.qslib.alpha.support.v4.app.archnew;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.slaterama.qslib.alpha.app.archnew.ArchitectureManager;
import com.slaterama.qslib.utils.LogEx;

/**
 * <p>A class that manages architecture patterns (i.e. MVC, MVP, MVVM etc.)
 * for use in decoupling the view from the model.</p>
 * <p>This class implements the "Initialization-on-demand holder idiom" as described here:
 * http://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom</p>
 */
@TargetApi(Build.VERSION_CODES.DONUT)
public class SupportArchitectureManager extends ArchitectureManager {

	/**
	 * Gets the appropriate singleton instance of ArchitectureManager, based on the
	 * current Android build.
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

	/**
	 * The base tag to be used for assigning a tag to fragment(s) created by the ArchitectureManager.
	 */
	protected static String FRAGMENT_TAG_BASE = SupportArchitectureManager.class.getName();

	/**
	 * Private constructor.
	 */
	private SupportArchitectureManager() {}

	/**
	 * Ensures an architecture is initialized and active. If the architecture doesn't already exist, one is created.
	 * Otherwise the last created architecture is re-used.
	 * @param activity The {@link android.support.v4.app.FragmentActivity} that will own this architecture.
	 * @param id A unique identifier for this architecture. Can be whatever you want. Identifiers are scoped to a particular
	 *           activity instance.
	 * @param args Optional arguments to supply to the loader at construction. If an architecture already exists
	 *             (a new one does not need to be created), this parameter will be ignored and the last arguments continue to be used.
	 * @param callback Interface the ArchitectureManager will call to create the architecture. Required.
	 * @return The last created architecture if one exists, or a newly-created architecture otherwise.
	 */
	public Architecture getArchitecture(FragmentActivity activity, int id, Bundle args, ArchitectureCallbacks callback) {
		if (activity == null)
			throw new IllegalArgumentException("Activity may not be null");
		else if (callback == null)
			throw new IllegalArgumentException("Callback may not be null");
		return getArchitecture(activity.getSupportFragmentManager(), id, args, callback);
	}

	/**
	 * Ensures an architecture is initialized and active. If the architecture doesn't already exist, one is created.
	 * Otherwise the last created architecture is re-used.
	 * @param fragment The {@link Fragment} that will own this architecture.
	 * @param id A unique identifier for this architecture. Can be whatever you want. Identifiers are scoped to a particular
	 *           activity instance.
	 * @param args Optional arguments to supply to the loader at construction. If an architecture already exists
	 *             (a new one does not need to be created), this parameter will be ignored and the last arguments continue to be used.
	 * @param callback Interface the ArchitectureManager will call to create the architecture. Required.
	 * @return The last created architecture if one exists, or a newly-created architecture otherwise.
	 */
	public Architecture getArchitecture(Fragment fragment, int id, Bundle args, ArchitectureCallbacks callback) {
		if (fragment == null)
			throw new IllegalArgumentException("Fragment may not be null");
		else if (callback == null)
			throw new IllegalArgumentException("Callback may not be null");
		if (fragment.getRetainInstance())
			return getArchitectureInternal(fragment, id, args, callback);
		return getArchitecture(fragment.getFragmentManager(), id, args, callback);
	}

	private Architecture getArchitecture(FragmentManager fragmentManager,
												 int id, Bundle args, ArchitectureCallbacks callback) {
		String tag = String.format("%s.%d", FRAGMENT_TAG_BASE, id);
		ArchitectureHolderFragment fragment = (ArchitectureHolderFragment) fragmentManager.findFragmentByTag(tag);
		if (fragment == null) {
			LogEx.d("Creating fragment...");
			fragment = new ArchitectureHolderFragment();
			fragmentManager.beginTransaction().add(fragment, tag).commit();
			fragmentManager.executePendingTransactions();
		} else {
			LogEx.d("Fragment already created.");
		}
		return getArchitectureInternal(fragment, id, args, callback);
	}

	public static class ArchitectureHolderFragment extends Fragment {
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setRetainInstance(true);
		}

		@Override
		public void onDestroy() {
			super.onDestroy();
			SupportArchitectureManager.getInstance().destroyArchitectures(this);
			LogEx.d();
		}
	}
}
