package org.telosys.tools.stats.dto;

import org.telosys.tools.stats.BundleStats;

import java.io.IOException;
import java.util.Date;

/**
 * Created by alexa on 04/05/2016.
 */
public class BundleDTO {

    private String projectName;
    private String bundleName;
    private Date installationDate;
    private int generationsCount;

    public BundleDTO(String projectName, String bundleName, Date installationDate, int generationsCount) {
        this.projectName = projectName;
        this.bundleName = bundleName;
        this.installationDate = installationDate;
        this.generationsCount = generationsCount;
    }

    public static BundleDTO fromBundleStats(BundleStats stats) throws IOException {
        BundleDTO dto = new BundleDTO(stats.getProjectName(), stats.getBundleName(), stats.getInstallationDate(),
                stats.getGenerationsCount());
        return dto;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getBundleName() {
        return bundleName;
    }

    public void setBundleName(String bundleName) {
        this.bundleName = bundleName;
    }

    public Date getInstallationDate() {
        return installationDate;
    }

    public void setInstallationDate(Date installationDate) {
        this.installationDate = installationDate;
    }

    public int getGenerationsCount() {
        return generationsCount;
    }

    public void setGenerationsCount(int generationsCount) {
        this.generationsCount = generationsCount;
    }
}
