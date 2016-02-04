package org.telosys.tools.stats.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.telosys.tools.stats.BundleStats;
import org.telosys.tools.stats.FilesystemStatsOverview;
import org.telosys.tools.stats.ModelStats;
import org.telosys.tools.stats.ProjectStats;
import org.telosys.tools.stats.StatsProvider;
import org.telosys.tools.stats.UserStats;
import org.telosys.tools.stats.exception.ProjectNotFoundException;

public class StatsProviderImpl implements StatsProvider {

	private final File root ;

	public StatsProviderImpl(File root) {
		super();
		this.root = root;
	}

	@Override
	public File getRoot() {
		return root ;
	}

	@Override
	public FilesystemStatsOverview getFilesystemStatsOverview() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserStats getUserStats(String userId) {
		// TODO Auto-generated method stub
		return null;
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
