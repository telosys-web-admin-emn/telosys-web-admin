package org.telosys.admin.actions;

import org.nanoj.web.tinymvc.GenericAction;
import org.telosys.tools.stats.Configuration;
import org.telosys.tools.stats.FilesystemStatsOverview;
import org.telosys.tools.stats.StatsProvider;
import org.telosys.tools.stats.StatsProviderFactory;
import org.telosys.tools.stats.impl.FilesystemStatsOverviewImpl;
import org.telosys.tools.stats.impl.StatsProviderImpl;
import org.telosys.web.services.UsersService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.ParseException;
import java.io.IOException;

public class StatisticsAction extends GenericAction {

    @Override
    public String process(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        FilesystemStatsOverview statsOverview = StatsProviderFactory.getStatsProvider().getFilesystemStatsOverview();
        try {
            //Get statistics from stats overview class
            int usersCount = statsOverview.getUsersCount();
            int projectsCount = statsOverview.getProjectsCount();
            int modelsCount = statsOverview.getModelsCount();
            long diskUsage = statsOverview.getDiskUsage();
            //Need to multipy by 1.0 to cast in double and have a double result
            double averageProjects = projectsCount * 1.0 / usersCount;
            double averageModels = modelsCount * 1.0 / usersCount;
            double averageDiskUsage = diskUsage * 1.0 / usersCount;
            //Add statistics to the body
            httpServletRequest.setAttribute("usersCount", usersCount);
            httpServletRequest.setAttribute("projectsCount", projectsCount);
            httpServletRequest.setAttribute("modelsCount", modelsCount);
            httpServletRequest.setAttribute("diskUsage", diskUsage);

            httpServletRequest.setAttribute("averageProjects", averageProjects);
            httpServletRequest.setAttribute("averageModels", averageModels);
            httpServletRequest.setAttribute("averageDiskUsage", averageDiskUsage );
        } catch (IOException | ArithmeticException | ParseException e )
        {
            e.printStackTrace();
        }
      return "statistics";
    }
}
