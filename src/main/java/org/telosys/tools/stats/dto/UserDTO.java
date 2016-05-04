package org.telosys.tools.stats.dto;

import org.telosys.tools.stats.UserStats;

import java.util.Date;
import java.util.List;

/**
 * Created by alexa on 04/05/2016.
 */
public class UserDTO {


    private String login;
    private String mail;
    private Date creationDate;
    private Date lastConnectionDate;
    private String country;
    private String language;
    private int projectsCount;
    private int modelsCount;
    private List<String> modelsNames;
    private int bundlesCount;
    private List<String> bundlesNames;
    private String diskUsageMB;
    private int generationsCount;

    public UserDTO(String login, String mail, Date lastConnectionDate, String country, String language, int projectsCount,
                   int modelsCount, List<String> modelsNames, Date creationDate, int bundlesCount, List<String> bundlesNames,
                   String diskUsageMB,
                   int generationsCount) {
        this.login = login;
        this.mail = mail;
        this.lastConnectionDate = lastConnectionDate;
        this.country = country;
        this.language = language;
        this.projectsCount = projectsCount;
        this.modelsCount = modelsCount;
        this.modelsNames = modelsNames;
        this.creationDate = creationDate;
        this.bundlesCount = bundlesCount;
        this.bundlesNames = bundlesNames;
        this.diskUsageMB = diskUsageMB;
        this.generationsCount = generationsCount;
    }

    public static UserDTO fromUserStats(UserStats stats) {
        UserDTO dto = new UserDTO(stats.getLogin(), stats.getMail(), stats.getLastConnectionDate(), stats.getCountry(),
                stats.getLanguage(), stats.getProjectsCount(), stats.getModelsCount(), stats.getModelsNames(),
                stats.getCreationDate(), stats.getBundlesCount(), stats.getBundlesNames(), stats.getDiskUsageMB(),
                stats.getGenerationsCount());
        return dto;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Date getLastConnectionDate() {
        return lastConnectionDate;
    }

    public void setLastConnectionDate(Date lastConnectionDate) {
        this.lastConnectionDate = lastConnectionDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getProjectsCount() {
        return projectsCount;
    }

    public void setProjectsCount(int projectsCount) {
        this.projectsCount = projectsCount;
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

    public int getBundlesCount() {
        return bundlesCount;
    }

    public void setBundlesCount(int bundlesCount) {
        this.bundlesCount = bundlesCount;
    }

    public List<String> getBundlesNames() {
        return bundlesNames;
    }

    public void setBundlesNames(List<String> bundlesNames) {
        this.bundlesNames = bundlesNames;
    }

    public String getDiskUsageMB() {
        return diskUsageMB;
    }

    public void setDiskUsageMB(String diskUsageMB) {
        this.diskUsageMB = diskUsageMB;
    }

    public int getGenerationsCount() {
        return generationsCount;
    }

    public void setGenerationsCount(int generationsCount) {
        this.generationsCount = generationsCount;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
