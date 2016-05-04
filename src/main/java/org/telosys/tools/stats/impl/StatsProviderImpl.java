package org.telosys.tools.stats.impl;

import org.telosys.tools.stats.*;
import org.telosys.tools.stats.exception.ProjectNotFoundException;
import org.telosys.tools.users.User;
import org.telosys.tools.users.UsersManager;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class StatsProviderImpl implements StatsProvider
{


	private PathHelper pathHelper;

	public StatsProviderImpl()
	{
		super();
		this.pathHelper = PathHelper.getInstance();
	}

	@Override
	public FilesystemStatsOverview getFilesystemStatsOverview()
	{
		return new FilesystemStatsOverviewImpl(pathHelper);
	}

	@Override
	public UserStats getUserStats(String userId)
	{
		try
		{
			UsersManager.setUsersFileName(pathHelper.getCsvFile().getPath());
			UsersManager users = UsersManager.getInstance();
			User myUser = users.getUserByLogin(userId);
			return new UsersStatsImpl(pathHelper, myUser);
		} catch(ParseException e)
		{
			return null;
		} catch(IOException e)
		{
			return null;
		}
	}

	@Override
	public ProjectStats getProjectStats(String userId, String projectName) throws ProjectNotFoundException
	{
		File projectDir = pathHelper.getProjectDir(userId, projectName);
		if(!projectDir.exists())
		{
			throw new ProjectNotFoundException(projectName);
		}
		return new ProjectStatsImpl(pathHelper, projectName, userId);
	}

	@Override
	public ModelStats getModelStats(String userId, String projectName, String modelName)
	{
		return new ModelStatsImpl(pathHelper, userId, projectName, modelName);
	}

	@Override
	public BundleStats getBundleStats(String userId, String projectName, String bundleName)
	{
		return new BundleStatsImpl(pathHelper, userId, bundleName, projectName);
	}

	@Override
	public List<ProjectStats> getProjectsStats(String userId)
	{
		try
		{
			UsersManager.setUsersFileName(pathHelper.getCsvFile().getPath());
			UsersManager users = UsersManager.getInstance();
			User myUser = users.getUserByLogin(userId);
			UserStats userStats = new UsersStatsImpl(pathHelper, myUser);
			List<String> projects = userStats.getProjectsNames();
			LinkedList<ProjectStats> projectsStats = new LinkedList<>();
			for(String s : projects)
			{
				ProjectStats pStats = new ProjectStatsImpl(pathHelper, s, userId);
				projectsStats.add(pStats);
			}
			return projectsStats;
		} catch(IOException e)
		{
			//TODO log exception.
			return null;
		} catch(ParseException e)
		{
			return null;
		}
	}

	@Override
	public List<ModelStats> getModelsStats(String userId)
	{
		List<ProjectStats> projectsStats = this.getProjectsStats(userId);
		if(projectsStats != null)
		{
			List<ModelStats> modelStats = new ArrayList<>();
			for(ProjectStats project : projectsStats)
			{
				for(String bundle : project.getModelsNames())
					modelStats.add(this.getModelStats(userId, project.getProjectName(), bundle));
			}
			return modelStats;
		}
		return null;
	}

	@Override
	public List<BundleStats> getBundlesStats(String userId)
	{
		List<BundleStats> bundles = new ArrayList<>();
		UserStats userStats = this.getUserStats(userId);

		for(String projectName : userStats.getProjectsNames()) {
			File templatesDir = pathHelper.getBundlesDir(userId, projectName);
			for(File file : templatesDir.listFiles()) {
				bundles.add(this.getBundleStats(userId, projectName, file.getName()));
			}
		}
		return bundles;
	}

}
