package org.telosys.tools.stats.impl;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.telosys.tools.stats.Configuration;
import org.telosys.tools.stats.UserStats;

public class UsersStatsImpl implements UserStats {
	private String user;
	private File root;

	public UsersStatsImpl(File root, String user)
	{
		this.user = user;
		this.root = root;
	}

	@Override
	public String getLogin() {
		return this.user;
	}

	@Override
	public String getMail() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getCreationDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCountry() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLanguage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getProjectsCount() {
		return new File(this.getUserDirPath()).listFiles(File::isDirectory).length;
	}

	@Override
	public List<String> getProjectsNames() {
		File userDir = new File(this.getUserDirPath());
		return asList(userDir.listFiles())
				.stream()
				.map(f -> f.getName())
				.collect(toList());
	}

	@Override
	public int getModelsCount() {
		return 0;
	}

	@Override
	public int getBundlesCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<String> getBundlesNames() {
		// TODO Auto-generated method stub
		return null ;
	}

	@Override
	public int getDiskUsage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getGenerationsCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	private String getUserDirPath() {
		return this.root.getAbsolutePath() + File.separator + this.user;
	}

}
