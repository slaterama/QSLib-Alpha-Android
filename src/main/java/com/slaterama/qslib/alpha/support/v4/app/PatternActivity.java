package com.slaterama.qslib.alpha.support.v4.app;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.app.FragmentActivity;

@TargetApi(Build.VERSION_CODES.DONUT)
public class PatternActivity extends FragmentActivity {

	public PatternManager getPatternManager() {
		return PatternManager.get(getSupportFragmentManager());
	}
}
