package org.telosys.tools.stats.exception;

/**
 * Created by Alexandre on 04/02/2016.
 */
public class ProjectNotFoundException extends Exception {

    private String projectId;

    public ProjectNotFoundException(String projectId) {
        super(projectId);
        this.projectId = projectId;
    }
}
