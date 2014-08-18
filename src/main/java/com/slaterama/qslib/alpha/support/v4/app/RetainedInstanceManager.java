package com.slaterama.qslib.alpha.support.v4.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * A helper class to easily retain values and class instances across configuration changes
 * in Activities and Fragments.
 */
@TargetApi(4)
public class RetainedInstanceManager implements Map<String, Object> {

	/**
	 * String value used to find the retained fragment used to store retained values and
	 * class instances.
	 */
	public static final String RETAINED_FRAGMENT_TAG = RetainedInstanceManager.class.getName();

	/**
	 * A static map used to store pending retained fragments until they have been successfully
	 * added to the fragment manager.
	 */
	private static Map<FragmentManager, RetainedFragment> sPendingFragments
			= new WeakHashMap<FragmentManager, RetainedFragment>();

	/**
	 * Factory method used to create a RetainedInstanceManager using a {@link FragmentActivity}.
	 *
	 * @param activity The {@link FragmentActivity} used to create the RetainedInstanceManager.
	 * @return The newly-created RetainedInstanceManager.
	 */
	public static RetainedInstanceManager newInstance(FragmentActivity activity) {
		if (activity == null)
			throw new IllegalArgumentException("Activity can not be null");
		return new RetainedInstanceManager(activity.getSupportFragmentManager());
	}

	/**
	 * Factory method used to create a RetainedInstanceManager using a {@link Fragment}.
	 *
	 * @param fragment The {@link Fragment} used to create the RetainedInstanceManager.
	 * @return The newly-created RetainedInstanceManager.
	 */
	public static RetainedInstanceManager newInstance(Fragment fragment) {
		if (fragment == null)
			throw new IllegalArgumentException("Fragment can not be null");
		return new RetainedInstanceManager(fragment.getFragmentManager());
	}

	/**
	 * Factory method used to create a RetainedInstanceManager using a {@link FragmentManager}.
	 *
	 * @param fragmentManager The {@link FragmentManager} used to create the RetainedInstanceManager.
	 * @return The newly-created RetainedInstanceManager.
	 */
	public static RetainedInstanceManager newInstance(FragmentManager fragmentManager) {
		if (fragmentManager == null)
			throw new IllegalArgumentException("FragmentManager can not be null");
		return new RetainedInstanceManager(fragmentManager);
	}

	/**
	 * The fragment used to store retained values and class instances.
	 */
	private RetainedFragment mRetainedFragment;

	/**
	 * Constructor.
	 *
	 * @param fragmentManager The {@link FragmentManager} that will host the fragment used to
	 *                        store retained values and class instances.
	 */
	protected RetainedInstanceManager(FragmentManager fragmentManager) {
		if (fragmentManager == null)
			throw new IllegalArgumentException("FragmentManager can not be null");
		mRetainedFragment = (RetainedFragment) fragmentManager.findFragmentByTag(RETAINED_FRAGMENT_TAG);
		if (mRetainedFragment == null)
			mRetainedFragment = sPendingFragments.get(fragmentManager);
		if (mRetainedFragment == null) {
			mRetainedFragment = new RetainedFragment();
			sPendingFragments.put(fragmentManager, mRetainedFragment);
			fragmentManager.beginTransaction().add(mRetainedFragment, RETAINED_FRAGMENT_TAG).commit();
		}
	}

	/**
	 * Returns The fragment used to store retained values and class instances.
	 */
	protected RetainedFragment getRetainedFragment() {
		return mRetainedFragment;
	}

	/**
	 * Removes all retained instances from this {@code RetainedInstanceManager}, leaving it empty.
	 */
	@Override
	public void clear() {
		mRetainedFragment.mRetainedInstances.clear();
	}

	/**
	 * Returns whether this {@code RetainedInstanceManager} contains the specified key.
	 *
	 * @param key The key to search [for.
	 * @return {@code True} if this {@code RetainedInstanceManager} contains the specified key,
	 * {@code false} otherwise.
	 */
	@Override
	public boolean containsKey(Object key) {
		return mRetainedFragment.mRetainedInstances.containsKey(key);
	}

	/**
	 * Returns whether this {@code RetainedInstanceManager} contains the specified value.
	 *
	 * @param value The value to search for.
	 * @return {@code True} if this {@code RetainedInstanceManager} contains the specified value,
	 * {@code false} otherwise.
	 */
	@Override
	public boolean containsValue(Object value) {
		return mRetainedFragment.mRetainedInstances.containsValue(value);
	}

	/**
	 * Returns a {@code Set} containing all of the mappings in this {@code RetainedInstanceManager}.
	 *
	 * @return A set of the mappings.
	 */
	@NonNull
	@Override
	public Set<Entry<String, Object>> entrySet() {
		return mRetainedFragment.mRetainedInstances.entrySet();
	}

	/**
	 * Returns the value of the mapping with the specified key.
	 *
	 * @param key The key.
	 * @return The value of the mapping with the specified key, or {@code null} if no mapping
	 * for the specified key is found.
	 */
	@Override
	public Object get(Object key) {
		return mRetainedFragment.mRetainedInstances.get(key);
	}

	/**
	 * Returns whether this {@code RetainedInstanceManager} is empty.
	 *
	 * @return {@code True} if this {@code RetainedInstanceManager} has no elements,
	 * {@code false} otherwise.
	 */
	@Override
	public boolean isEmpty() {
		return mRetainedFragment.mRetainedInstances.isEmpty();
	}

	/**
	 * Returns a set of the keys contained in this {@code RetainedInstanceManager}.
	 * The {@code Set} is backed by this {@code RetainedInstanceManager} so changes to one
	 * are reflected by the other. The {@code Set} does not support adding.
	 *
	 * @return A set of the keys.
	 */
	@NonNull
	@Override
	public Set<String> keySet() {
		return mRetainedFragment.mRetainedInstances.keySet();
	}

	/**
	 * Maps the specified key to the specified value.
	 *
	 * @param key   The key.
	 * @param value The value.
	 * @return The value of any previous mapping with the specified key or {@code null} if there was no mapping.
	 */
	@Override
	public Object put(String key, Object value) {
		return mRetainedFragment.mRetainedInstances.put(key, value);
	}

	/**
	 * Copies every mapping in the specified {@code Map} to this {@code RetainedInstanceManager}.
	 *
	 * @param map the {@code Map} to copy mappings from.
	 */
	@Override
	public void putAll(Map<? extends String, ?> map) {
		mRetainedFragment.mRetainedInstances.putAll(map);
	}

	/**
	 * Removes a mapping with the specified key from this {@code RetainedInstanceManager}.
	 *
	 * @param key The key of the mapping to remove.
	 * @return The value of the removed mapping or {@code null} if no mapping for the specified key was found.
	 */
	@Override
	public Object remove(Object key) {
		return mRetainedFragment.mRetainedInstances.remove(key);
	}

	/**
	 * Returns the number of mappings in this {@code RetainedInstanceManager}.
	 *
	 * @return The number of mappings in this {@code RetainedInstanceManager}.
	 */
	@Override
	public int size() {
		return mRetainedFragment == null ? 0 : mRetainedFragment.mRetainedInstances.size();
	}

	/**
	 * Returns a {@code Collection} of the values contained in this {@code RetainedInstanceManager}.
	 * The {@code Collection} is backed by this {@code RetainedInstanceManager} so changes to one
	 * are reflected by the other. The {@code Collection} supports {@code remove(Object)},
	 * {@code removeAll(Collection)}, {@code retainAll(Collection)}, and {@code clear()} operations,
	 * and it does not support {@code add(E)} or {@code addAll(Collection)} operations.
	 * <p/>
	 * This method returns a {@code Collection} which is the subclass of {@code AbstractCollection.}
	 * The {@code iterator()} method of this subclass returns a "wrapper object" over the iterator
	 * of this {@code RetainedInstanceManager}'s {@code entrySet()}. The {@code size()} method wraps
	 * this {@code RetainedInstanceManager}'s {@code size()} method and the {@code contains(Object)}
	 * method wraps this {@code RetainedInstanceManager}'s {@code containsValue(Object)} method.
	 * <p/>
	 * The collection is created when this method is called at first time and returned in response
	 * to all subsequent calls. This method may return different {@code Collection} when multiple
	 * calls are made to this method, since it has no synchronization performed.
	 *
	 * @return A collection of the values contained in this {@code RetainedInstanceManager}.
	 */
	@NonNull
	@Override
	public Collection<Object> values() {
		return mRetainedFragment.mRetainedInstances.values();
	}

	/**
	 * A retained {@link Fragment} associated with this {@code RetainedInstanceManager}. This fragment
	 * is added to the {@link FragmentManager} established by this {@code RetainedInstanceManager} and
	 * is used to store retained values and class instances across configuration changes.
	 */
	public static class RetainedFragment extends Fragment {

		/**
		 * A mapping of retained system instances. These are special retained instances used by
		 * descendants of {@code RetainedInstanceManager}.
		 * @see {@link PatternManager}
		 */
		private Map<RetainedSystemInstanceKey, RetainedSystemInstanceValue> mRetainedSystemInstances;

		/**
		 * A mapping of retained instances.
		 */
		private Map<String, Object> mRetainedInstances;

		/**
		 * Constructor.
		 */
		public RetainedFragment() {
			super();
			mRetainedSystemInstances = new WeakHashMap<RetainedSystemInstanceKey, RetainedSystemInstanceValue>();
			mRetainedInstances = new HashMap<String, Object>();
		}

		/**
		 * Removes this fragment's {@link FragmentManager} from the pending fragments collection now that
		 * this fragment has been successfully attached to its {@code Activity}.
		 * @param activity
		 */
		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			sPendingFragments.remove(getFragmentManager());
		}

		/**
		 * Sets this {@code Fragment} as retained across Activity re-creation (such as from a
		 * configuration change).
		 */
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setRetainInstance(true);
		}

		/**
		 * Returns the mapping of retained system instances for descendants of the {@code RetainedInstanceManager}
		 * to register their {@code RetainedSystemInstanceValue}s.
		 * @return The mapping of retained system instances.
		 */
		protected Map<RetainedSystemInstanceKey, RetainedSystemInstanceValue> getRetainedSystemInstances() {
			return mRetainedSystemInstances;
		}
	}

	/**
	 * A special class used by descendants of the {@code RetainedInstanceManager} to register their
	 * {@code RetainedSystemInstanceValue}s.
	 */
	protected static class RetainedSystemInstanceKey {
	}

	/**
	 * An interface representing the information that descendants of the {@code RetainedInstanceManager}
	 * wish to retain across Activity re-creation (such as from a configuration change).
	 */
	protected static interface RetainedSystemInstanceValue {
	}
}
