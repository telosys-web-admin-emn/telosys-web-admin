package org.telosys.web.services;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.telosys.tools.stats.Configuration;
import org.telosys.tools.stats.impl.UsersStatsImpl;
import org.telosys.tools.users.User;
import org.telosys.tools.users.UsersFileDAO;

public class UsersService {

	private Configuration configuration;
	
	public UsersService()
	{
		this.configuration = Configuration.getInstance();
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
	
	/**
	 * Compare a list of UsersStatsImpl users
	 * @param httpServletRequest
	 * @return Comparator<UsersStatsImpl
	 */
	public Comparator<UsersStatsImpl> getUsersComparator(HttpServletRequest httpServletRequest)
    {
    	Comparator<UsersStatsImpl> comparator = new Comparator<UsersStatsImpl>(){
			public int compare(UsersStatsImpl u1, UsersStatsImpl u2) {
				String filter = httpServletRequest.getParameter("filter");
		    	if(filter == null) {
		    		filter = "login";
		    	}
		    	String order = httpServletRequest.getParameter("order");
		    	// the result of the comparison
		    	int compared = 0;
				switch(filter){
					case "creationDate":
						compared = u1.getCreationDate().compareTo(u2.getCreationDate());
						break;
					case "mail":
						compared =  u1.getMail().compareTo(u2.getMail());
						break;
					case "diskUsage":
						compared = Double.compare(u1.getDiskUsage(), u2.getDiskUsage());
						break;
					case "projectsCount":
						compared = Integer.compare(u1.getProjectsCount(), u2.getProjectsCount());
						break;
					case "modelsCount":
						compared = Integer.compare(u1.getModelsCount(), u2.getModelsCount());
						break;
					case "generationsCount":
						compared = Integer.compare(u1.getGenerationsCount(), u2.getGenerationsCount());
						break;
					default:
						compared = u1.getLogin().compareTo(u2.getLogin());
				}
				// check if we have to reverse the order
				if(order != null && order.equals("DESC")) {
					compared = -compared;
				}
				return compared;
			}
		};
		return comparator;
    }
	
	/**
	 * Get the list of UsersStatsImpl users paginated
	 * @param page
	 * @param maxPerPage
	 * @param providedUsers
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public List<UsersStatsImpl> getPaginatedUsers(int page, int maxPerPage, List<UsersStatsImpl> providedUsers) throws IOException, ParseException {
		int firstUserIndex = (page-1) * maxPerPage +1;
		System.out.println("first index : "+firstUserIndex);
		int userIndex = 1;
		int lastUserIndex = page * maxPerPage;
		System.out.println("Last user index : "+lastUserIndex);
		List<UsersStatsImpl> paginatedUsers = new ArrayList<UsersStatsImpl>();
		for(UsersStatsImpl user : providedUsers){
			if(userIndex >= firstUserIndex && userIndex <= lastUserIndex) {
				System.out.println("ajout du user "+user.getLogin());
				paginatedUsers.add(user);
				System.out.println("current user index : "+userIndex);
			}
			userIndex++;
			
		}
		return paginatedUsers;
	}
}
