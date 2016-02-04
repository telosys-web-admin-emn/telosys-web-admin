package org.telosys.tools.stats.impl;

import java.io.File;
import java.util.Date;

import org.telosys.tools.stats.BundleStats;

public class BundleStatsImpl implements BundleStats {

	private String name;

	private String projectName;

	private File dir;

	@Override
	public String getProjectName() {
		return this.projectName;
	}

	@Override
	public String getBundleName() {
		return this.name;
	}

	@Override
	public Date getInstallationDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getGenerationsCount() {
		// TODO Auto-generated method stub
		return 0;
	}

}
