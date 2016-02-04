package org.telosys.tools.stats;

import java.io.IOException;
import java.util.Date;

public interface ModelStats {

	/**
	 * The project name <br>
	 * Origin : folder name in the filesystem
	 * @return
	 */
	String getProjectName() ;
	
	/**
	 * The model name <br>
	 * Origin : model file name in the filesystem <br>
	 * e.g. : "cars.model"
	 * @return
	 */
	String getModelName() ;
	
	/**
	 * Returns the last modified date of the model file <br> 
	 * Origin : model file OS info 
	 *  see : Files.readAttributes + attr.lastModifiedTime() (OK in Windows and Linux)
	 * @return
	 */
	Date getLastModifiedDate() throws IOException;
	
//	/**
//	 * Returns the creation date of the model file <br> 
//	 * Origin : "creation.date"
//	 * @return
//	 */
//	Date getCreationDate() ;
	
}
