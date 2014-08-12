package com.slaterama.qslib.alpha.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class PatternActivity extends Activity {

	public PatternManager getPatternManager() {
		return PatternManager.get(getFragmentManager());
	}
}
