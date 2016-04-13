package org.telosys.tools.stats.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

import org.telosys.tools.stats.Configuration;
import org.telosys.tools.stats.ModelStats;

public class ModelStatsImpl implements ModelStats {

	private Configuration configuration;
	private String userId;
	private String modelName;
	private String projectName;

	public ModelStatsImpl(Configuration configuration, String userId, String projectName,String modelName) {
		this.configuration = configuration;
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
		Path file = configuration.getModelFile(userId, projectName, modelName).toPath();
		// File info
		BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);

		// lastModifiedTime : OK / Windows and Linux
		return new Date(attr.lastModifiedTime().toMillis());
	}

}
