package com.slaterama.qslib.alpha.app.pattern;

import android.app.Activity;
import android.app.Fragment;

import com.slaterama.qslib.utils.LogEx;

public class PatternManagerFragment extends PatternManager {

	/* package */ PatternManagerFragment(Fragment owner) {
		super(owner);
		LogEx.d(String.format("owner instanceof %s", ((Object) owner).getClass().getSimpleName()));
	}

	/* package */ PatternManagerFragment(Activity owner) {
		super(owner);
		LogEx.d(String.format("owner instanceof %s", ((Object) owner).getClass().getSimpleName()));
	}
}
