package org.telosys.web.services;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.telosys.tools.entities.User;
import org.telosys.tools.helper.UsersCsvParser;
import org.telosys.tools.stats.impl.UsersStatsImpl;

public class UsersService {
	private File usersCsvFile;
	
	public UsersService(String usersCsvFilePath)
	{
		this.usersCsvFile = new File(usersCsvFilePath);
	}
				
	public List<UsersStatsImpl> getUsers() throws IOException, ParseException
	{
		UsersCsvParser uCP = new UsersCsvParser(this.usersCsvFile);
		List<UsersStatsImpl> users = new ArrayList<UsersStatsImpl>();
		String s = File.separator;
    	File root = new File("C:"+s+"Users"+s+"Xavier"+s+"git"+s+"telosys-saas"+s+"fs"+s);
		for(User u:uCP.parse()) {
			users.add(new UsersStatsImpl(u, root));
		}
		
		return users;
	}
}
