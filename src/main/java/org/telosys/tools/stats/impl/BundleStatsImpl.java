package org.telosys.tools.stats.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

import org.telosys.tools.stats.BundleStats;
import org.telosys.tools.stats.PathHelper;

public class BundleStatsImpl implements BundleStats {

	private String userName;
	private String bundleName;
	private String projectName;
	private PathHelper pathHelper;

	public BundleStatsImpl(PathHelper pathHelper, String userName, String bundleName, String projectName) {
		this.pathHelper = pathHelper;
		this.userName = userName;
		this.bundleName = bundleName;
		this.projectName = projectName;
	}

	@Override
	public String getProjectName() {
		return this.projectName;
	}

	@Override
	public String getBundleName() {
		return this.bundleName;
	}

	@Override
	public Date getInstallationDate() throws IOException {
		Path bundle = pathHelper.getTemplatesDir(userName, projectName, bundleName).toPath();
		BasicFileAttributes attr = Files.readAttributes(bundle, BasicFileAttributes.class);
		return new Date(attr.creationTime().toMillis());
	}

	@Override
	public int getGenerationsCount() {
		// TODO Auto-generated method stub
		return 0;
	}

}
