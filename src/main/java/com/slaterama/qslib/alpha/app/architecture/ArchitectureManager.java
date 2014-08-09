package com.slaterama.qslib.alpha.app.architecture;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;

public abstract class ArchitectureManager extends AbsArchitectureManager {

	/**
	 * Gets the singleton instance of ArchitectureManager, based on Android release.
	 * @return The singleton instance.
	 */
	public static ArchitectureManager getInstance() {
		return LazyHolder.INSTANCE;
	}

	/**
	 * A lazily-instantiated instance holder.
	 */
	private static class LazyHolder {
		private static final ArchitectureManager INSTANCE = newInstance();

		private static final ArchitectureManager newInstance() {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				return new ArchitectureManagerHoneycomb();
			} else {
				return new ArchitectureManagerBase();
			}
		}
	}

	protected ArchitectureManager() {}

	public Architecture getArchitecture(Fragment fragment, int id, Bundle args, ArchitectureCallbacks callback) {
		throw new UnsupportedOperationException();
	}
}
