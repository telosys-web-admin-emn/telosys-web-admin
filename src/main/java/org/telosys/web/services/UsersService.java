package org.telosys.web.services;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
	public final static String SEARCH_BY_LOGIN = "searchLogin";
	public final static String SEARCH_BY_BEGINNING_CREATION_DATE = "searchBeginCreationDate";
	public final static String SEARCH_BY_ENDING_CREATION_DATE = "searchEndCreationDate";
	public final static String SEARCH_BY_BEGINNING_LAST_CONNECTION_DATE = "searchBeginLastConnectionDate";
	public final static String SEARCH_BY_ENDING_LAST_CONNECTION_DATE = "searchEndLastConnectionDate";
	public final static String SEARCH_BY_IS_CONNECTED = "searchIsConnected";
	
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
	 * Build the filters for the view users
	 * @param request
	 * @return HttpServletRequest
	 */
	public HttpServletRequest buildUsersFilters(HttpServletRequest request){
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
	 * Search users
	 * @param users
	 * @param request
	 * @return List<UsersStatsImpl
	 */
	public List<UsersStatsImpl> searchUser(List<UsersStatsImpl> users, HttpServletRequest request) {
		List<UsersStatsImpl> searchedUsers = users;
		String searchByLogin = request.getParameter(SEARCH_BY_LOGIN);
		searchedUsers = handleSearchLogin(searchedUsers, searchByLogin);
		String beginCreationDate = request.getParameter(SEARCH_BY_BEGINNING_CREATION_DATE);
		String endCreationDate = request.getParameter(SEARCH_BY_ENDING_CREATION_DATE);
		searchedUsers = handleSearchCreationDate(searchedUsers, beginCreationDate, endCreationDate);
		String beginLastConnectionDate = request.getParameter(SEARCH_BY_BEGINNING_LAST_CONNECTION_DATE);
		String endLastConnectionDate = request.getParameter(SEARCH_BY_ENDING_LAST_CONNECTION_DATE);
		searchedUsers = handleSearchLastConnectionDate(searchedUsers, beginLastConnectionDate, endLastConnectionDate);
		// todo : gerer le statut connecte des users
		return searchedUsers;
	}

	/**
	 * Compare a list of UsersStatsImpl users
	 * @param httpServletRequest
	 * @return Comparator<UsersStatsImpl>
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
	
	/**
	 * Search users by login
	 * @param searchedUsers
	 * @param searchByLogin
	 * @return List<UsersStatsImpl>
	 */
	protected List<UsersStatsImpl> handleSearchLogin(List<UsersStatsImpl> searchedUsers, String searchByLogin) {
		if(searchByLogin != null && !searchByLogin.equals("")) {
			searchedUsers = searchedUsers
					.stream()
					.filter(
							u -> u.getLogin()
							.toUpperCase()
							.contains(searchByLogin.toUpperCase())
							)
					.collect(Collectors.toList());
		}
		return searchedUsers;
	}
	
	/**
	 * Search users by creation date
	 * @param searchedUsers
	 * @param beginCreationDate
	 * @param endCreationDate
	 * @return List<UsersStatsImpl>
	 */
	protected List<UsersStatsImpl> handleSearchCreationDate(List<UsersStatsImpl> searchedUsers, String beginCreationDate, String endCreationDate) {
		if(beginCreationDate != null && !beginCreationDate.equals("")) {
			searchedUsers = searchedUsers
					.stream()
					.filter(
							u -> u.getCreationDate()
							.compareTo(stringToDate(beginCreationDate)) >= 0
							)
					.collect(Collectors.toList());
							
		}
		if(endCreationDate != null && !endCreationDate.equals("")) {
			searchedUsers = searchedUsers
					.stream()
					.filter(
							u -> u.getCreationDate()
							.compareTo(stringToDate(endCreationDate)) <= 0
							)
					.collect(Collectors.toList());
							
		}
		return searchedUsers;
	}
	
	/**
	 * Search users by last connection date
	 * @param searchedUsers
	 * @param beginLastConnectionDate
	 * @param endLastConnectionDate
	 * @return List<UsersStatsImpl>
	 */
	protected List<UsersStatsImpl> handleSearchLastConnectionDate(List<UsersStatsImpl> searchedUsers, String beginLastConnectionDate, String endLastConnectionDate) {
		if(beginLastConnectionDate != null && !beginLastConnectionDate.equals("")) {
			searchedUsers = searchedUsers
					.stream()
					.filter(
							u -> u.getLastConnectionDate()
							.compareTo(stringToDate(beginLastConnectionDate)) >= 0
							)
					.collect(Collectors.toList());
							
		}
		if(endLastConnectionDate != null && !endLastConnectionDate.equals("")) {
			searchedUsers = searchedUsers
					.stream()
					.filter(
							u -> u.getLastConnectionDate()
							.compareTo(stringToDate(endLastConnectionDate)) <= 0
							)
					.collect(Collectors.toList());
							
		}
		return searchedUsers;
	}
	
	protected Date stringToDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat(pathHelper.getViewDateFormat());
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}
	
}
