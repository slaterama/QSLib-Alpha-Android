package com.slaterama.qslib.alpha.app.architecture;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.SparseArray;

import java.util.WeakHashMap;

public class ArchitectureManagerBase extends ArchitectureManager {

	protected WeakHashMap<Object, SparseArray<Architecture>> mArchitectureMap;

	/*
	public Architecture getArchitecture(Activity activity, int id, Bundle args, ArchitectureCallbacks callback) {
		if (activity == null)
			throw new IllegalArgumentException("Activity may not be null");

		// TODO What to do in this case?

		return null;
	}
	*/

	/**
	 * {@inheritDoc}
	 */
	public Architecture getArchitecture(Context context, int id, Bundle args, ArchitectureCallbacks callback) {
		if (context == null)
			throw new IllegalArgumentException("Context may not be null");

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
	protected Architecture getArchitectureInternal(Object object, int id, Bundle args, ArchitectureCallbacks callback) {
		if (mArchitectureMap == null)
			mArchitectureMap = new WeakHashMap<Object, SparseArray<Architecture>>();

		SparseArray<Architecture> architectureSparseArray = mArchitectureMap.get(object);
		if (architectureSparseArray == null) {
			architectureSparseArray = new SparseArray<Architecture>();
			mArchitectureMap.put(object, architectureSparseArray);
		}

		Architecture architecture = architectureSparseArray.get(id);
		if (architecture == null && callback != null) {
			architecture = callback.onCreateArchitecture(id, args);
			architectureSparseArray.put(id, architecture);
		}

		return architecture;
	}
}
