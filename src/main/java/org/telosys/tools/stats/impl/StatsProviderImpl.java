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

	public StatsProviderImpl(File root) {
		super();
		this.root = root;
	}

	@Override
	public File getRoot() {
		return root;
	}

	@Override
	public FilesystemStatsOverview getFilesystemStatsOverview() {
		return new FilesystemStatsOverviewImpl(root);
	}

	@Override
	public UserStats getUserStats(String userId) {
		try {
			UsersFileName.setSpecificFileName(Configuration.getTelosysSaasLocation() + "/fs/users.csv");
			UsersManager users = UsersManager.getInstance();
			User myUser = users.getUserByLogin(userId);
			return new UsersStatsImpl(myUser, new File(Configuration.getTelosysSaasLocation() + "/fs/"));
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
		return new ProjectStatsImpl(projectDir, projectName, userId);
	}

	@Override
	public ModelStats getModelStats(String userId, String projectName, String modelName) {
		// TODO Auto-generated method stub
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
			UsersFileName.setSpecificFileName(Configuration.getTelosysSaasLocation() + "/fs/users.csv");
			UsersManager users = UsersManager.getInstance();
			User myUser = users.getUserByLogin(userId);
			String userRoot = Configuration.getTelosysSaasLocation() + "/fs/";
			UserStats userStats = new UsersStatsImpl(myUser, new File(userRoot));
			List<String> projects = userStats.getProjectsNames();
			LinkedList<ProjectStats> projectsStats = new LinkedList<>();
			for(String s: projects)
			{
				projectsStats.add(new ProjectStatsImpl(new File(userRoot+userId+"/"+s), s, userId));
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
			File telosysDir = new File(projectDir.getPath() + File.separator + "TelosysTools" + File.separator + "templates");
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
