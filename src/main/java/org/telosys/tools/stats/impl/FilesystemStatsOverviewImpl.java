package org.telosys.tools.stats.impl;

import org.apache.commons.io.FileUtils;
import org.telosys.tools.stats.FilesystemStatsOverview;
import org.telosys.tools.stats.PathHelper;
import org.telosys.tools.users.User;
import org.telosys.tools.users.UsersFileDAO;

import java.io.IOException;
import java.text.ParseException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FilesystemStatsOverviewImpl implements FilesystemStatsOverview  {
	private final List<User> users;
	private PathHelper pathHelper;


	public FilesystemStatsOverviewImpl(PathHelper pathHelper) {
		UsersFileDAO dao = new UsersFileDAO(pathHelper.getCsvFile().getAbsolutePath());
		this.pathHelper = pathHelper;
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
    public Map<String,Integer> getCountFileTypes() throws ParseException, IOException
    {
        Map<String,Integer> res = new HashMap<>();
        for (User user : this.users) {
            UsersStatsImpl stats = new UsersStatsImpl(PathHelper.getInstance(), user);
            List<String> userProjects = stats.getProjectsNames();
            for (String project : userProjects)
            {
                ProjectStatsImpl projectStats = new ProjectStatsImpl(PathHelper.getInstance(),project,user.getLogin());
                Map<String,Integer> userFilesTypes = projectStats.getCountFileTypes();
                for(String key : userFilesTypes.keySet())
                {
                    if(!res.containsKey(key))
                        res.put(key,userFilesTypes.get(key));
                    else
                        res.put(key,res.get(key) + userFilesTypes.get(key));
                }
            }
        }
        return  res;
    }


	@Override
	public long getDiskUsage() {
		return FileUtils.sizeOfDirectory(pathHelper.getRoot());
	}

}
