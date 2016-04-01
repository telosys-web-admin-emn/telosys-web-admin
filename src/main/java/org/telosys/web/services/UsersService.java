package org.telosys.web.services;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.telosys.tools.entities.User;
import org.telosys.tools.helper.UsersCsvParser;

public class UsersService {
	private File usersCsvFile;
	
	public UsersService(String usersCsvFilePath)
	{
		this.usersCsvFile = new File(usersCsvFilePath);
	}
				
	public List<User> getUsers() throws IOException, ParseException
	{
		UsersCsvParser uCP = new UsersCsvParser(this.usersCsvFile);
		
		return uCP.parse();
	}
}
