package org.telosys.history;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.commons.io.FilenameUtils;
import org.telosys.web.services.StatisticsService;

import java.io.*;
import java.util.*;

public class Transformer {
    public Map<String, String> getStatistics(String pathPrefix){
        Map<String, String> jsData = new HashMap<>();
        List<ChartPoint> averageDiskUsageStats = new ArrayList<>();
        List<ChartPoint> modelsCountStats = new ArrayList<>();
        List<ChartPoint> usersCountStats = new ArrayList<>();
        List<ChartPoint> diskUsageStats = new ArrayList<>();
        List<ChartPoint> projectsCountStats = new ArrayList<>();
        List<ChartPoint> averageModelsStats = new ArrayList<>();
        List<ChartPoint> averageProjectsStats = new ArrayList<>();
        File folder = new File(pathPrefix + Configuration.HISTORY_FOLDER_PATH);
        for (File fileEntry : folder.listFiles()) {
            if (!fileEntry.isDirectory()) {
                String fileName = fileEntry.getName();
                // todo : folder.getPath
                String filePath = pathPrefix + Configuration.HISTORY_FOLDER_PATH +fileName;
                Properties prop = getProperty(filePath);
                String timestamp = FilenameUtils.getBaseName(filePath);
                if(prop != null) {
                    averageDiskUsageStats.add(getAverageDiskUsage(timestamp, prop));
                    modelsCountStats.add(getModelsCount(timestamp, prop));
                    usersCountStats.add(getUsersCount(timestamp, prop));
                    diskUsageStats.add(getDiskUsage(timestamp, prop));
                    projectsCountStats.add(getProjectsCount(timestamp, prop));
                    averageModelsStats.add(getAverageModels(timestamp, prop));
                    averageProjectsStats.add(getAverageProjects(timestamp, prop));
                }
            }
        }
        Collections.sort(averageDiskUsageStats);
        Collections.sort(modelsCountStats);
        Collections.sort(usersCountStats);
        Collections.sort(diskUsageStats);
        Collections.sort(projectsCountStats);
        Collections.sort(averageModelsStats);
        Collections.sort(averageProjectsStats);
        Map<String, String> res = new HashMap<>();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            res.put(Configuration.AVERAGE_DISK_USAGE_STATS, ow.writeValueAsString(averageDiskUsageStats));
            res.put(Configuration.MODELS_COUNT_STATS, ow.writeValueAsString(modelsCountStats));
            res.put(Configuration.USERS_COUNT_STATS, ow.writeValueAsString(usersCountStats));
            res.put(Configuration.DISK_USAGE_STATS, ow.writeValueAsString(diskUsageStats));
            res.put(Configuration.PROJECTS_COUNT_STATS, ow.writeValueAsString(projectsCountStats));
            res.put(Configuration.AVERAGE_MODELS_STATS, ow.writeValueAsString(averageModelsStats));
            res.put(Configuration.AVERAGE_PROJECTS_STATS, ow.writeValueAsString(averageProjectsStats));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Get the property file according to a filePath
     * @param filePath
     * @return Properties
     */
    protected Properties getProperty(String filePath) {
        int count = 0;
        Properties prop = new Properties();
        InputStream historyFile = null;
        try {
            historyFile = new FileInputStream(filePath);
            if (historyFile != null) {
                try {
                    prop.load(historyFile);
                    try {
                        historyFile.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return prop;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Transform timestamp string to a date
     * @param timestamp
     * @return Date
     */
    protected Date getTimestampToDate(String timestamp) {
        return new Date(Long.parseLong(timestamp));
    }

    /**
     * Create a chart point representing the number of users
     * @param timestamp
     * @param prop
     * @return ChartPoint
     */
    protected ChartPoint getUsersCount(String timestamp, Properties prop) {
        return new ChartPoint(this.getTimestampToDate(timestamp), Integer.parseInt(prop.getProperty(StatisticsService.USERS_COUNT)));
    }

    /**
     * Create a chart point representing the average disk usage
     * @param timestamp
     * @param prop
     * @return ChartPoint
     */
    protected ChartPoint getAverageDiskUsage(String timestamp, Properties prop) {
        return new ChartPoint(this.getTimestampToDate(timestamp), Integer.parseInt(prop.getProperty(StatisticsService.AVERAGE_DISK_USAGE)));
    }

    /**
     * Create a chart point representing the number of models
     * @param timestamp
     * @param prop
     * @return ChartPoint
     */
    protected ChartPoint getModelsCount(String timestamp, Properties prop) {
        return new ChartPoint(this.getTimestampToDate(timestamp), Integer.parseInt(prop.getProperty(StatisticsService.MODELS_COUNT)));
    }

    /**
     * Create a chart point representing the number of projects
     * @param timestamp
     * @param prop
     * @return ChartPoint
     */
    protected ChartPoint getProjectsCount(String timestamp, Properties prop) {
        return new ChartPoint(this.getTimestampToDate(timestamp), Integer.parseInt(prop.getProperty(StatisticsService.PROJECTS_COUNT)));
    }

    /**
     * Create a chart point representing the average number of models
     * @param timestamp
     * @param prop
     * @return ChartPoint
     */
    protected ChartPoint getAverageModels(String timestamp, Properties prop) {
        return new ChartPoint(this.getTimestampToDate(timestamp), Double.parseDouble(prop.getProperty(StatisticsService.AVERAGE_MODELS)));
    }

    /**
     * Create a chart point representing the average number of projects
     * @param timestamp
     * @param prop
     * @return ChartPoint
     */
    protected ChartPoint getAverageProjects(String timestamp, Properties prop) {
        return new ChartPoint(this.getTimestampToDate(timestamp), Double.parseDouble(prop.getProperty(StatisticsService.AVERAGE_PROJECTS)));
    }

    /**
     * Create a chart point representing the disk usage
     * @param timestamp
     * @param prop
     * @return ChartPoint
     */
    protected ChartPoint getDiskUsage(String timestamp, Properties prop) {
        return new ChartPoint(this.getTimestampToDate(timestamp), Integer.parseInt(prop.getProperty(StatisticsService.DISK_USAGE)));
    }
}
