package org.telosys.tools.stats.dto;

import org.telosys.tools.stats.ModelStats;

import java.io.IOException;
import java.util.Date;

/**
 * Created by alexa on 04/05/2016.
 */
public class ModelDTO {

    private String projectName;
    private String modelName;
    private Date lastModifiedDate;
    private Date creationDate;


    public ModelDTO(String projectName, String modelName, Date lastModifiedDate, Date creationDate) {
        this.projectName = projectName;
        this.modelName = modelName;
        this.lastModifiedDate = lastModifiedDate;
        this.creationDate = creationDate;
    }

    public static ModelDTO fromModelStats(ModelStats stats) throws IOException {
        ModelDTO dto = new ModelDTO(stats.getProjectName(), stats.getModelName(), stats.getLastModifiedDate(),
                stats.getCreationDate());
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

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
