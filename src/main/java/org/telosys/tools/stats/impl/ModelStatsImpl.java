package org.telosys.tools.stats.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

import org.telosys.tools.stats.ModelStats;

public class ModelStatsImpl implements ModelStats {

	private File root;
	private String userId;
	private String modelName;
	private String projectName;

	public ModelStatsImpl(File root, String userId, String modelName, String projectName)
	{
		this.root = root;
		this.userId = userId;
		this.modelName = modelName;
		this.projectName = projectName;
	}

	@Override
	public String getProjectName() {
		return this.projectName;
	}

	@Override
	public String getModelName() {
		return this.modelName;
	}

	@Override
	public Date getLastModifiedDate() throws IOException {
		String s = File.separator;
		Path file = Paths.get(this.root.getAbsolutePath()+s+this.userId+s+this.projectName+s+"TelosysTools", this.modelName+".model");
		BasicFileAttributes attr;
		// File info 
		attr = Files.readAttributes(file, BasicFileAttributes.class);

		// lastModifiedTime : OK / Windows and Linux
		return new Date(attr.lastModifiedTime().toMillis());
	}

}
