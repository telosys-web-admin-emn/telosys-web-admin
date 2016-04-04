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
	private File usersCsvFile;
	
	public UsersService(String usersCsvFilePath)
	{
		this.usersCsvFile = new File(usersCsvFilePath);
	}
				
	public List<UsersStatsImpl> getUsers() throws IOException, ParseException
	{
		List<UsersStatsImpl> users = new ArrayList<UsersStatsImpl>();
    	File root = new File(Configuration.getTelosysSaasLocation()+"/fs/users.csv");
		UsersFileDAO dao = new UsersFileDAO(Configuration.getTelosysSaasLocation()+"/fs/users.csv");
		for(Map.Entry<String, User> entry : dao.loadAllUsers().entrySet()) {
			users.add(new UsersStatsImpl(entry.getValue(), root));
		}
		
		return users;
	}
}
