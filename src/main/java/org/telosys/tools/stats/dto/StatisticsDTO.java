package org.telosys.tools.stats.dto;

import org.telosys.tools.helper.FileUnit;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by alexa on 11/05/2016.
 */
public class StatisticsDTO {

    private String usersCount;
    private String diskUsage;
    private String modelsCount;
    private String projectsCount;
    private String averageDiskUsageMB;
    private String averageModels;
    private String averageProjects;

    public StatisticsDTO(int usersCount, long diskUsage, int modelsCount, int projectsCount,
                         double averageDiskUsage, double averageModels, double averageProjects) {
        this.usersCount = "" + usersCount;
        this.diskUsage = "" + diskUsage;
        this.modelsCount = "" + modelsCount;
        this.projectsCount = "" + projectsCount;
        NumberFormat formatter = new DecimalFormat("#0.00");
        this.averageDiskUsageMB = formatter.format(averageDiskUsage);
        this.averageModels = formatter.format(averageModels);
        this.averageProjects = formatter.format(averageProjects);
    }

    public String getAverageDiskUsageMB() {
        return averageDiskUsageMB;
    }

    public void setAverageDiskUsageMB(String averageDiskUsageMB) {
        this.averageDiskUsageMB = averageDiskUsageMB;
    }

    public String getAverageModels() {
        return averageModels;
    }

    public void setAverageModels(String averageModels) {
        this.averageModels = averageModels;
    }

    public String getAverageProjects() {
        return averageProjects;
    }

    public void setAverageProjects(String averageProjects) {
        this.averageProjects = averageProjects;
    }

    public String getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(String usersCount) {
        this.usersCount = usersCount;
    }

    public String getDiskUsage() {
        return diskUsage;
    }

    public void setDiskUsage(String diskUsage) {
        this.diskUsage = diskUsage;
    }

    public String getModelsCount() {
        return modelsCount;
    }

    public void setModelsCount(String modelsCount) {
        this.modelsCount = modelsCount;
    }

    public String getProjectsCount() {
        return projectsCount;
    }

    public void setProjectsCount(String projectsCount) {
        this.projectsCount = projectsCount;
    }
}
