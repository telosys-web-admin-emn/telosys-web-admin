package org.telosys.web.services;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.telosys.tools.stats.Configuration;
import org.telosys.tools.stats.impl.UsersStatsImpl;
import org.telosys.tools.users.User;
import org.telosys.tools.users.UsersFileDAO;
import org.telosys.tools.users.UsersFileName;
import org.telosys.tools.users.UsersManager;

public class UsersService {
	private Configuration configuration;
	
	public UsersService(Configuration configuration)
	{
		this.configuration = configuration;
	}
				
	public List<UsersStatsImpl> getUsers() throws IOException, ParseException
	{
		List<UsersStatsImpl> users = new ArrayList<UsersStatsImpl>();
		UsersFileDAO dao = new UsersFileDAO(configuration.getCsvFile());
		for(Map.Entry<String, User> entry : dao.loadAllUsers().entrySet()) {
			users.add(new UsersStatsImpl(configuration, entry.getValue()));
		}
		
		return users;
	}
}
