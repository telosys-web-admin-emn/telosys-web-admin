package org.telosys.tools.stats.impl;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.telosys.tools.stats.*;
import org.telosys.tools.stats.exception.ProjectNotFoundException;
import org.telosys.tools.users.User;
import org.telosys.tools.users.UsersFileName;
import org.telosys.tools.users.UsersManager;

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
			return new UsersStatsImpl(myUser, new File(Configuration.getTelosysSaasLocation() + "/fs/" + userId));
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
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<ProjectStats> getProjectsStats(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ModelStats> getModelsStats(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BundleStats> getBundlesStats(String userId) {
		// TODO Auto-generated method stub
		return null;
	}


	private String userDirPath(String userId) {
		return this.root.getPath() + File.separator + userId;
	}

	private String projectDirPath(String userId, String projectId) {
		return this.userDirPath(userId) + File.separator + projectId;
	}
}
