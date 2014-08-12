package com.slaterama.qslib.alpha.support.v7.app;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;

import com.slaterama.qslib.alpha.support.v4.app.PatternManager;

@TargetApi(Build.VERSION_CODES.ECLAIR_MR1)
public class PatternActionBarActivity extends ActionBarActivity {

	public PatternManager getPatternManager() {
		return PatternManager.get(getSupportFragmentManager());
	}
}
