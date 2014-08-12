package com.slaterama.qslib.alpha.app;

import android.app.Fragment;

public class PatternFragment extends Fragment {

	public PatternManager getPatternManager() {
		return PatternManager.get(getFragmentManager());
	}
}
