package org.telosys.admin.actions;

import org.nanoj.web.tinymvc.GenericAction;
import org.telosys.tools.stats.*;
import org.telosys.tools.stats.dto.BundleDTO;
import org.telosys.tools.stats.dto.ModelDTO;
import org.telosys.tools.stats.dto.ProjectDTO;
import org.telosys.tools.stats.dto.UserDTO;
import org.telosys.tools.users.User;
import org.telosys.tools.users.UsersFileName;
import org.telosys.tools.users.UsersManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
				request.setAttribute("userStats", UserDTO.fromUserStats(usersStats));

				// User's projects stats
				List<ProjectDTO> projectsDTO = new ArrayList<>();
				for (ProjectStats projectStats : provider.getProjectsStats(userName)) {
					projectsDTO.add(ProjectDTO.fromProjectStats(projectStats));
				}
				request.setAttribute("projectStats", projectsDTO);
				List<BundleDTO> bundlesDTO = new ArrayList<>();
				for (BundleStats bundleStats : provider.getBundlesStats(userName)) {
					bundlesDTO.add(BundleDTO.fromBundleStats(bundleStats));
				}
				request.setAttribute("bundleStats", bundlesDTO);
				List<ModelDTO> modelsDTO = new ArrayList<>();
				for (ModelStats modelStats : provider.getModelsStats(userName)) {
					modelsDTO.add(ModelDTO.fromModelStats(modelStats));
				}
				request.setAttribute("modelStats", modelsDTO);
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
