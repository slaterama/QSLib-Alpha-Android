package com.slaterama.qslib.alpha.support.v4.app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public class RetainedInstanceManager implements Map<String, Object> {

	public static final String TAG = RetainedInstanceManager.class.getName();

	protected static final String ILLEGAL_STATE_MESSAGE = "newInstance called from onCreate of a Fragment. Call from onActivityCreated instead";

	private RetainedFragment mRetainedFragment;

	public static RetainedInstanceManager newInstance(FragmentActivity activity) {
		if (activity == null)
			throw new IllegalArgumentException("Activity can not be null");
		if (checkSource())
			throw new IllegalStateException(ILLEGAL_STATE_MESSAGE);
		return new RetainedInstanceManager(activity.getSupportFragmentManager());
	}

	public static RetainedInstanceManager newInstance(Fragment fragment) {
		if (fragment == null)
			throw new IllegalArgumentException("Fragment can not be null");
		if (checkSource())
			throw new IllegalStateException(ILLEGAL_STATE_MESSAGE);
		return new RetainedInstanceManager(fragment.getFragmentManager());
	}

	public static RetainedInstanceManager newInstance(FragmentManager fragmentManager) {
		if (fragmentManager == null)
			throw new IllegalArgumentException("FragmentManager can not be null");
		if (checkSource())
			throw new IllegalStateException(ILLEGAL_STATE_MESSAGE);
		return new RetainedInstanceManager(fragmentManager);
	}

	protected static boolean checkSource() {
		try {
			Throwable th = new Throwable();
			StackTraceElement[] elements = th.getStackTrace();

			for (StackTraceElement element : elements) {
				Class<?> callingClass = Class.forName(element.getClassName());
				if (RetainedInstanceManager.class.isAssignableFrom(callingClass))
					continue;

				if (Fragment.class.isAssignableFrom(callingClass)) {



					if (TextUtils.equals(element.getMethodName(), "onCreate")) {
						return true;
					}
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	protected RetainedInstanceManager(FragmentManager fragmentManager) {
		if (fragmentManager == null)
			throw new IllegalArgumentException("FragmentManager can not be null");

		List<Fragment> fragments = fragmentManager.getFragments();

		mRetainedFragment = (RetainedFragment) fragmentManager.findFragmentByTag(TAG);
		if (mRetainedFragment == null) {
			mRetainedFragment = new RetainedFragment();
			fragmentManager.beginTransaction().add(mRetainedFragment, TAG).commit();
			//fragmentManager.executePendingTransactions();

			// NOTE A second manager created right after this won't be able to "find" retained fragment.
			// How to fix? Executing pending transactions causes other errors IF newInstance() called
			// from onCreate of a Fragment
		}
	}

	/*
	private boolean checkSource() {
		try {
			Throwable th = new Throwable();
			StackTraceElement[] elements = th.getStackTrace();

			for (StackTraceElement element : elements) {
				Class<?> callingClass = Class.forName(element.getClassName());
				if (RetainedInstanceManager.class.isAssignableFrom(callingClass))
					continue;

				if (Fragment.class.isAssignableFrom(callingClass)) {
					if (TextUtils.equals(element.getMethodName(), "onCreate")) {
						throw new IllegalStateException("newInstance() called from Fragment.onCreate. Call from onActivityCreated instead");
					}
				}
			}

			LogEx.d();

		} catch (IndexOutOfBoundsException e) {

		} catch (ClassNotFoundException e) {

		}
		return false;
	}
	*/

	protected RetainedFragment getRetainedFragment() {
		return mRetainedFragment;
	}

	@Override
	public void clear() {
		mRetainedFragment.getStringMap().clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return mRetainedFragment.getStringMap().containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return mRetainedFragment.getStringMap().containsValue(value);
	}

	@NonNull
	@Override
	public Set<Entry<String, Object>> entrySet() {
		return mRetainedFragment.getStringMap().entrySet();
	}

	@Override
	public Object get(Object key) {
		return mRetainedFragment.getStringMap().get(key);
	}

	@Override
	public boolean isEmpty() {
		return mRetainedFragment.getStringMap().isEmpty();
	}

	@NonNull
	@Override
	public Set<String> keySet() {
		return mRetainedFragment.getStringMap().keySet();
	}

	@Override
	public Object put(String key, Object value) {
		return mRetainedFragment.getStringMap().put(key, value);
	}

	@Override
	public void putAll(Map<? extends String, ?> map) {
		mRetainedFragment.getStringMap().putAll(map);
	}

	@Override
	public Object remove(Object key) {
		return mRetainedFragment.getStringMap().remove(key);
	}

	@Override
	public int size() {
		return mRetainedFragment.getStringMap().size();
	}

	@NonNull
	@Override
	public Collection<Object> values() {
		return mRetainedFragment.getStringMap().values();
	}

	public static class RetainedFragment extends Fragment {
		private Map<Object, Object> mObjectMap;
		private Map<String, Object> mStringMap;

		public RetainedFragment() {
			super();
			mObjectMap = new WeakHashMap<Object, Object>();
			mStringMap = new HashMap<String, Object>();
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setRetainInstance(true);
		}

		protected Map<Object, Object> getObjectMap() {
			return mObjectMap;
		}

		protected Map<String, Object> getStringMap() {
			return mStringMap;
		}
	}
}
