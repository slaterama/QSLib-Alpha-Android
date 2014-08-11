package com.slaterama.qslib.alpha.app.pattern;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.slaterama.qslib.utils.LogEx;

@TargetApi(Build.VERSION_CODES.DONUT)
public class PatternManagerSupport extends PatternManager {

	protected FragmentManager mFragmentManager;

	/* package */ PatternManagerSupport(Fragment owner) {
		super(owner);
		mFragmentManager = owner.getFragmentManager();
		LogEx.d(String.format("owner instanceof %s", ((Object) owner).getClass().getName()));
	}

	/* package */ PatternManagerSupport(FragmentActivity owner) {
		super(owner);
		mFragmentManager = owner.getSupportFragmentManager();
		LogEx.d(String.format("owner instanceof %s", ((Object) owner).getClass().getName()));
	}

	@Override
	public Pattern initPattern(int id, Bundle args, PatternCallbacks callback) {
		return PatternMap.getInstance().initPattern(
				getPatternFragment(mFragmentManager), id, args, callback);
	}

	@Override
	public Pattern getPattern(int id) {
		return PatternMap.getInstance().getPattern(getPatternFragment(mFragmentManager), id);
	}

	private PatternFragment getPatternFragment(FragmentManager fragmentManager) {
		PatternFragment fragment = (PatternFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
		if (fragment == null) {
			fragment = new PatternFragment();
			fragmentManager.beginTransaction().add(fragment, FRAGMENT_TAG).commit();
			fragmentManager.executePendingTransactions();
		}
		return fragment;
	}

	public static class PatternFragment extends Fragment {
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setRetainInstance(true);
		}

		@Override
		public void onDestroy() {
			super.onDestroy();
			PatternMap.getInstance().remove(this);
		}
	}
}