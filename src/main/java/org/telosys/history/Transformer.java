package org.telosys.history;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.commons.io.FilenameUtils;
import org.telosys.web.services.StatisticsService;

import java.io.*;
import java.util.*;

/**
 * Transforms the data we have in multiple property files (representing a snapshot of statistics) into JSON strings
 */
public class Transformer {
    /**
     * Get the statistics to a JSON String format
     * @param pathPrefix the begin path to be in the telosys-web-amin working directory
     * @return Map of JSON string values
     */
    public Map<String, String> getStatistics(String pathPrefix){
        String maxYear = "";
        String minYear = "";
        // Prepare the result Map
        Map<String, String> jsData = new HashMap<>();
        // create lists for each type of data we have
        List<ChartPoint> averageDiskUsageStats = new ArrayList<>();
        List<ChartPoint> modelsCountStats = new ArrayList<>();
        List<ChartPoint> usersCountStats = new ArrayList<>();
        List<ChartPoint> diskUsageStats = new ArrayList<>();
        List<ChartPoint> projectsCountStats = new ArrayList<>();
        List<ChartPoint> averageModelsStats = new ArrayList<>();
        List<ChartPoint> averageProjectsStats = new ArrayList<>();
        // ensure the history directory exists
        Helper.checkHistoryDirectory();
        // read the history folder containg all the generated properties
        File folder = new File(pathPrefix + File.separator + Configuration.HISTORY_FOLDER_PATH);
        for (File fileEntry : folder.listFiles()) {
            // check that the file is not a directory
            if (!fileEntry.isDirectory()) {
                // get the current file path
                String filePath = folder.getPath() + "/" + fileEntry.getName();
                // get the property from the file path
                Properties prop = getProperty(filePath);
                // get the timestamp from the name of the property
                String timestamp = FilenameUtils.getBaseName(filePath);
                // if we succeeded getting the prop loaded
                if(prop != null) {
                    minYear = computeMinYear(minYear, timestamp);
                    maxYear = computeMaxYear(maxYear, timestamp);
                    // we retrieve and add each type of data to the corresponding lists
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

        // Finally, we sort the lists to be sure data are ordered by ascending date
        Collections.sort(averageDiskUsageStats);
        Collections.sort(modelsCountStats);
        Collections.sort(usersCountStats);
        Collections.sort(diskUsageStats);
        Collections.sort(projectsCountStats);
        Collections.sort(averageModelsStats);
        Collections.sort(averageProjectsStats);

        // Convert the lists to JSON strings
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        jsData.put(Configuration.AVERAGE_DISK_USAGE_STATS, "{}");
        jsData.put(Configuration.MODELS_COUNT_STATS, "{}");
        jsData.put(Configuration.USERS_COUNT_STATS, "{}");
        jsData.put(Configuration.DISK_USAGE_STATS, "{}");
        jsData.put(Configuration.PROJECTS_COUNT_STATS, "{}");
        jsData.put(Configuration.AVERAGE_MODELS_STATS, "{}");
        jsData.put(Configuration.AVERAGE_PROJECTS_STATS, "{}");
        try {
            jsData.put(Configuration.AVERAGE_DISK_USAGE_STATS, ow.writeValueAsString(averageDiskUsageStats));
            jsData.put(Configuration.MODELS_COUNT_STATS, ow.writeValueAsString(modelsCountStats));
            jsData.put(Configuration.USERS_COUNT_STATS, ow.writeValueAsString(usersCountStats));
            jsData.put(Configuration.DISK_USAGE_STATS, ow.writeValueAsString(diskUsageStats));
            jsData.put(Configuration.PROJECTS_COUNT_STATS, ow.writeValueAsString(projectsCountStats));
            jsData.put(Configuration.AVERAGE_MODELS_STATS, ow.writeValueAsString(averageModelsStats));
            jsData.put(Configuration.AVERAGE_PROJECTS_STATS, ow.writeValueAsString(averageProjectsStats));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        jsData.put(Configuration.MIN_YEAR, minYear);
        jsData.put(Configuration.MAX_YEAR, maxYear);
        return jsData;
    }



    /**
     * Keep the min year from all histories files up to date
     * @param minYear
     * @param timestamp
     * @return String the min year
     */
    protected String computeMinYear(String minYear, String timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getTimestampToDate(timestamp));
        int year = cal.get(Calendar.YEAR);
        // if min year is not defined yet or if we found a smaller one
        if(minYear.equals("") || Integer.parseInt(minYear) > year) {
            return year+"";
        } else {
            // else if we already have the min year
            return minYear;
        }
    }

    /**
     * Keep the max year from all histories files up to date
     * @param maxYear
     * @param timestamp
     * @return String the max year
     */
    protected String computeMaxYear(String maxYear, String timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getTimestampToDate(timestamp));
        int year = cal.get(Calendar.YEAR);
        // if max year is not defined yet or if we found a greater one
        if(maxYear.equals("") || Integer.parseInt(maxYear) < year) {
            return year+"";
        } else {
            // else if we already have the max year
            return maxYear;
        }
    }


    /**
     * Get the property file according to a filePath
     * @param filePath
     * @return Properties
     */
    protected Properties getProperty(String filePath) {
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
     * Transform a timestamp string to a date
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
