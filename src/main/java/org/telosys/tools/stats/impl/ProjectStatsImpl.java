package org.telosys.tools.stats.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.telosys.tools.helper.FileUnit;
import org.telosys.tools.stats.Configuration;
import org.telosys.tools.stats.ProjectStats;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

// TODO handle exceptions
public class ProjectStatsImpl implements ProjectStats {

	private Configuration configuration;
	private String name;
	private String user;
	private File dir;

	public ProjectStatsImpl(Configuration configuration, String name, String user) {
		this.name = name;
		this.user = user;
		this.configuration = configuration;
		this.dir = configuration.getProjectDir(user, name);
	}

	@Override
	public String getProjectName() {
		return this.name;
	}

	@Override
	public int getBundlesCount() {
		File templatesDir = configuration.getBundlesDir(user, name);
		return templatesDir.listFiles(File::isDirectory).length;
	}

	@Override
	public List<String> getBundlesNames() {
		File templatesDir = configuration.getBundlesDir(user, name);
		return Arrays.stream(templatesDir.listFiles(File::isDirectory))
				.map(File::getName)
				.collect(toList());
	}

	@Override
	public int getModelsCount() {
		File telosysDir = configuration.getTelosysDir(user, name);
		return telosysDir.listFiles(f -> f.getName().endsWith(Configuration.MODEL_EXTENSION)).length;
	}

	@Override
	public List<String> getModelsNames() {
		File telosysDir = configuration.getTelosysDir(user, name);
		return Arrays.stream(telosysDir.listFiles(f -> f.getName().endsWith(Configuration.MODEL_EXTENSION)))
				.map(f -> f.getName().replace(Configuration.MODEL_EXTENSION, ""))
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
