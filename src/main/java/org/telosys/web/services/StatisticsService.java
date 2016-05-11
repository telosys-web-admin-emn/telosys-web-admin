package org.telosys.web.services;

import org.telosys.history.Configuration;
import org.telosys.history.Transformer;
import org.telosys.tools.helper.FileUnit;
import org.telosys.tools.stats.FilesystemStatsOverview;
import org.telosys.tools.stats.StatsProviderFactory;
import org.telosys.tools.stats.dto.StatisticsDTO;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class StatisticsService {
    public static final String USERS_COUNT = "usersCount";
    public static final String PROJECTS_COUNT = "projectsCount";
    public static final String MODELS_COUNT = "modelsCount";
    public static final String DISK_USAGE = "diskUsage";
    public static final String AVERAGE_PROJECTS = "averageProjects";
    public static final String AVERAGE_MODELS = "averageModels";
    public static final String AVERAGE_DISK_USAGE = "averageDiskUsage";

    /**
     * Build the counts to add it to the HttpServletRequest
     * @param request
     * @return HttpServletRequest updated
     */
    public static void buildCounts(HttpServletRequest request) {
        StatisticsDTO counts = computeCounts();
        request.setAttribute("statistics", counts);
    }

    /**
     * Compute the counts
     * @return Map containing the counts
     */
    public static StatisticsDTO computeCounts() {
        FilesystemStatsOverview statsOverview = StatsProviderFactory.getStatsProvider().getFilesystemStatsOverview();
        int usersCount = 0;
        int projectsCount = 0;
        int modelsCount = 0;
        try {
            //Get statistics from stats overview class
            usersCount = statsOverview.getUsersCount();
            projectsCount = statsOverview.getProjectsCount();
            modelsCount = statsOverview.getModelsCount();
        } catch(ParseException | IOException e) {
            e.printStackTrace();
        }
        long diskUsage = Math.round(statsOverview.getDiskUsage()/ FileUnit.MEGABYTE);
        double averageProjects = projectsCount * 1d / usersCount;
        double averageModels = modelsCount * 1d / usersCount;
        long averageDiskUsage = Math.round(diskUsage / usersCount);
        return new StatisticsDTO(usersCount, diskUsage, modelsCount,
                projectsCount, averageDiskUsage, averageModels, averageProjects);
    }

    /**
     * Build the chart data
     * @param request
     * @return HttpServletRequest
     */
    public static void buildChartData(HttpServletRequest request) {
        Transformer transformer = new Transformer();
        Map<String, String> stats = transformer.getStatistics(Configuration.PATH_PREFIX);
        request.setAttribute(Configuration.AVERAGE_DISK_USAGE_STATS, stats.get(Configuration.AVERAGE_DISK_USAGE_STATS));
        request.setAttribute(Configuration.AVERAGE_MODELS_STATS, stats.get(Configuration.AVERAGE_MODELS_STATS));
        request.setAttribute(Configuration.AVERAGE_PROJECTS_STATS, stats.get(Configuration.AVERAGE_PROJECTS_STATS));
        request.setAttribute(Configuration.DISK_USAGE_STATS, stats.get(Configuration.DISK_USAGE_STATS));
        request.setAttribute(Configuration.MODELS_COUNT_STATS, stats.get(Configuration.MODELS_COUNT_STATS));
        request.setAttribute(Configuration.PROJECTS_COUNT_STATS, stats.get(Configuration.PROJECTS_COUNT_STATS));
        request.setAttribute(Configuration.USERS_COUNT_STATS, stats.get(Configuration.USERS_COUNT_STATS));
        request.setAttribute(Configuration.MIN_YEAR, stats.get(Configuration.MIN_YEAR));
        request.setAttribute(Configuration.MAX_YEAR, stats.get(Configuration.MAX_YEAR));
    }
}
