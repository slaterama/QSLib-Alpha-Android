package com.slaterama.qslib.alpha.app.architecture;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;

import java.util.WeakHashMap;

public abstract class AbsArchitectureManager {

	/**
	 * The base tag to be used for assigning a tag to fragment(s) created by the ArchitectureManager.
	 */
	protected static String FRAGMENT_TAG_BASE = AbsArchitectureManager.class.getName();

	protected Engine mEngine;

	protected AbsArchitectureManager() {
		mEngine = Engine.getInstance();
	}

	public void destroyArchitecture(Object object, int id) {
		mEngine.destroyArchitecture(object, id);
	}

	public void destroyArchitectures(Object object) {
		mEngine.destroyArchitectures(object);
	}

	/**
	 * Ensures an architecture is initialized and active. If the architecture doesn't already exist, one is created.
	 * Otherwise the last created architecture is re-used.
	 *
	 * @param context  The {@link android.content.Context} that will own this architecture.
	 * @param id       A unique identifier for this architecture. Can be whatever you want. Identifiers are scoped to a particular
	 *                 activity instance.
	 * @param args     Optional arguments to supply to the loader at construction. If an architecture already exists
	 *                 (a new one does not need to be created), this parameter will be ignored and the last arguments continue to be used.
	 * @param callback Interface the ArchitectureManager will call to create the architecture. Required.
	 * @return The last created architecture if one exists, or a newly-created architecture otherwise.
	 */
	public Architecture getArchitecture(Context context, int id, Bundle args, ArchitectureCallbacks callback) {
		if (context == null)
			throw new IllegalArgumentException("Context may not be null");
		else if (callback == null)
			throw new IllegalArgumentException("Callback may not be null");
		return mEngine.getArchitecture(context, id, args, callback);
	}

	public Architecture getArchitecture(Activity activity, int id, Bundle args, ArchitectureCallbacks callback) {
		throw new UnsupportedOperationException();
	}

	protected static class Engine {

		/**
		 * Gets the singleton instance of Engine.
		 *
		 * @return The singleton instance.
		 */
		public static Engine getInstance() {
			return LazyHolder.INSTANCE;
		}

		/**
		 * A lazily-instantiated instance holder.
		 */
		private static class LazyHolder {
			private static final Engine INSTANCE = new Engine();
		}

		/**
		 * A mapping of objects to sparse arrays of {@link com.slaterama.qslib.alpha.app.architecture.Architecture}s.
		 */
		protected WeakHashMap<Object, SparseArray<Architecture>> mArchitectureMap;

		private Engine() {
		}

		public void destroyArchitecture(Object object, int id) {
			if (mArchitectureMap == null)
				return;

			SparseArray<Architecture> array = mArchitectureMap.get(object);
			if (array != null)
				array.remove(id);
		}

		public void destroyArchitectures(Object object) {
			if (mArchitectureMap != null)
				mArchitectureMap.remove(object);
		}

		/**
		 * Ensures an architecture is initialized and active. If the architecture doesn't already exist, one is created.
		 * Otherwise the last created architecture is re-used.
		 *
		 * @param object   The Object that will own this architecture.
		 * @param id       A unique identifier for this architecture. Can be whatever you want. Identifiers are scoped to a particular
		 *                 activity instance.
		 * @param args     Optional arguments to supply to the loader at construction. If an architecture already exists
		 *                 (a new one does not need to be created), this parameter will be ignored and the last arguments continue to be used.
		 * @param callback Interface the ArchitectureManager will call to create the architecture. Required.
		 * @return The last created architecture if one exists, or a newly-created architecture otherwise.
		 */
		protected Architecture getArchitecture(Object object,
											   int id, Bundle args, ArchitectureCallbacks callback) {
			if (mArchitectureMap == null)
				mArchitectureMap = new WeakHashMap<Object, SparseArray<Architecture>>();
			SparseArray<Architecture> array = mArchitectureMap.get(object);
			if (array == null) {
				array = new SparseArray<Architecture>();
				mArchitectureMap.put(object, array);
			}
			Architecture architecture = array.get(id);
			if (architecture == null) {
				architecture = callback.onCreateArchitecture(id, args);
				array.put(id, architecture);
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
		 *
		 * @param id   The ID whose architecture is to be created.
		 * @param args Any arguments supplied by the caller.
		 * @return Return a new architecture that is ready to respond to events.
		 */
		public Architecture onCreateArchitecture(int id, Bundle args);
	}
}
