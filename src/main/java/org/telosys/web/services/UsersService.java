package org.telosys.web.services;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.telosys.admin.actions.helper.FilterSorter;
import org.telosys.tools.stats.PathHelper;
import org.telosys.tools.stats.impl.UsersStatsImpl;
import org.telosys.tools.users.User;
import org.telosys.tools.users.UsersFileDAO;

public class UsersService {

	private PathHelper pathHelper;
	
	public UsersService()
	{
		this.pathHelper = PathHelper.getInstance();
	}
				
	public List<UsersStatsImpl> getUsers() throws IOException, ParseException
	{
		List<UsersStatsImpl> users = new ArrayList<UsersStatsImpl>();
		UsersFileDAO dao = new UsersFileDAO(pathHelper.getCsvFile());
		for(Map.Entry<String, User> entry : dao.loadAllUsers().entrySet()) {
			users.add(new UsersStatsImpl(pathHelper, entry.getValue()));
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
				String filter = httpServletRequest.getParameter(FilterSorter.FILTER_PARAMETER);
		    	if(filter == null) {
		    		filter = "login";
		    	}
		    	String order = httpServletRequest.getParameter(FilterSorter.ORDER_PARAMETER);
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
				if(order != null && order.equals(FilterSorter.DESCENDING_ORDER)) {
					compared = -compared;
				}
				return compared;
			}
		};
		return comparator;
    }
}
