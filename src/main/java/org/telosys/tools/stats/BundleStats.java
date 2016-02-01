package org.telosys.tools.stats;

import java.util.Date;

public interface BundleStats {

	/**
	 * The project name <br>
	 * Origin : folder name in the filesystem
	 * @return
	 */
	String getProjectName() ;
	
	/**
	 * The bundle name <br>
	 * Origin : model folder name in the filesystem <br>
	 * @return
	 */
	String getBundleName() ;
	
	//------------------------------------------------------------------------------
	// DATES
	//------------------------------------------------------------------------------
	/**
	 * Returns the installation date of the bundle <br> 
	 * Origin : installation date file ( ".date" file )
	 * @return
	 */
	Date getInstallationDate() ;
	
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
