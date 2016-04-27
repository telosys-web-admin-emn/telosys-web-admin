package org.telosys.tools.stats;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ProjectStats {

	//------------------------------------------------------------------------------
	// FILESYSTEM
	//------------------------------------------------------------------------------
	/**
	 * The number of each type in a project <br>
	 * Origin : all the project files in the filesystem
	 * @return
	 */
	Map<String,Integer> getCountFileTypes();
	/**
	 * The project name <br>
	 * Origin : folder name in the filesystem
	 * @return
	 */
	String getProjectName() ;
	
	/**
	 * The number of bundles installed in the project <br>
	 * Origin : Bundles folders in the filesystem
	 * @return
	 */
	int getBundlesCount();
	
	/**
	 * List of bundles names in the project <br>
	 * @return
	 */
	List<String> getBundlesNames() ;

	/**
	 * The number of models defined in the project <br>
	 * Origin : Models files in the filesystem
	 * @return
	 */
	int getModelsCount();

	/**
	 * List of models names in the project <br>
	 * @return
	 */
	List<String> getModelsNames() ;

	/**
	 * The total disk usage for the project <br>
	 * Origin : all the project files in the filesystem
	 * @return
	 */
	long getDiskUsage();

	/**
	 * The total disk usage for the project in megabytes<br>
	 * @see ProjectStats#getDiskUsage()
	 * @return
	 */
	String getDiskUsageMB();

	//------------------------------------------------------------------------------
	// DATES
	//------------------------------------------------------------------------------
	/**
	 * The creation date of the project 
	 * Origin : creation date file ( ".date" file)
	 * @return
	 */
	Date getCreationDate() throws IOException;
	
	/**
	 * The max date retrieved from all the ".date" files of the project
	 * @return
	 */
	Date getLastGenerationDate() throws IOException;
	
	//------------------------------------------------------------------------------
	// COUNTERS
	//------------------------------------------------------------------------------
	/**
	 * The number of code generations launched for the project <br>
	 * Origin : generations counter file ( ".counter" file )
	 * @return
	 */
	int getGenerationsCount();


}
