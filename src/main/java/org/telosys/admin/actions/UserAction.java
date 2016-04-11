package org.telosys.admin.actions;

import org.nanoj.web.tinymvc.GenericAction;
import org.telosys.tools.stats.*;
import org.telosys.tools.stats.exception.ProjectNotFoundException;
import org.telosys.tools.users.User;
import org.telosys.tools.users.UsersFileName;
import org.telosys.tools.users.UsersManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class UserAction extends GenericAction {

	StatsProvider provider = StatsProviderFactory.getStatsProvider();

	@Override
	public String process(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		try
		{
			String userName = httpServletRequest.getParameter("username");
			if (userName != null) {
				// User's stats.
				UsersFileName.setSpecificFileName(Configuration.getTelosysSaasLocation()+"/fs/users.csv");
				UsersManager users = UsersManager.getInstance();
				User myUser = users.getUserByLogin(userName);
				UserStats usersStats = provider.getUserStats(userName);
				httpServletRequest.setAttribute("user", myUser);
				httpServletRequest.setAttribute("userStats", usersStats);

				// User's projects stats
				List<ProjectStats> projectStats = new ArrayList<>();
				for (String project : usersStats.getProjectsNames()) {
					projectStats.add(provider.getProjectStats(userName, project));
				}
				httpServletRequest.setAttribute("projectStats", projectStats);

			}
		} catch(ProjectNotFoundException e)
		{
			e.printStackTrace();
		}

		return "user";
	}
}
