package org.telosys.admin.actions;

import org.nanoj.web.tinymvc.GenericAction;
import org.telosys.tools.stats.*;
import org.telosys.tools.stats.dto.*;
import org.telosys.tools.users.User;
import org.telosys.tools.users.UsersManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
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
				UsersManager.setUsersFileName(pathHelper.getCsvFile().getPath());
				UsersManager users = UsersManager.getInstance();
				User myUser = users.getUserByLogin(userName);
				UserStats usersStats = provider.getUserStats(userName);
				if(myUser == null || usersStats == null)
				{
					request.setAttribute("erreur", "Impossible de trouver l'utilisateur.");
					return "erreur : layout";
				}
				request.setAttribute("user", UserDTO.fromUser(myUser, pathHelper.getViewDateFormat()));
				request.setAttribute("userStats", UserStatsDTO.fromUserStats(usersStats, pathHelper.getViewDateFormat()));

				// User's projects stats
				List<ProjectStatsDTO> projectsDTO = new ArrayList<>();
				for (ProjectStats projectStats : provider.getProjectsStats(userName)) {
					projectsDTO.add(ProjectStatsDTO.fromProjectStats(projectStats, pathHelper.getViewDateFormat()));
				}
				request.setAttribute("projectStats", projectsDTO);
				List<BundleStatsDTO> bundlesDTO = new ArrayList<>();
				for (BundleStats bundleStats : provider.getBundlesStats(userName)) {
					bundlesDTO.add(BundleStatsDTO.fromBundleStats(bundleStats, pathHelper.getViewDateFormat()));
				}
				request.setAttribute("bundleStats", bundlesDTO);
				List<ModelStatsDTO> modelsDTO = new ArrayList<>();
				for (ModelStats modelStats : provider.getModelsStats(userName)) {
					modelsDTO.add(ModelStatsDTO.fromModelStats(modelStats, pathHelper.getViewDateFormat()));
				}
				request.setAttribute("modelStats", modelsDTO);

				InputStream is = PathHelper.class.getResourceAsStream("/META-INF/webadmin.properties");
				Configuration conf = new Configuration(is);
				request.setAttribute("quota", conf.getDiskUsageQuota());
				request.setAttribute("title", "User: " + userName);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			request.setAttribute("error", "Cannot load user, error : " + e.getLocalizedMessage());
			return "error : layout";
		}

		return "user : layout";
	}
}
