package org.telosys.admin.actions;

import org.nanoj.web.tinymvc.GenericAction;
import org.telosys.tools.helper.FileUnit;
import org.telosys.tools.stats.FilesystemStatsOverview;
import org.telosys.tools.stats.StatsProviderFactory;
import org.telosys.web.services.StatisticsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class StatisticsAction extends GenericAction {

    @Override
    public String process(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        FilesystemStatsOverview statsOverview = StatsProviderFactory.getStatsProvider().getFilesystemStatsOverview();

        try {
            httpServletRequest = StatisticsService.buildCounts(httpServletRequest);
            // On récupère chaque type fichier
            Map<String,Integer> usersProjectsTypes = statsOverview.getCountFileTypes();
            Map<String,Integer> cleanProjectsTypes = new HashMap<>();
            usersProjectsTypes.forEach((key,value) -> {
                if (!key.isEmpty())
                    cleanProjectsTypes.put(key,value);
            });
            httpServletRequest.setAttribute("filesTypes", cleanProjectsTypes);
        } catch (IOException | ArithmeticException | ParseException e ) {
            e.printStackTrace();
        }
      return "statistics";
    }
}
