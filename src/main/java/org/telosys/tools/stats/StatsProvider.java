package org.telosys.tools.stats;

import org.telosys.tools.stats.exception.ProjectNotFoundException;

import java.io.File;
import java.util.List;

public interface StatsProvider {

	/**
	 * Returns the root folder for the current StatsProvider
	 * @return
	 */
	File getRoot();
	
	/**
	 * Returns the global stats overview
	 * @return
	 */
	FilesystemStatsOverview getFilesystemStatsOverview() ;
	
	/**
	 * Returns the user stats for the given user
	 * 
	 * @param userId
	 * @return
	 */
	UserStats getUserStats(String userId) ;
	
	/**
	 * Returns the project stats for the given project name
	 * @param userId
	 * @param projectName
	 * @return
	 */
	ProjectStats getProjectStats(String userId, String projectName) throws ProjectNotFoundException;
	
	/**
	 * Returns the model stats for the given model name
	 * @param userId
	 * @param projectName
	 * @param modelName
	 * @return
	 */
	ModelStats getModelStats(String userId, String projectName, String modelName) ;
	
	/**
	 * Returns the bundle stats for the given model name
	 * @param userId
	 * @param projectName
	 * @param bundleName
	 * @return
	 */
	BundleStats getBundleStats(String userId, String projectName, String bundleName) ;
	
	//------------------------------------------------------------------------------
	// 
	//------------------------------------------------------------------------------
	List<ProjectStats> getProjectsStats(String userId);
	
	List<ModelStats>   getModelsStats(String userId);
	
	List<BundleStats>  getBundlesStats(String userId);

	
}
