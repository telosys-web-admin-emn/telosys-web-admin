package org.telosys.tools.stats.dto;

import org.telosys.tools.stats.ModelStats;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ModelStatsDTO {

    private String projectName;
    private String modelName;
    private String lastModifiedDate;
    private String creationDate;


    public ModelStatsDTO(String projectName, String modelName, String lastModifiedDate, String creationDate) {
        this.projectName = projectName;
        this.modelName = modelName;
        this.lastModifiedDate = lastModifiedDate;
        this.creationDate = creationDate;
    }

    public static ModelStatsDTO fromModelStats(ModelStats stats, String datePattern) throws IOException {
        DateFormat sdf = new SimpleDateFormat(datePattern);
        ModelStatsDTO dto = new ModelStatsDTO(stats.getProjectName(), stats.getModelName(), sdf.format(stats.getLastModifiedDate()),
                sdf.format(stats.getCreationDate()));
        return dto;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
