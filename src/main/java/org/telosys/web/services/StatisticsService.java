package org.telosys.web.services;

import org.telosys.history.Configuration;
import org.telosys.history.Transformer;
import org.telosys.tools.helper.FileUnit;
import org.telosys.tools.stats.FilesystemStatsOverview;
import org.telosys.tools.stats.StatsProviderFactory;

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
    public static HttpServletRequest buildCounts(HttpServletRequest request) {
        Map<String, Number> counts = computeCounts();
        request.setAttribute(StatisticsService.USERS_COUNT, counts.get(USERS_COUNT));
        request.setAttribute(StatisticsService.PROJECTS_COUNT, counts.get(PROJECTS_COUNT));
        request.setAttribute(StatisticsService.MODELS_COUNT, counts.get(MODELS_COUNT));
        request.setAttribute(StatisticsService.DISK_USAGE, counts.get(DISK_USAGE));

        request.setAttribute(StatisticsService.AVERAGE_PROJECTS, counts.get(AVERAGE_PROJECTS));
        request.setAttribute(StatisticsService.AVERAGE_MODELS, counts.get(AVERAGE_MODELS));
        request.setAttribute(StatisticsService.AVERAGE_DISK_USAGE, counts.get(AVERAGE_DISK_USAGE));
        return request;
    }

    /**
     * Compute the counts
     * @return Map containing the counts
     */
    public static Map<String, Number> computeCounts() {
        FilesystemStatsOverview statsOverview = StatsProviderFactory.getStatsProvider().getFilesystemStatsOverview();
        HashMap<String, Number> counts = new HashMap<>();
        int usersCount = 0;
        int projectsCount = 0;
        int modelsCount = 0;
        try {
            //Get statistics from stats overview class
            usersCount = statsOverview.getUsersCount();
            projectsCount = statsOverview.getProjectsCount();
            modelsCount = statsOverview.getModelsCount();
        } catch(ParseException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
        long diskUsage = Math.round(statsOverview.getDiskUsage()/ FileUnit.MEGABYTE);
        //Need to multiply by 1.0 to cast in double ) have a double result
        double averageProjects = projectsCount * 1.0 / usersCount;
        double averageModels = modelsCount * 1.0 / usersCount;
        long averageDiskUsage = Math.round(diskUsage / usersCount);
        counts.put(USERS_COUNT, usersCount);
        counts.put(PROJECTS_COUNT, projectsCount);
        counts.put(MODELS_COUNT, modelsCount);
        counts.put(DISK_USAGE, diskUsage);
        counts.put(AVERAGE_DISK_USAGE, averageDiskUsage);
        counts.put(AVERAGE_PROJECTS, averageProjects);
        counts.put(AVERAGE_MODELS, averageModels);

        return counts;
    }

    /**
     * Build the chart data
     * @param request
     * @return HttpServletRequest
     */
    public static HttpServletRequest buildChartData(HttpServletRequest request) {
        Transformer transformer = new Transformer();
        Map<String, String> stats = transformer.getStatistics("../../telosys-web-admin/");
        request.setAttribute(Configuration.AVERAGE_DISK_USAGE_STATS, stats.get(Configuration.AVERAGE_DISK_USAGE_STATS));
        request.setAttribute(Configuration.AVERAGE_MODELS_STATS, stats.get(Configuration.AVERAGE_MODELS_STATS));
        request.setAttribute(Configuration.AVERAGE_PROJECTS_STATS, stats.get(Configuration.AVERAGE_PROJECTS_STATS));
        request.setAttribute(Configuration.DISK_USAGE_STATS, stats.get(Configuration.DISK_USAGE_STATS));
        request.setAttribute(Configuration.MODELS_COUNT_STATS, stats.get(Configuration.MODELS_COUNT_STATS));
        request.setAttribute(Configuration.PROJECTS_COUNT_STATS, stats.get(Configuration.PROJECTS_COUNT_STATS));
        request.setAttribute(Configuration.USERS_COUNT_STATS, stats.get(Configuration.USERS_COUNT_STATS));
        request.setAttribute(Configuration.MIN_YEAR, stats.get(Configuration.MIN_YEAR));
        request.setAttribute(Configuration.MAX_YEAR, stats.get(Configuration.MAX_YEAR));
        return request;
    }
}
