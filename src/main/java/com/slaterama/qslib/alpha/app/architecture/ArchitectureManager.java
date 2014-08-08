package com.slaterama.qslib.alpha.app.architecture;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.slaterama.qslib.utils.LogEx;

/**
 * <p>A class that manages architecture patterns (i.e. MVC, MVP, MVVM etc.)
 * for use in decoupling the view from the model.</p>
 * <p>This class implements the "Initialization-on-demand holder idiom" as described here:
 * http://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom</p>
 */
public abstract class ArchitectureManager {

	/**
	 * A lazily-instantiated instance holder.
	 */
	private static class LazyHolder {
		private static ArchitectureManager newInstance() {
			ArchitectureManager architectureManager = null;
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				architectureManager = new ArchitectureManagerHoneycomb();
			} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
				architectureManager = new ArchitectureManagerFroyo();
			} else {
				architectureManager = new ArchitectureManagerBase();
			}
			LogEx.d(String.format("Created %s...", architectureManager.getClass().getSimpleName()));
			return architectureManager;
		}

		private static final ArchitectureManager INSTANCE = newInstance();
	}

	/**
	 * Gets the appropriate singleton instance of ArchitectureManager, based on the
	 * current Android build.
	 * @return The singleton instance.
	 */
	public static ArchitectureManager getInstance() {
		return LazyHolder.INSTANCE;
	}

	/**
	 * Ensures an architecture is initialized and active. If the architecture doesn't already exist, one is created.
	 * Otherwise the last created architecture is re-used.
	 * @param activity The {@link Activity} that will own this architecture.
	 * @param id A unique identifier for this architecture. Can be whatever you want. Identifiers are scoped to a particular
	 *           activity instance.
	 * @param args Optional arguments to supply to the loader at construction. If an architecture already exists
	 *             (a new one does not need to be created), this parameter will be ignored and the last arguments continue to be used.
	 * @param callback Interface the ArchitectureManager will call to create the architecture. Required.
	 * @return The last created architecture if one exists, or a newly-created architecture otherwise.
	 */
	public Architecture getArchitecture(Activity activity, int id, Bundle args, ArchitectureCallbacks callback) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Ensures an architecture is initialized and active. If the architecture doesn't already exist, one is created.
	 * Otherwise the last created architecture is re-used.
	 * @param activity The {@link FragmentActivity} that will own this architecture.
	 * @param id A unique identifier for this architecture. Can be whatever you want. Identifiers are scoped to a particular
	 *           activity instance.
	 * @param args Optional arguments to supply to the loader at construction. If an architecture already exists
	 *             (a new one does not need to be created), this parameter will be ignored and the last arguments continue to be used.
	 * @param callback Interface the ArchitectureManager will call to create the architecture. Required.
	 * @return The last created architecture if one exists, or a newly-created architecture otherwise.
	 */
	public Architecture getArchitecture(FragmentActivity activity, int id, Bundle args, ArchitectureCallbacks callback) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Ensures an architecture is initialized and active. If the architecture doesn't already exist, one is created.
	 * Otherwise the last created architecture is re-used.
	 * @param context The {@link Context} that will own this architecture.
	 * @param id A unique identifier for this architecture. Can be whatever you want. Identifiers are scoped to a particular
	 *           activity instance.
	 * @param args Optional arguments to supply to the loader at construction. If an architecture already exists
	 *             (a new one does not need to be created), this parameter will be ignored and the last arguments continue to be used.
	 * @param callback Interface the ArchitectureManager will call to create the architecture. Required.
	 * @return The last created architecture if one exists, or a newly-created architecture otherwise.
	 */
	public Architecture getArchitecture(Context context, int id, Bundle args, ArchitectureCallbacks callback) {
		throw new UnsupportedOperationException();
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
		throw new UnsupportedOperationException();
	}

	/**
	 * Ensures an architecture is initialized and active. If the architecture doesn't already exist, one is created.
	 * Otherwise the last created architecture is re-used.
	 * @param fragment The {@link android.app.Fragment} that will own this architecture.
	 * @param id A unique identifier for this architecture. Can be whatever you want. Identifiers are scoped to a particular
	 *           activity instance.
	 * @param args Optional arguments to supply to the loader at construction. If an architecture already exists
	 *             (a new one does not need to be created), this parameter will be ignored and the last arguments continue to be used.
	 * @param callback Interface the ArchitectureManager will call to create the architecture. Required.
	 * @return The last created architecture if one exists, or a newly-created architecture otherwise.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public Architecture getArchitecture(android.app.Fragment fragment, int id, Bundle args, ArchitectureCallbacks callback) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Callback interface for a client to interact with the manager.
	 */
	public static interface ArchitectureCallbacks {

		/**
		 * Instantiate and return a new Loader for the given ID.
		 * @param id The ID whose architecture is to be created.
		 * @param args Any arguments supplied by the caller.
		 * @return Return a new architecture that is ready to respond to events.
		 */
		public Architecture onCreateArchitecture(int id, Bundle args);
	}
}
