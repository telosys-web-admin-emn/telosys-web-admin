package org.telosys.admin.actions;

import java.io.IOException;
import java.text.ParseException;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nanoj.web.tinymvc.GenericAction;
import org.telosys.admin.actions.helper.FilterSorter;
import org.telosys.admin.actions.helper.Paginator;
import org.telosys.tools.stats.PathHelper;
import org.telosys.tools.stats.impl.UsersStatsImpl;
import org.telosys.web.services.UsersService;

public class UsersAction extends GenericAction{

	private PathHelper pathHelper;

	public UsersAction() {
		this.pathHelper = PathHelper.getInstance();
	}

    @Override
    public String process(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    	try {
    		List<UsersStatsImpl> allUsers = this.getUsersService().getUsers();
    		int maxPage = this.getMaxPage(allUsers.size());
			// build the pagination parameters
			httpServletRequest = Paginator.buildPagination(httpServletRequest, maxPage);
			httpServletRequest = FilterSorter.buildSorting(httpServletRequest);
			int page = (int) httpServletRequest.getAttribute(Paginator.CURRENT_PAGE_ATTRIBUTE);
    		List<UsersStatsImpl> users = this.getPaginatedUsers(allUsers, page, httpServletRequest);
    		httpServletRequest.setAttribute("users", users);
			
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
    	return Paginator.getPaginatedItems(page, pathHelper.getUsersPerPage(), allUsers);
    }
    
    /**
     * Get the max number of page
     * @param usersLength
     * @return int the max page number
     */
    protected int getMaxPage(int usersLength)
    {
    	int maxUsersPerPage = pathHelper.getUsersPerPage();
    	if(maxUsersPerPage != 0) {
    		return (int) Math.ceil((double)usersLength / maxUsersPerPage);
    	}
    	return usersLength;
    }
    
    /**
     * Get the usersService
     * @return UsersService
     */
    protected UsersService getUsersService()
    {
		return new UsersService();
    }
}
