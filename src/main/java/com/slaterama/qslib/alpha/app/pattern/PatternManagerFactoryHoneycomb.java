package com.slaterama.qslib.alpha.app.pattern;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.support.v4.app.FragmentActivity;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class PatternManagerFactoryHoneycomb extends PatternManagerFactoryDonut {

	/* package */ PatternManagerFactoryHoneycomb() {
		super();
	}

	@Override
	/* package */ PatternManager newPatternManager(Object owner) {
		if (owner instanceof Fragment)
			return new PatternManagerFragment((Fragment) owner);
		else if (owner instanceof FragmentActivity)
			return new PatternManagerSupport((FragmentActivity) owner);
		else if (owner instanceof Activity)
			return new PatternManagerFragment((Activity) owner);
		else
			return super.newPatternManager(owner);
	}
}
