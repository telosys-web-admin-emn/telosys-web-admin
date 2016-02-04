package org.telosys.tools.stats.impl;

import java.util.Date;
import java.util.List;

import org.telosys.tools.stats.UserStats;

public class UsersStatsImpl implements UserStats {
	private String user;

	public UsersStatsImpl(String user)
	{
		this.user = user;
	}

	@Override
	public String getLogin() {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<String> getProjectsNames() {
		// TODO Auto-generated method stub
		return null ;
	}

	@Override
	public int getModelsCount() {
		// TODO Auto-generated method stub
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

}
