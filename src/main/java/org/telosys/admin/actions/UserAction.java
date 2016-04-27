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
import java.util.LinkedList;
import java.util.List;

public class UserAction extends GenericAction
{

	private StatsProvider provider = StatsProviderFactory.getStatsProvider();

	private PathHelper pathHelper;

	public UserAction()
	{
		super();
		this.pathHelper = PathHelper.getInstance();
	}

	@Override
	public String process(HttpServletRequest request, HttpServletResponse httpServletResponse)
	{
		try
		{
			String userName = request.getParameter("username");
			if(userName != null)
			{
				// User's stats.
				UsersFileName.setSpecificFileName(pathHelper.getCsvFile().getPath());
				UsersManager users = UsersManager.getInstance();
				User myUser = users.getUserByLogin(userName);
				UserStats usersStats = provider.getUserStats(userName);
				if(myUser == null || usersStats == null)
				{
					request.setAttribute("erreur", "Impossible de trouver l'utilisateur.");
					return "erreur";
				}
				request.setAttribute("user", myUser);
				request.setAttribute("userStats", usersStats);

				// User's projects stats
				request.setAttribute("projectStats", provider.getProjectsStats(userName));
				request.setAttribute("bundleStats", provider.getBundlesStats(userName));
				request.setAttribute("modelStats", provider.getModelsStats(userName));
				// add the date format to format creation and last connection dates
				request.setAttribute("dateFormat", pathHelper.getViewDateFormat());

			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			request.setAttribute("error", e.getClass().getName());
			return "error";
		}

		return "user";
	}
}
