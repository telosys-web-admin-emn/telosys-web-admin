package org.telosys.admin.actions;

import org.nanoj.web.tinymvc.GenericAction;
import org.telosys.tools.stats.PathHelper;
import org.telosys.tools.stats.StatsProvider;
import org.telosys.tools.stats.StatsProviderFactory;
import org.telosys.tools.stats.UserStats;
import org.telosys.tools.users.User;
import org.telosys.tools.users.UsersFileName;
import org.telosys.tools.users.UsersManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	public String process(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
	{
		try
		{
			String userName = httpServletRequest.getParameter("username");
			if(userName != null)
			{
				// User's stats.
				UsersFileName.setSpecificFileName(pathHelper.getCsvFile().getPath());
				UsersManager users = UsersManager.getInstance();
				User myUser = users.getUserByLogin(userName);
				UserStats usersStats = provider.getUserStats(userName);
				if(myUser == null || usersStats == null)
				{
					httpServletRequest.setAttribute("erreur", "Impossible de trouver l'utilisateur.");
					return "erreur";
				}
				httpServletRequest.setAttribute("user", myUser);
				httpServletRequest.setAttribute("userStats", usersStats);

				// User's projects stats
				httpServletRequest.setAttribute("projectStats", provider.getProjectsStats(userName));
				httpServletRequest.setAttribute("bundleStats", provider.getBundlesStats(userName));
				httpServletRequest.setAttribute("modelStats", provider.getModelsStats(userName));

			}
		}
		catch(Exception e)
		{
			httpServletRequest.setAttribute("erreur", e.getCause());
			return "erreur";
		}

		return "user";
	}
}
