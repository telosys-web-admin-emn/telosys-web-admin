package org.telosys.tools.stats.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.telosys.tools.helper.FileUnit;
import org.telosys.tools.stats.Configuration;
import org.telosys.tools.stats.ProjectStats;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

// TODO handle exceptions
public class ProjectStatsImpl implements ProjectStats {

	private final static String MODEL_EXT = ".model";

	private File dir;

	private String name;

	private String user;

	public ProjectStatsImpl(File dir, String name, String user) {
		this.dir = dir;
		this.name = name;
		this.user = user;
	}

	@Override
	public String getProjectName() {
		return this.name;
	}

	@Override
	public int getBundlesCount() {
		File templatesDir = new File(this.getTemplatesDirPath());
		int count = templatesDir.listFiles(File::isDirectory).length;
		return count;
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
		File telosysDir = new File(this.getTelosysDirPath());
		return telosysDir.listFiles(f -> f.getName().endsWith(MODEL_EXT)).length;
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
	public int getDiskUsage() {
		return (int) (this.dir.length() / FileUnit.MEGABYTE);
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

	private String getTelosysDirPath() {
		return this.dir.getAbsolutePath() + File.separator + Configuration.getTelosysDir();
	}

	private String getTemplatesDirPath() {
		return this.getTelosysDirPath() + File.separator + "templates";
	}

}
