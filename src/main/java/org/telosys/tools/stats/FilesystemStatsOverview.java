package org.telosys.tools.stats;

/**
 * Global filesystem information for all users <br>
 * 
 * Information is based on the files and folders in the filesystem
 *
 */
public interface FilesystemStatsOverview {

	/**
	 * Returns the total number of users folders
	 * @return
	 */
	public int getUsersCount() ;
	
	/**
	 * Returns the total number of projects folders for all the users
	 * @return
	 */
	public int getProjectsCount() ;
	
	/**
	 * Return the total number of ".model" files for all the users
	 * @return
	 */
	public int getModelsCount() ;
	
	/**
	 * Return the total disk usage for all the users
	 * @return
	 */
	public int getDiskUsage() ;
	
}
