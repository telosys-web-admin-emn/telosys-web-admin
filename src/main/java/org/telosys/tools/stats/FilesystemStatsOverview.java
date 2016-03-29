package org.telosys.tools.stats;

import java.io.IOException;
import java.text.ParseException;

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
	 * @throws ParseException 
	 */
	int getUsersCount() throws ParseException ;
	
	/**
	 * Returns the total number of projects folders for all the users
	 * @return
	 * @throws ParseException 
	 * @throws IOException 
	 */
	int getProjectsCount() throws IOException, ParseException ;
	
	/**
	 * Return the total number of ".model" files for all the users
	 * @return
	 * @throws ParseException 
	 * @throws IOException 
	 */
	int getModelsCount() throws ParseException, IOException ;
	
	/**
	 * Return the total disk usage for all the users
	 * @return
	 */
	long getDiskUsage() ;
	
}
