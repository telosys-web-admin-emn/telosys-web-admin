package org.telosys.tools.stats.dto;

import org.telosys.tools.stats.ProjectStats;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by alexa on 04/05/2016.
 */
public class ProjectStatsDTO {

    private Map<String, Integer> fileTypes;
    private String projectName;
    private List<String> bundlesNames;
    private int modelsCount;
    private int bundlesCount;
    private List<String> modelsNames;
    private String diskUsageMB;
    private String creationDate;
    private String lastGenerationDate;
    private int generationsCount;


    public ProjectStatsDTO(Map<String, Integer> fileTypes, String projectName, List<String> bundlesNames,
                           int modelsCount, List<String> modelsNames, String diskUsageMB, int bundlesCount, String creationDate,
                           String lastGenerationDate, int generationsCount) {
        this.fileTypes = fileTypes;
        this.projectName = projectName;
        this.bundlesNames = bundlesNames;
        this.modelsCount = modelsCount;
        this.modelsNames = modelsNames;
        this.diskUsageMB = diskUsageMB;
        this.bundlesCount = bundlesCount;
        this.creationDate = creationDate;
        this.lastGenerationDate = lastGenerationDate;
        this.generationsCount = generationsCount;
    }

    public static ProjectStatsDTO fromProjectStats(ProjectStats stats, String datePattern) throws IOException {
        DateFormat sdf = new SimpleDateFormat(datePattern);
        ProjectStatsDTO dto = new ProjectStatsDTO(stats.getCountFileTypes(), stats.getProjectName(), stats.getBundlesNames(),
                stats.getModelsCount(), stats.getModelsNames(), stats.getDiskUsageMB(), stats.getBundlesCount(),
                sdf.format(stats.getCreationDate()), sdf.format(stats.getLastGenerationDate()), stats.getGenerationsCount());
        return dto;
    }

    public Map<String, Integer> getFileTypes() {
        return fileTypes;
    }

    public void setFileTypes(Map<String, Integer> fileTypes) {
        this.fileTypes = fileTypes;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<String> getBundlesNames() {
        return bundlesNames;
    }

    public void setBundlesNames(List<String> bundlesNames) {
        this.bundlesNames = bundlesNames;
    }

    public int getModelsCount() {
        return modelsCount;
    }

    public void setModelsCount(int modelsCount) {
        this.modelsCount = modelsCount;
    }

    public List<String> getModelsNames() {
        return modelsNames;
    }

    public void setModelsNames(List<String> modelsNames) {
        this.modelsNames = modelsNames;
    }

    public String getDiskUsageMB() {
        return diskUsageMB;
    }

    public void setDiskUsageMB(String diskUsageMB) {
        this.diskUsageMB = diskUsageMB;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getLastGenerationDate() {
        return lastGenerationDate;
    }

    public void setLastGenerationDate(String lastGenerationDate) {
        this.lastGenerationDate = lastGenerationDate;
    }

    public int getGenerationsCount() {
        return generationsCount;
    }

    public void setGenerationsCount(int generationsCount) {
        this.generationsCount = generationsCount;
    }

    public int getBundlesCount() {
        return bundlesCount;
    }

    public void setBundlesCount(int bundlesCount) {
        this.bundlesCount = bundlesCount;
    }
}
