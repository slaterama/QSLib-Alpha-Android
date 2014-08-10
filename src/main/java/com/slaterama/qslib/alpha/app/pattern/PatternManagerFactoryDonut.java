package com.slaterama.qslib.alpha.app.pattern;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

@TargetApi(Build.VERSION_CODES.DONUT)
public class PatternManagerFactoryDonut extends PatternManagerFactory {

	/* package */ PatternManagerFactoryDonut() {
		super();
		mDetailMessage = "Owner must be an instance of Context or Fragment";
	}

	@Override
	/* package */ PatternManager newPatternManager(Object owner) {
		if (owner instanceof Fragment)
			return new PatternManagerSupport((Fragment) owner);
		else if (owner instanceof FragmentActivity)
			return new PatternManagerSupport((FragmentActivity) owner);
		else
			return super.newPatternManager(owner);
	}
}
