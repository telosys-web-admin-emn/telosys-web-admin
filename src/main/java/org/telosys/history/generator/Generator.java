package org.telosys.history.generator;

import org.telosys.tools.stats.FilesystemStatsOverview;
import org.telosys.tools.stats.StatsProviderFactory;
import org.telosys.web.services.StatisticsService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import static org.telosys.history.Configuration.HISTORY_FILE_PATH;

public class Generator {
    public static void generateHistory(){
        Map<String, Number> counts = StatisticsService.computeCounts();
        try {
            Properties properties = new Properties();
            properties.setProperty(StatisticsService.USERS_COUNT, counts.get(StatisticsService.USERS_COUNT).toString());
            properties.setProperty(StatisticsService.PROJECTS_COUNT, counts.get(StatisticsService.PROJECTS_COUNT).toString());
            properties.setProperty(StatisticsService.MODELS_COUNT, counts.get(StatisticsService.MODELS_COUNT).toString());
            properties.setProperty(StatisticsService.DISK_USAGE, counts.get(StatisticsService.DISK_USAGE).toString());
            properties.setProperty(StatisticsService.AVERAGE_DISK_USAGE, counts.get(StatisticsService.AVERAGE_DISK_USAGE).toString());
            properties.setProperty(StatisticsService.AVERAGE_MODELS, counts.get(StatisticsService.AVERAGE_MODELS).toString());
            properties.setProperty(StatisticsService.AVERAGE_PROJECTS, counts.get(StatisticsService.AVERAGE_PROJECTS).toString());
            String fileName = new Date().getTime()+".properties";
            String path = HISTORY_FILE_PATH + fileName;
            File file = new File(path);
            FileOutputStream fileOut = new FileOutputStream(file);
            properties.store(fileOut, "");
            fileOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
