package org.telosys.history;

import org.telosys.web.services.StatisticsService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

/**
 * Generates snapshot(s) of all statistics we have at time t
 */
public class StatisticsSnapshotGenerator {

    /**
     * Get all counters values and store it into a property file
     */
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
            String path = Configuration.HISTORY_FOLDER_PATH + fileName;
            File file = new File(path);
            FileOutputStream fileOut = new FileOutputStream(file);
            properties.store(fileOut, "");
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generate random history files with random values for test purpose
     * @param historiesNumber
     */
    public static void generateRandomHistories(int historiesNumber) {
        for (int i = 0; i < historiesNumber; i++) {
            try {
                Properties properties = new Properties();
                properties.setProperty(StatisticsService.USERS_COUNT, getRandomInt());
                properties.setProperty(StatisticsService.PROJECTS_COUNT, getRandomInt());
                properties.setProperty(StatisticsService.MODELS_COUNT, getRandomInt());
                properties.setProperty(StatisticsService.DISK_USAGE, getRandomInt());
                properties.setProperty(StatisticsService.AVERAGE_DISK_USAGE, getRandomInt());
                properties.setProperty(StatisticsService.AVERAGE_MODELS, getRandomDouble());
                properties.setProperty(StatisticsService.AVERAGE_PROJECTS, getRandomDouble());
                String fileName = getRandomTimestamp()+".properties";
                String path = Configuration.HISTORY_FOLDER_PATH + fileName;
                File file = new File(path);
                FileOutputStream fileOut = new FileOutputStream(file);
                properties.store(fileOut, "");
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Generate a random int as a String
     * @return String
     */
    protected static String getRandomInt() {
        Random randomGenerator = new Random();
        return Integer.toString(randomGenerator.nextInt(3500));
    }

    /**
     * Generate a random timestamp as a String
     * @return String
     */
    protected static String getRandomTimestamp() {
        Random  rnd;
        Date    dt;
        long    ms;
        rnd = new Random();
        ms = Math.abs(-946771200000L + (Math.abs(rnd.nextLong()) % (70L * 365 * 24 * 60 * 60 * 1000)));

        return new Date(ms).getTime()+"";
    }

    /**
     * Generate a random double as a string
     * @return String
     */
    protected static String getRandomDouble() {
        double start = 0;
        double end = 3500;
        double random = new Random().nextDouble();
        return Double.toString(start + (random * (end - start)));
    }

}
