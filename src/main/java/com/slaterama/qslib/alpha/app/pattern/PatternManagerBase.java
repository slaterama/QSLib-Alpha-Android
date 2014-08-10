package com.slaterama.qslib.alpha.app.pattern;

import android.content.Context;

import com.slaterama.qslib.utils.LogEx;

public class PatternManagerBase extends PatternManager {

	PatternManagerBase(Context owner) {
		super(owner);
		LogEx.d(String.format("owner instanceof %s", ((Object) owner).getClass().getSimpleName()));
	}
}
