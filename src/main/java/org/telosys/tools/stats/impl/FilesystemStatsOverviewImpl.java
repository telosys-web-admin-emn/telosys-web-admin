package org.telosys.tools.stats.impl;

import org.apache.commons.io.FileUtils;
import org.telosys.tools.helper.CSVReader;
import org.telosys.tools.stats.FilesystemStatsOverview;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

public class FilesystemStatsOverviewImpl implements FilesystemStatsOverview  {
	private Path root;
	private CSVReader users;


	public FilesystemStatsOverviewImpl(Path root) {
		this.root = root;
	}

	@Override
	public int getUsersCount() {
		return 0;
	}

	@Override
	public int getProjectsCount() {
		return 0;
	}

	@Override
	public int getModelsCount() {
		return 0;
	}

	@Override
	public long getDiskUsage() {
		return 0;
	}
}
