package org.telosys.tools.stats.exception;

public class ProjectNotFoundException extends Exception {

    private String projectId;

    public ProjectNotFoundException(String projectId) {
        super(projectId);
        this.projectId = projectId;
    }
}
