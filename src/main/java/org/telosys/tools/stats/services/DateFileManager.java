package org.telosys.tools.stats.services;

import java.io.File;
import java.util.Date;

/**
 * A manager class for a date which is stored in a file <br>
 * e.g. "generation.date", "creation.date", ...
 *  
 */
public class DateFileManager {

	private final File file ;
	
	public DateFileManager(File file) {
		super();
		this.file = file;
	}

	public File getFile() {
		return file ;
	}
	
	public void writeDate(Date date) {
		// write (no lock ?)
	}

	public void writeDate() {
		writeDate(new Date());
	}

	public Date readDate() {
		return null ;
	}
	
}
