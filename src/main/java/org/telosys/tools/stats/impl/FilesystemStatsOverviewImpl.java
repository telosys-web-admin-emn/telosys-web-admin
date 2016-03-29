package org.telosys.tools.stats.impl;

import org.apache.commons.io.FileUtils;
import org.telosys.tools.entities.User;
import org.telosys.tools.helper.UsersCsvParser;
import org.telosys.tools.stats.FilesystemStatsOverview;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class FilesystemStatsOverviewImpl implements FilesystemStatsOverview  {
	private File root;
	private UsersCsvParser usersCsv;


	public FilesystemStatsOverviewImpl(File root) {
		this.root = root;
		this.usersCsv = new UsersCsvParser(new File(this.root.getAbsolutePath() + "/users.csv"));
	}

	@Override
	public int getUsersCount() throws ParseException {
		try {
			return this.usersCsv.parse().size();
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public int getProjectsCount() throws IOException, ParseException {
		List<User> users;
		try {
			users = usersCsv.parse();
		} catch(IOException e) {
			return -1;
		}

		int somme = 0;
		for (User user : users) {
			UsersStatsImpl stats = new UsersStatsImpl(user, root);
			somme += stats.getProjectsCount();
		}
		return somme;
	}

	@Override
	public int getModelsCount() throws ParseException, IOException {
		List<User> users;
		try {
			users = usersCsv.parse();
		} catch(IOException e) {
			return -1;
		}

		int somme = 0;
		for (User user : users) {
			UsersStatsImpl stats = new UsersStatsImpl(user, root);
			somme += stats.getModelsCount();
		}
		return somme;
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
