package com.slaterama.qslib.alpha.app.archnew;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseArray;

import com.slaterama.qslib.alpha.support.v4.app.archnew.Architecture;

import java.util.WeakHashMap;

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
			} else {
				architectureManager = new ArchitectureManagerBase();
			}
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
	 * A mapping of objects to sparse arrays of {@link Architecture}s.
	 */
	protected WeakHashMap<Object, ArchitectureHolder> mArchitectureMap;

	public void destroyArchitecture(Object object, int id) {
		if (mArchitectureMap == null)
			return;

		ArchitectureHolder holder = mArchitectureMap.get(object);
		if (holder == null)
			return;

		holder.removeArchitecture(id);
	}

	public void destroyArchitectures(Object object) {
		if (mArchitectureMap != null)
			mArchitectureMap.remove(object);
	}

	/**
	 * Ensures an architecture is initialized and active. If the architecture doesn't already exist, one is created.
	 * Otherwise the last created architecture is re-used.
	 * @param context The {@link android.content.Context} that will own this architecture.
	 * @param id A unique identifier for this architecture. Can be whatever you want. Identifiers are scoped to a particular
	 *           activity instance.
	 * @param args Optional arguments to supply to the loader at construction. If an architecture already exists
	 *             (a new one does not need to be created), this parameter will be ignored and the last arguments continue to be used.
	 * @param callback Interface the ArchitectureManager will call to create the architecture. Required.
	 * @return The last created architecture if one exists, or a newly-created architecture otherwise.
	 */
	public Architecture getArchitecture(Context context, int id, Bundle args, ArchitectureCallbacks callback) {
		if (context == null)
			throw new IllegalArgumentException("Context may not be null");
		else if (callback == null)
			throw new IllegalArgumentException("Callback may not be null");
		return getArchitectureInternal(context, id, args, callback);
	}

	/**
	 * Ensures an architecture is initialized and active. If the architecture doesn't already exist, one is created.
	 * Otherwise the last created architecture is re-used.
	 * @param object The Object that will own this architecture.
	 * @param id A unique identifier for this architecture. Can be whatever you want. Identifiers are scoped to a particular
	 *           activity instance.
	 * @param args Optional arguments to supply to the loader at construction. If an architecture already exists
	 *             (a new one does not need to be created), this parameter will be ignored and the last arguments continue to be used.
	 * @param callback Interface the ArchitectureManager will call to create the architecture. Required.
	 * @return The last created architecture if one exists, or a newly-created architecture otherwise.
	 */
	protected Architecture getArchitectureInternal(Object object,
												 int id, Bundle args, ArchitectureCallbacks callback) {
		if (mArchitectureMap == null)
			mArchitectureMap = new WeakHashMap<Object, ArchitectureHolder>();

		ArchitectureHolder architectureHolder = mArchitectureMap.get(object);
		if (architectureHolder == null) {
			architectureHolder = new ArchitectureHolder();
			mArchitectureMap.put(object, architectureHolder);
		}
		return architectureHolder.ensureArchitecture(id, args, callback);
	}


	public static class ArchitectureHolder {
		private SparseArray<Architecture> mArchitectureSparseArray;

		public void putArchitecture(int id, Architecture architecture) {
			if (mArchitectureSparseArray == null)
				mArchitectureSparseArray = new SparseArray<Architecture>();
			mArchitectureSparseArray.put(id, architecture);
		}

		public Architecture getArchitecture(int id) {
			return (mArchitectureSparseArray == null
					? null : mArchitectureSparseArray.get(id));
		}

		public void removeArchitecture(int id) {
			if (mArchitectureSparseArray != null)
				mArchitectureSparseArray.remove(id);
		}

		public Architecture ensureArchitecture(int id, Bundle args, ArchitectureCallbacks callback) {
			Architecture architecture = getArchitecture(id);
			if (architecture == null) {
				architecture = callback.onCreateArchitecture(id, args);
				putArchitecture(id, architecture);
			}
			return architecture;
		}
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
