package org.telosys.tools.stats.dto;

import org.telosys.tools.stats.BundleStats;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by alexa on 04/05/2016.
 */
public class BundleStatsDTO {

    private String projectName;
    private String bundleName;
    private String installationDate;
    private int generationsCount;

    public BundleStatsDTO(String projectName, String bundleName, String installationDate, int generationsCount) {
        this.projectName = projectName;
        this.bundleName = bundleName;
        this.installationDate = installationDate;
        this.generationsCount = generationsCount;
    }

    public static BundleStatsDTO fromBundleStats(BundleStats stats, String datePattern) throws IOException {
        DateFormat sdf = new SimpleDateFormat(datePattern);
        BundleStatsDTO dto = new BundleStatsDTO(stats.getProjectName(), stats.getBundleName(), sdf.format(stats.getInstallationDate()),
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

    public String getInstallationDate() {
        return installationDate;
    }

    public void setInstallationDate(String installationDate) {
        this.installationDate = installationDate;
    }

    public int getGenerationsCount() {
        return generationsCount;
    }

    public void setGenerationsCount(int generationsCount) {
        this.generationsCount = generationsCount;
    }
}
