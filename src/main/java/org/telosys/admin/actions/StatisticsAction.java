package org.telosys.admin.actions;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringEscapeUtils;
import org.nanoj.web.tinymvc.GenericAction;
import org.telosys.tools.helper.FileUnit;
import org.telosys.tools.stats.FilesystemStatsOverview;
import org.telosys.tools.stats.ProjectStats;
import org.telosys.tools.stats.StatsProviderFactory;
import org.telosys.tools.stats.impl.ProjectStatsImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StatisticsAction extends GenericAction {

    @Override
    public String process(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        FilesystemStatsOverview statsOverview = StatsProviderFactory.getStatsProvider().getFilesystemStatsOverview();

        try {
            //Get statistics from stats overview class
            int usersCount = statsOverview.getUsersCount();
            int projectsCount = statsOverview.getProjectsCount();
            int modelsCount = statsOverview.getModelsCount();
            long diskUsage = Math.round(statsOverview.getDiskUsage()/ FileUnit.MEGABYTE);
            //Need to multiply by 1.0 to cast in double ) have a double result
            double averageProjects = projectsCount * 1.0 / usersCount;
            double averageModels = modelsCount * 1.0 / usersCount;
            long averageDiskUsage = Math.round(diskUsage / usersCount);
            //Add statistics to the body
            httpServletRequest.setAttribute("usersCount", usersCount);
            httpServletRequest.setAttribute("projectsCount", projectsCount);
            httpServletRequest.setAttribute("modelsCount", modelsCount);
            httpServletRequest.setAttribute("diskUsage", diskUsage);

            httpServletRequest.setAttribute("averageProjects", averageProjects);
            httpServletRequest.setAttribute("averageModels", averageModels);
            httpServletRequest.setAttribute("averageDiskUsage", averageDiskUsage );
            // On récupère chaque type fichier
            Map<String,Integer> usersProjectsTypes = statsOverview.getCountFileTypes();
            Map<String,Integer> cleanProjectsTypes = new HashMap<>();
            usersProjectsTypes.forEach((key,value) -> {
                if (key!= "")
                    cleanProjectsTypes.put(key,value);
            });
            httpServletRequest.setAttribute("filesTypes", cleanProjectsTypes);
        } catch (IOException | ArithmeticException | ParseException e ) {
            e.printStackTrace();
        }
      return "statistics";
    }
}
