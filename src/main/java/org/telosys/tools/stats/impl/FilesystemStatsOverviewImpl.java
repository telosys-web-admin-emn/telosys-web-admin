package org.telosys.tools.stats.impl;

import org.apache.commons.io.FileUtils;
import org.telosys.tools.stats.PathHelper;
import org.telosys.tools.stats.FilesystemStatsOverview;
import org.telosys.tools.users.User;
import org.telosys.tools.users.UsersFileDAO;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FilesystemStatsOverviewImpl implements FilesystemStatsOverview  {
	private final List<User> users;
	private File root;


	public FilesystemStatsOverviewImpl(File root) {
        this.root = root;
        UsersFileDAO dao = null;
        try {
            dao = new UsersFileDAO(root.getCanonicalPath()+"/users.csv");

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.users = dao.loadAllUsers().entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
	}

	@Override
	public int getUsersCount() throws ParseException {
		return this.users.size();
	}

	@Override
	public int getProjectsCount() throws IOException, ParseException {
		int somme = 0;
		for (User user : this.users) {
			UsersStatsImpl stats = new UsersStatsImpl(PathHelper.getInstance(), user);
			somme += stats.getProjectsCount();
		}
		return somme;
	}

	@Override
	public int getModelsCount() throws ParseException, IOException {
		int somme = 0;
		for (User user : this.users) {
			UsersStatsImpl stats = new UsersStatsImpl(PathHelper.getInstance(), user);
			somme += stats.getModelsCount();
		}
		return somme;
	}

	@Override
	public long getDiskUsage() {
			return FileUtils.sizeOfDirectory(root);
	}

}
