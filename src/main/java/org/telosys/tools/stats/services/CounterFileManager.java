package org.telosys.tools.stats.services;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

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


	public void incrementCounter() throws IOException {
		FileLock lock = lockFile();
		int current = readCounter();

		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.write(current + 1 + "");
		lock.release();
	}

	public int readCounter() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String count = reader.readLine();
			return Integer.parseInt(count);
		} catch (Exception e) {
			//e.printStackTrace();
			return 0;
		}
	}

	private FileLock lockFile() throws IOException {
		FileChannel channel = new RandomAccessFile(file, "rw").getChannel();
		FileLock lock = channel.lock();
		return lock;
	}

}
