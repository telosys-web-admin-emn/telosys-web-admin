package org.telosys.tools.stats.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.List;

import org.telosys.tools.stats.*;
import org.telosys.tools.stats.exception.BadInputException;
import org.telosys.tools.stats.exception.ProjectNotFoundException;
import org.telosys.tools.stats.exception.UserNotFoundException;
import org.telosys.tools.stats.model.User;

public class StatsProviderImpl implements StatsProvider {

	private final Path root ;

	public StatsProviderImpl(Path root) {
		super();
		this.root = root;
	}

	@Override
	public Path getRoot() {
		return root ;
	}

	@Override
	public FilesystemStatsOverview getFilesystemStatsOverview() {
		// TODO Auto-generated method stub
		return null;
	}

	public UserStats getUserStats(String login) throws BadInputException, UserNotFoundException {
		User userInfo = this.getUsersStats().getUser(login);
		return new UserStatsImpl(this, root.resolve(login), userInfo);
	};

	@Override
	public UsersStats getUsersStats() {
		return new UsersStatsImpl(this);
	}


	@Override
	public ProjectStats getProjectStats(String userId, String projectName) throws ProjectNotFoundException {
		return this.getProjectsStats(userId)
				.stream()
				.filter(ps -> ps.getProjectName().equals(projectName))
				.findAny()
				.orElseThrow(() -> new ProjectNotFoundException(projectName));
	}

	@Override
	public ModelStats getModelStats(String userId, String projectName, String modelName) throws ProjectNotFoundException{
		ProjectStats projectStats = this.getProjectStats(userId, projectName);
		return projectStats.getModelsNames()
				.stream()
				.map(m -> new ModelStatsImpl())
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

}
