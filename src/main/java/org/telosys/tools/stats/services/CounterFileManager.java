package org.telosys.tools.stats.services;

import java.io.File;

/**
 * A counter which is stored in a file <br>
 * e.g. "generations.counter",  ...
 *
 */
public class CounterFileManager {
	
	private final File file ;
	
	public CounterFileManager(File file) {
		super();
		this.file = file;
	}

	public File getFile() {
		return file ;
	}
	
	
	public void incrementCounter() {
		// TODO
		// read, increment, write (with lock)
	}

	public int readCounter() {
		// TODO
		// read (no lock)
		return 0 ;
	}

}
