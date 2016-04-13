package org.telosys.tools.stats.impl;

import org.telosys.tools.stats.*;
import org.telosys.tools.stats.exception.ProjectNotFoundException;
import org.telosys.tools.users.User;
import org.telosys.tools.users.UsersFileName;
import org.telosys.tools.users.UsersManager;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class StatsProviderImpl implements StatsProvider {


	private Configuration configuration;

	public StatsProviderImpl() {
		super();
		this.configuration = Configuration.getInstance();
	}

	@Override
	public File getRoot() {
		return configuration.getRoot();
	}

	@Override
	public FilesystemStatsOverview getFilesystemStatsOverview() {
		return new FilesystemStatsOverviewImpl(configuration);
	}

	@Override
	public UserStats getUserStats(String userId) {
		try {
			UsersFileName.setSpecificFileName(configuration.getCsvFile().getPath());
			UsersManager users = UsersManager.getInstance();
			User myUser = users.getUserByLogin(userId);
			return new UsersStatsImpl(configuration, myUser);
		} catch(ParseException e) {
			return null;
		} catch(IOException e) {
			return null;
		}
	}

	@Override
	public ProjectStats getProjectStats(String userId, String projectName) throws ProjectNotFoundException {
		File projectDir = configuration.getProjectDir(userId, projectName);
		if(!projectDir.exists()) {
			throw new ProjectNotFoundException(projectName);
		}
		return new ProjectStatsImpl(configuration, projectName, userId);
	}

	@Override
	public ModelStats getModelStats(String userId, String projectName, String modelName) {
		// TODO
		return null;
	}

	@Override
	public BundleStats getBundleStats(String userId, String projectName, String bundleName) {
		return new BundleStatsImpl(configuration.getRoot(), userId, bundleName, projectName);
	}


	@Override
	public List<ProjectStats> getProjectsStats(String userId) {
		try
		{
			UsersFileName.setSpecificFileName(configuration.getCsvFile().getPath());
			UsersManager users = UsersManager.getInstance();
			User myUser = users.getUserByLogin(userId);
			UserStats userStats = new UsersStatsImpl(configuration, myUser);
			List<String> projects = userStats.getProjectsNames();
			LinkedList<ProjectStats> projectsStats = new LinkedList<>();
			for(String s: projects)
			{
				ProjectStats pStats = new ProjectStatsImpl(configuration, s, userId);
				projectsStats.add(pStats);
			}
			return projectsStats;
		} catch(IOException e) {
			//TODO log exception.
			return null;
		} catch(ParseException e) {
			return null;
		}
	}

	@Override
	public List<ModelStats> getModelsStats(String userId) {

		return null;
	}

	@Override
	public List<BundleStats> getBundlesStats(String userId) {
		List<BundleStats> bundles = new ArrayList<>();
		File userDir = configuration.getUserDir(userId);
		File[] projectDirs = userDir.listFiles();
		for (File projectDir : projectDirs) {
			File telosysDir = configuration.getTelosysDir(userId, projectDir.getName());
			for (File file : telosysDir.listFiles()) {
				bundles.add(this.getBundleStats(userId, projectDir.getName(), file.getName()));
			}
		}
		return bundles;
	}

}
