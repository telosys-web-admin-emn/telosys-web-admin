package org.telosys.admin.actions;

import java.io.IOException;
import java.text.ParseException;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nanoj.web.tinymvc.GenericAction;
import org.telosys.tools.stats.Configuration;
import org.telosys.tools.stats.impl.UsersStatsImpl;
import org.telosys.web.services.UsersService;

public class UsersAction extends GenericAction {


	private Configuration configuration;

	public UsersAction() {
		super();
		this.configuration = Configuration.getInstance();
	}

    @Override
    public String process(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    	try {
    		List<UsersStatsImpl> allUsers = this.getUsersService().getUsers();
    		int page = this.getCurrentpage(httpServletRequest);
    		List<UsersStatsImpl> users = this.getPaginatedUsers(allUsers, page, httpServletRequest);
			httpServletRequest.setAttribute("users", users);
			httpServletRequest.setAttribute("currentPage", page);
			httpServletRequest.setAttribute("maxPage", this.getMaxPage(allUsers.size()));
			httpServletRequest.setAttribute("currentUri", httpServletRequest.getRequestURL()+"?"+httpServletRequest.getQueryString());
    	} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
    	return "users";
    }
    
    /**
     * Paginate the user
     * @param httpServletRequest
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public List<UsersStatsImpl> getPaginatedUsers(List<UsersStatsImpl> allUsers, int page, HttpServletRequest httpServletRequest) throws IOException, ParseException
    {
    	UsersService usersService = this.getUsersService();
    	// get the comparator for the user to order the list
    	Comparator<UsersStatsImpl> comparator = usersService.getUsersComparator(httpServletRequest);
    	// sort the list with comparator
    	allUsers.sort(comparator);
    	// finally, paginate the users
    	return usersService.getPaginatedUsers(page, Configuration.USERS_PER_PAGE, allUsers);
    }
    
    /**
     * Get the current page number
     * @param httpServletRequest
     * @return int the page number
     */
    protected int getCurrentpage(HttpServletRequest httpServletRequest)
    {
    	int page = 1;
    	String pageStringFormat = httpServletRequest.getParameter("page");
    	if(pageStringFormat != null) {
    		page = Integer.parseInt(pageStringFormat);
    	}
    	return page;
    }
    
    /**
     * Get the max number of page
     * @param usersLength
     * @return int the max page number
     */
    protected int getMaxPage(int usersLength)
    {
    	int maxUsersPerPage = Configuration.USERS_PER_PAGE;
    	if(maxUsersPerPage != 0) {
    		return usersLength / maxUsersPerPage;
    	}
    	return usersLength;
    }
    
    /**
     * Get the usersService
     * @return UsersService
     */
    protected UsersService getUsersService()
    {
    	UsersService usersService = new UsersService();
    	return usersService;
    }

}
