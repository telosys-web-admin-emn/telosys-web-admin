package org.telosys.tools.stats;

import java.io.File;

import org.telosys.tools.stats.impl.StatsProviderImpl;

public class StatsProviderFactory {

	public static final StatsProvider getStatsProvider() {
		
		// get root folder : to be implemented at the end
		File root = new File("/aaa/bbb/ccc");
		

		return new StatsProviderImpl(root) ;
	}
}
