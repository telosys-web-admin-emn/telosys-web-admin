package org.telosys.tools.stats.impl;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.telosys.tools.entities.User;
import org.telosys.tools.helper.UsersCsvParser;
import org.telosys.tools.stats.Configuration;
import org.telosys.tools.stats.UserStats;

public class UsersStatsImpl implements UserStats {
	private User user;
	private File root;

	public UsersStatsImpl(User user, File root) throws IOException, ParseException
	{
		this.user = user;
		this.root = root;
	}

	@Override
	public String getLogin() {
		return this.user.getUsername();
	}

	@Override
	public String getMail() {
		return this.user.getEmail();
	}

	@Override
	public Date getCreationDate() {
		return this.user.getCreationDate();
	}

	@Override
	public String getCountry() {
		return this.user.getCountry();
	}

	@Override
	public String getLanguage() {
		return this.user.getLanguage();
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
				.map(File::getName)
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
	
	/**
	 * Get the path of the user
	 * @return string
	 */
	private String getUserDirPath() {
		// TODO : check path exists
		return this.root.getAbsolutePath() + File.separator + this.user.getUsername();
	}

}
