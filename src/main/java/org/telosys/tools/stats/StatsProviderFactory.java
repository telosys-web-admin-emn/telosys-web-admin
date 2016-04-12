package org.telosys.tools.stats;

import java.io.File;

import org.telosys.tools.stats.impl.StatsProviderImpl;

public class StatsProviderFactory {

	public static StatsProvider getStatsProvider() {
		return new StatsProviderImpl() ;
	}
}
