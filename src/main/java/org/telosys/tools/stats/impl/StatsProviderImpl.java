package org.telosys.tools.stats.impl;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.sun.xml.internal.bind.v2.TODO;
import org.telosys.tools.commons.FileUtil;
import org.telosys.tools.stats.*;
import org.telosys.tools.stats.exception.ProjectNotFoundException;
import org.telosys.tools.users.User;
import org.telosys.tools.users.UsersFileName;
import org.telosys.tools.users.UsersManager;

import static java.util.Arrays.stream;

public class StatsProviderImpl implements StatsProvider {

	private final File root ;

	private Configuration configuration;

	public StatsProviderImpl(File root) {
		super();
		this.root = root;
		this.configuration = new Configuration(root);
	}

	@Override
	public File getRoot() {
		return root;
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
		File projectDir = new File(this.projectDirPath(userId, projectName));
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
		return new BundleStatsImpl(root, userId, bundleName, projectName);
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
		File userDir = new File(userDirPath(userId));
		File[] projectDirs = userDir.listFiles();
		for (File projectDir : projectDirs) {
			File telosysDir = configuration.getTelosysDir(userId, projectDir.getName());
			for (File file : telosysDir.listFiles()) {
				bundles.add(this.getBundleStats(userId, projectDir.getName(), file.getName()));
			}
		}
		return bundles;
	}


	private String userDirPath(String userId) {
		return this.root.getPath() + File.separator + userId;
	}

	private String projectDirPath(String userId, String projectId) {
		return this.userDirPath(userId) + File.separator + projectId;
	}
}
