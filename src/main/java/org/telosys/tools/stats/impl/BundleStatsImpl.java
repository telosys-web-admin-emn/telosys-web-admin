package org.telosys.tools.stats.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

import org.telosys.tools.stats.BundleStats;

public class BundleStatsImpl implements BundleStats {

	private File root;
	private String userName;
	private String bundleName;
	private String projectName;

	public BundleStatsImpl(File root, String userName, String bundleName, String projectName) {
		this.root = root;
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
	public Date getInstallationDate() {
		Path bundle_path = this.root.toPath().resolve(this.userName).resolve(this.projectName);
		bundle_path.resolve("TelosysTools/templates").resolve(this.bundleName);
		BasicFileAttributes attr = null;
		try
		{
			attr = Files.readAttributes(bundle_path, BasicFileAttributes.class);
			return new Date(attr.creationTime().toMillis());
		} catch(IOException e)
		{
			return null;
		}
	}

	@Override
	public int getGenerationsCount() {
		// TODO Auto-generated method stub
		return 0;
	}

}
