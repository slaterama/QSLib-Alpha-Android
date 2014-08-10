package com.slaterama.qslib.alpha.app.pattern;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.slaterama.qslib.utils.LogEx;

public class PatternManagerSupport extends PatternManager {

	/* package */ PatternManagerSupport(Fragment owner) {
		super(owner);
		LogEx.d(String.format("owner instanceof %s", ((Object) owner).getClass().getSimpleName()));
	}

	/* package */ PatternManagerSupport(FragmentActivity owner) {
		super(owner);
		LogEx.d(String.format("owner instanceof %s", ((Object) owner).getClass().getSimpleName()));
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
//			ArchitectureManager.getInstance().destroyArchitectures(this);
		}
	}}
