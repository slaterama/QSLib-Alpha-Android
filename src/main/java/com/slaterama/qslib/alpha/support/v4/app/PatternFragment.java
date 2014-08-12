package com.slaterama.qslib.alpha.support.v4.app;

import android.support.v4.app.Fragment;

public class PatternFragment extends Fragment {

	public PatternManager getPatternManager() {
		return PatternManager.get(getFragmentManager());
	}
}
