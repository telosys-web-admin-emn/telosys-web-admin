package org.telosys.tools.stats.impl;

import org.apache.commons.io.FileUtils;
import org.telosys.tools.helper.CSVReader;
import org.telosys.tools.stats.FilesystemStatsOverview;
import org.telosys.tools.stats.services.FilesystemUtil;

import java.io.*;
import java.util.List;

public class FilesystemStatsOverviewImpl implements FilesystemStatsOverview  {
	private File root;
	private CSVReader users;


	public FilesystemStatsOverviewImpl(File root) {
		this.root = root;
	}

	private void readUsersFile() throws IOException {
		FileReader user_file = new FileReader(this.root.getAbsolutePath()+"/users.csv");
		BufferedReader buffer = new BufferedReader(user_file);
		StringBuilder res = new StringBuilder();
		for(String line = buffer.readLine(); line != null; line = buffer.readLine()) {
			res.append(line).append("\n");
		}
		this.users = new CSVReader(res.toString());
	}

	@Override
	public int getUsersCount() {
		try {
			this.readUsersFile();
			return this.users.numberOfLines();
		}
		catch (IOException e) {
			return -1;
		}
	}

	@Override
	public int getProjectsCount() {
		int somme = 0;
		try {
			this.readUsersFile();
			for(int i=0; i<this.users.numberOfLines(); ++i) {
				UsersStatsImpl usersStats = new UsersStatsImpl(root, this.users.getColumn(i,0));
				somme += usersStats.getProjectsCount();
			}
			return somme;
		} catch (IOException e) {
			return -1;
		}

	}

	@Override
	public int getModelsCount() {
		int somme = 0;
		try {
			this.readUsersFile();
			for(int i=0; i<this.users.numberOfLines(); ++i) {
				UsersStatsImpl usersStats = new UsersStatsImpl(root, this.users.getColumn(i,0));
				somme += usersStats.getModelsCount();
			}
			return somme;
		} catch (IOException e) {
			return -1;
		}
	}

	@Override
	public long getDiskUsage() {
		try
		{
			return FileUtils.sizeOfDirectory(new File(root.getCanonicalFile() + "/fs"));
		}
		catch(IOException e)
		{
			return -1;
		}
	}

}
