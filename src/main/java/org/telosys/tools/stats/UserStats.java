package org.telosys.tools.stats;

import java.util.Date;
import java.util.List;

public interface UserStats {

	//------------------------------------------------------------------------------
	// From User object
	// see :
	// org.telosys.saas.domain.User
	// org.telosys.saas.security.user.UsersManager
	//------------------------------------------------------------------------------
	
	String getLogin();
	
	String getMail();
	
	Date getCreationDate(); // to be added in User
	
	String getCountry(); // to be added in User
	
	String getLanguage(); // to be added in User
	
	//------------------------------------------------------------------------------
	// From the filesystem
	//------------------------------------------------------------------------------
	/**
	 * The number of projects  <br>
	 * Origin : projects folders found in the filesystem
	 * @return
	 */
	int getProjectsCount() ;
	
	/**
	 * Returns all the projects names for the user
	 * @return
	 */
	List<String> getProjectsNames() ;
	
	/**
	 * The number of models <br>
	 * Origin : models files found in the filesystem
	 * @return
	 */
	int getModelsCount() ;

	/**
	 * Returns all the models names for the user
	 * @return
	 */
	List<String> getModelsNames();
	
	/**
	 * The number of bundles <br>
	 * Origin : bundles folders found in the filesystem
	 * @return
	 */
	int getBundlesCount() ;
	
	/**
	 * Returns all the bundles names for the user
	 * @return
	 */
	List<String> getBundlesNames() ;
	
	/**
	 * The total disk usage <br>
	 * Origin : disk usage for all the user's files in the filesystem
	 * @return
	 */
	long getDiskUsage();
	
	//------------------------------------------------------------------------------
	// From ".counter" files
	//------------------------------------------------------------------------------
	/**
	 * The number of code generations launched for all the projects <br>
	 * Origin : generations counter files ( ".counter" files )
	 * @return
	 */
	int getGenerationsCount();
	
}
