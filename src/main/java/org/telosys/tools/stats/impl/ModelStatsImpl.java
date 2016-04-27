package org.telosys.tools.stats.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

import org.telosys.tools.stats.PathHelper;
import org.telosys.tools.stats.ModelStats;

public class ModelStatsImpl implements ModelStats {

	private PathHelper pathHelper;
	private String userId;
	private String modelName;
	private String projectName;

	public ModelStatsImpl(PathHelper pathHelper, String userId, String projectName, String modelName) {
		this.pathHelper = pathHelper;
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
	public Date getLastModifiedDate() {
		Path file = pathHelper.getModelFile(userId, projectName, modelName).toPath();

		try
		{
			// File info
			BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);

			// lastModifiedTime : OK / Windows and Linux
			return new Date(attr.lastModifiedTime().toMillis());
		}
		catch(IOException e)
		{
			return null;
		}
	}

	@Override
	public Date getCreationDate()
	{
		Path file = pathHelper.getModelFile(userId, projectName, modelName).toPath();

		try
		{
			// File info
			BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);

			// creationTime : OK / Windows and Linux
			return new Date(attr.creationTime().toMillis());
		}
		catch(IOException e)
		{
			return null;
		}
	}

	@Override
	public Date getCreationDate()
	{
		Path file = pathHelper.getModelFile(userId, projectName, modelName).toPath();

		try
		{
			// File info
			BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);

			// creationTime : OK / Windows and Linux
			return new Date(attr.creationTime().toMillis());
		}
		catch(IOException e)
		{
			return null;
		}
	}

}
