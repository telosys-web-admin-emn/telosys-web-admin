package org.telosys.tools.stats.impl;

import org.apache.commons.io.FileUtils;
import org.telosys.tools.stats.Configuration;
import org.telosys.tools.stats.FilesystemStatsOverview;
import org.telosys.tools.users.User;
import org.telosys.tools.users.UsersFileDAO;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class FilesystemStatsOverviewImpl implements FilesystemStatsOverview  {
	private final List<User> users;

	private Configuration configuration;


	public FilesystemStatsOverviewImpl(Configuration configuration) {
		this.configuration = configuration;
		UsersFileDAO dao = new UsersFileDAO(configuration.getCsvFile());
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
			UsersStatsImpl stats = new UsersStatsImpl(user, configuration);
			somme += stats.getProjectsCount();
		}
		return somme;
	}

	@Override
	public int getModelsCount() throws ParseException, IOException {
		int somme = 0;
		for (User user : this.users) {
			UsersStatsImpl stats = new UsersStatsImpl(user, configuration);
			somme += stats.getModelsCount();
		}
		return somme;
	}

	@Override
	public long getDiskUsage() {
		return FileUtils.sizeOfDirectory(configuration.getRoot());
	}

}
