package org.telosys.history;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.commons.io.FilenameUtils;
import org.telosys.web.services.StatisticsService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Timestamp;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Transformer {
    public String getStatisticsToJs(String pathPrefix){
        Map<String, String> jsData = new HashMap<>();
        List<Point> averageDiskUsageData = new ArrayList<>();
        List<Point> modelsCountData = new ArrayList<>();
        List<Point> usersCountData = new ArrayList<>();
        List<Point> diskUsageData = new ArrayList<>();
        List<Point> projectsCountData = new ArrayList<>();
        List<Point> averageModelsData = new ArrayList<>();
        List<Point> averageProjectsData = new ArrayList<>();
        File folder = new File(pathPrefix + Configuration.HISTORY_FOLDER_PATH);
        for (File fileEntry : folder.listFiles()) {
            if (!fileEntry.isDirectory()) {
                String fileName = fileEntry.getName();
                String filePath = pathPrefix + Configuration.HISTORY_FOLDER_PATH +fileName;
                usersCountData.add(getUsersCountFromHistoryFile(filePath));
            }
        }
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = "";
        try {
            json = ow.writeValueAsString(jsData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * Get the usersCount value from an history file
     * @param filePath
     * @return Number
     */
    protected Point getUsersCountFromHistoryFile(String filePath) {
        int count = 0;
        Properties prop = new Properties();
        InputStream historyFile = null;
        try {
            historyFile = new FileInputStream(filePath);
            if (historyFile != null) {
                try {
                    prop.load(historyFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                count = Integer.parseInt(prop.getProperty(StatisticsService.USERS_COUNT));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(historyFile != null) {
                try{
                    historyFile.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }

            }

        }
        String timestamp = FilenameUtils.getBaseName(filePath);
        return new Point(this.getTimestampToDate(timestamp), count);

    }

    protected Date getTimestampToDate(String timestamp) {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("TRANSOFRMER RECEIVED : "+timestamp);
        Date date = new Date(Long.parseLong(timestamp));
        //DateFormat format = new SimpleDateFormat("MMddyyHHmmss");
        System.out.println("SUCCESSFUL transform : "+formatter.format(date));
        /*Date date = null;
        try {
            date = format.parse(timestamp);
            System.out.println("SUCCESSFUL transform : "+formatter.format(date));
        } catch(ParseException e) {
            return null;
        }*/

        return date;
    }

}
