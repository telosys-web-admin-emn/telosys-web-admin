package org.telosys.admin.actions;

import org.nanoj.web.tinymvc.GenericAction;
import org.telosys.admin.actions.helper.FilterSorter;
import org.telosys.admin.actions.helper.Paginator;
import org.telosys.tools.stats.Configuration;
import org.telosys.tools.stats.PathHelper;
import org.telosys.tools.stats.dto.UserStatsDTO;
import org.telosys.tools.stats.impl.UsersStatsImpl;
import org.telosys.web.services.UsersService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UsersAction extends GenericAction
{
	private PathHelper pathHelper;

	public UsersAction()
	{
		this.pathHelper = PathHelper.getInstance();
	}

	@Override
	public String process(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
	{
		try
		{
			List<UsersStatsImpl> users = this.getUsersService().searchUser(this.getUsersService().getUsers(), httpServletRequest);
			int maxPage = this.getMaxPage(users.size());
			// build the pagination parameters
			httpServletRequest = Paginator.buildPagination(httpServletRequest, maxPage);
			// build sorting object
			httpServletRequest = FilterSorter.buildSorting(httpServletRequest);
			// build filters
			httpServletRequest = this.getUsersService().buildUsersFilters(httpServletRequest);
			// build searchs
			httpServletRequest = this.getUsersService().buildUsersSearchs(httpServletRequest);
			int page = (int) httpServletRequest.getAttribute(Paginator.CURRENT_PAGE_ATTRIBUTE);
			users = this.getPaginatedUsers(users, page, httpServletRequest);
			// add the users to the view
			List<UserStatsDTO> usersDTO = users.stream()
					.map(u -> UserStatsDTO.fromUserStats(u, pathHelper.getViewDateFormat()))
					.collect(Collectors.toList());
			httpServletRequest.setAttribute("users", usersDTO);
			InputStream is = PathHelper.class.getResourceAsStream("/META-INF/webadmin.properties");
			Configuration conf = new Configuration(is);
			httpServletRequest.setAttribute("quota", conf.getDiskUsageQuota());
		} catch(IOException | ParseException e)
		{
			e.printStackTrace();
		}
		return "users";
	}

	/**
	 * Paginate the user
	 *
	 * @param allUsers
	 * @param page
	 * @param httpServletRequest
	 * @return List of users paginated
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
	 *
	 * @param usersLength
	 * @return int the max page number
	 */
	protected int getMaxPage(int usersLength)
	{
		int maxUsersPerPage = pathHelper.getUsersPerPage();
		if(maxUsersPerPage != 0)
		{
			return (int) Math.ceil((double) usersLength / maxUsersPerPage);
		}
		return usersLength;
	}

	/**
	 * Get the usersService
	 *
	 * @return UsersService
	 */
	protected UsersService getUsersService()
	{
		return new UsersService();
	}
}
