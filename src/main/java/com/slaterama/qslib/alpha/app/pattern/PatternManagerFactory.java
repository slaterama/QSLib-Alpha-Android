package com.slaterama.qslib.alpha.app.pattern;

import android.content.Context;
import android.os.Build;

public class PatternManagerFactory {

	/* package */ static PatternManagerFactory newInstance() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			return new PatternManagerFactoryHoneycomb();
		else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT)
			return new PatternManagerFactoryDonut();
		else
			return new PatternManagerFactory();
	}

	/* package */ String mDetailMessage = "Owner must be an instance of Context";

	/* package */ PatternManager newPatternManager(Object owner) {
		if (owner instanceof Context)
			return new PatternManagerBase((Context) owner);

		throw new IllegalArgumentException(mDetailMessage);
	}

	/* package */ PatternManagerFactory() {}
}
