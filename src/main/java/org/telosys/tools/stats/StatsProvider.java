package org.telosys.tools.stats;

import com.google.inject.Singleton;
import org.telosys.tools.stats.exception.BadInputException;
import org.telosys.tools.stats.exception.ProjectNotFoundException;
import org.telosys.tools.stats.exception.UserNotFoundException;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

@Singleton
public interface StatsProvider {

	/**
	 * Returns the root folder for the current StatsProvider
	 * @return
	 */
	Path getRoot();
	
	/**
	 * Returns the global stats overview
	 * @return
	 */
	FilesystemStatsOverview getFilesystemStatsOverview() ;

	/**
	 * Returns this stats for a specific login of the application
	 * @return
	 */
	UserStats getUserStats(String login) throws BadInputException, UserNotFoundException;

	/**
	 * Returns the stats of all users
	 */
	UsersStats getUsersStats();
	
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
