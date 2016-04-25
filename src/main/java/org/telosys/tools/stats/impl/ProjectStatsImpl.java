package org.telosys.tools.stats.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.telosys.tools.stats.PathHelper;
import org.telosys.tools.stats.ProjectStats;

import static java.util.stream.Collectors.toList;

// TODO handle exceptions
public class ProjectStatsImpl implements ProjectStats {

	private PathHelper pathHelper;
	private String name;
	private String user;
	private File dir;

	public ProjectStatsImpl(PathHelper pathHelper, String name, String user) {
		this.name = name;
		this.user = user;
		this.pathHelper = pathHelper;
		this.dir = pathHelper.getProjectDir(user, name);
	}

	@Override
	public String getProjectName() {
		return this.name;
	}

	@Override
	public int getBundlesCount() {
		File templatesDir = pathHelper.getBundlesDir(user, name);
		return templatesDir.listFiles(File::isDirectory).length;
	}

	@Override
	public List<String> getBundlesNames() {
		File templatesDir = pathHelper.getBundlesDir(user, name);
		return Arrays.stream(templatesDir.listFiles(File::isDirectory))
				.map(File::getName)
				.collect(toList());
	}

	@Override
	public int getModelsCount() {
		File telosysDir = pathHelper.getTelosysDir(user, name);
		return telosysDir.listFiles(f -> f.getName().endsWith(pathHelper.getModelExtension())).length;
	}

	@Override
	public List<String> getModelsNames() {
		File telosysDir = pathHelper.getTelosysDir(user, name);
		return Arrays.stream(telosysDir.listFiles(f -> f.getName().endsWith(pathHelper.getModelExtension())))
				.map(f -> f.getName().replace(pathHelper.getModelExtension(), ""))
				.collect(toList());
	}

	@Override
	public long getDiskUsage() {
		return FileUtils.sizeOfDirectory(dir);
	}

	@Override
	public Date getCreationDate() throws IOException {
		BasicFileAttributes attributes = Files.readAttributes(Paths.get(dir.getAbsolutePath()), BasicFileAttributes.class);
		return new Date(attributes.creationTime().toMillis());
	}

	@Override
	public Date getLastGenerationDate() throws IOException {
		BasicFileAttributes attributes = Files.readAttributes(Paths.get(dir.getAbsolutePath()), BasicFileAttributes.class);
		return new Date(attributes.lastModifiedTime().toMillis());
	}

	@Override
	public int getGenerationsCount() {
		// TODO Auto-generated method stub
		return 0;
	}


}
