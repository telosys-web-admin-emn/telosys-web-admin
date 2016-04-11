package org.telosys.admin.actions;

import org.nanoj.web.tinymvc.GenericAction;
import org.telosys.tools.stats.*;
import org.telosys.tools.api.TelosysUsers;
import org.telosys.tools.stats.exception.ProjectNotFoundException;
import org.telosys.tools.stats.impl.UsersStatsImpl;
import org.telosys.tools.users.User;
import org.telosys.tools.users.UsersFileName;
import org.telosys.tools.users.UsersManager;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
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
				// Step 1 : retrieve user project stats
				UserStats usersStats = provider.getUserStats(userName);
				List<ProjectStats> projectStats = new ArrayList<>();
				for (String project : usersStats.getProjectsNames()) {
					projectStats.add(provider.getProjectStats(userName, project));
				}
				httpServletRequest.setAttribute("projectStats", projectStats);

				// Step 2 : retrieve user stats
				UsersFileName.setSpecificFileName(Configuration.getTelosysSaasLocation()+"/fs/users.csv");
				UsersManager users = UsersManager.getInstance();
				User myUser = users.getUserByLogin(userName);
				UserStats myUserstat = new UsersStatsImpl(myUser, new File(Configuration.getTelosysSaasLocation()+"/fs"));
				httpServletRequest.setAttribute("user", myUser);
				httpServletRequest.setAttribute("userStats", myUserstat);

			}
		} catch(IOException | ParseException | ProjectNotFoundException e)
		{
			e.printStackTrace();
		}

		return "user";
	}
}
