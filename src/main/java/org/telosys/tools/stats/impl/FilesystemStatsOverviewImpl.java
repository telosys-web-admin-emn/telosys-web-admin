package org.telosys.tools.stats.impl;

import org.apache.commons.io.FileUtils;
import org.telosys.tools.stats.Configuration;
import org.telosys.tools.stats.FilesystemStatsOverview;
import org.telosys.tools.users.User;
import org.telosys.tools.users.UsersFileDAO;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class FilesystemStatsOverviewImpl implements FilesystemStatsOverview  {
	private final List<User> users;
	private File root;


	public FilesystemStatsOverviewImpl(File root) {
		this.root = root;
		UsersFileDAO dao = new UsersFileDAO(Configuration.getTelosysSaasLocation()+"/fs/users.csv");
		this.users = (List<User>) dao.loadAllUsers().values();
	}

	@Override
	public int getUsersCount() throws ParseException {
		return this.users.size();
	}

	@Override
	public int getProjectsCount() throws IOException, ParseException {
		int somme = 0;
		for (User user : this.users) {
			UsersStatsImpl stats = new UsersStatsImpl(user, root);
			somme += stats.getProjectsCount();
		}
		return somme;
	}

	@Override
	public int getModelsCount() throws ParseException, IOException {
		int somme = 0;
		for (User user : this.users) {
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
