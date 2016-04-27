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
	public final static String LOGIN_FILTER = "login";
	public final static String CREATION_DATE_FILTER = "creationDate";
	public final static String LAST_CONNECTION_DATE_FILTER = "lastConnectionDate";
	public final static String MAIL_FILTER = "mail";
	public final static String DISK_USAGE_FILTER = "diskUsage";
	public final static String PROJECTS_COUNT_FILTER = "projectsCount";
	public final static String MODELS_COUNT_FILTER = "modelsCount";
	public final static String GENERATIONS_COUNT_FILTER = "generationsCount";
	
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
	public static HttpServletRequest buildUsersFilters(HttpServletRequest request){
		request.setAttribute("LOGIN_FILTER", LOGIN_FILTER);
		request.setAttribute("CREATION_DATE_FILTER", CREATION_DATE_FILTER);
		request.setAttribute("LAST_CONNECTION_DATE_FILTER", LAST_CONNECTION_DATE_FILTER);
		request.setAttribute("MAIL_FILTER", MAIL_FILTER);
		request.setAttribute("DISK_USAGE_FILTER", DISK_USAGE_FILTER);
		request.setAttribute("PROJECTS_COUNT_FILTER", PROJECTS_COUNT_FILTER);
		request.setAttribute("MODELS_COUNT_FILTER", MODELS_COUNT_FILTER);
		request.setAttribute("GENERATIONS_COUNT_FILTER", GENERATIONS_COUNT_FILTER);
		return request;
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
		    		filter = LOGIN_FILTER;
		    	}
		    	String order = httpServletRequest.getParameter(FilterSorter.ORDER_PARAMETER);
		    	// the result of the comparison
		    	int compared = 0;
				switch(filter){
					case CREATION_DATE_FILTER:
						compared = u1.getCreationDate().compareTo(u2.getCreationDate());
						break;
					case LAST_CONNECTION_DATE_FILTER:
						compared = u1.getLastConnectionDate().compareTo(u2.getLastConnectionDate());
						break;
					case MAIL_FILTER:
						compared =  u1.getMail().compareTo(u2.getMail());
						break;
					case DISK_USAGE_FILTER:
						compared = Double.compare(u1.getDiskUsage(), u2.getDiskUsage());
						break;
					case PROJECTS_COUNT_FILTER:
						compared = Integer.compare(u1.getProjectsCount(), u2.getProjectsCount());
						break;
					case MODELS_COUNT_FILTER:
						compared = Integer.compare(u1.getModelsCount(), u2.getModelsCount());
						break;
					case GENERATIONS_COUNT_FILTER:
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
