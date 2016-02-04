package org.telosys.tools.stats.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.rmi.UnexpectedException;
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

	private final static String MODEL_EXT = Configuration.getModelExtension();

	private Path dir;

	private String name;

	public ProjectStatsImpl(Path dir, String name) {
		this.dir = dir;
		this.name = name;
	}

	@Override
	public String getProjectName() {
		return this.name;
	}

	@Override
	public int getBundlesCount() {
		return this.getBundlesNames().size();
	}

	@Override
	public List<String> getBundlesNames() {
		File templatesDir = new File(this.getTemplatesDirPath());
		return asList(templatesDir.listFiles(File::isDirectory))
				.stream()
				.map(File::getName)
				.collect(toList());
	}

	@Override
	public int getModelsCount() {
		return this.getModelsNames().size();
	}

	@Override
	public List<String> getModelsNames() {
		File telosysDir = new File(this.getTelosysDirPath());
		return asList(telosysDir.listFiles(f -> f.getName().endsWith(MODEL_EXT)))
				.stream()
				.map(f -> f.getName().replace(MODEL_EXT, ""))
				.collect(toList());
	}

	@Override
	public long getDiskUsage() {
		try {
			return Files.size(dir);
		} catch(IOException e) {
			throw new IllegalStateException("The folder should exist !");
		}
	}

	@Override
	public Date getCreationDate() throws IOException {
		BasicFileAttributes attributes = Files.readAttributes(dir, BasicFileAttributes.class);
		return new Date(attributes.creationTime().toMillis());
	}

	@Override
	public Date getLastGenerationDate() throws IOException {
		BasicFileAttributes attributes = Files.readAttributes(dir, BasicFileAttributes.class);
		return new Date(attributes.lastModifiedTime().toMillis());
	}

	@Override
	public int getGenerationsCount() {
		// TODO how to find it ?
		return 0;
	}

	private String getTelosysDirPath() {
		return this.dir + File.separator + Configuration.getTelosysDir();
	}

	private String getTemplatesDirPath() {
		return this.getTelosysDirPath() + File.separator + "templates";
	}

}
