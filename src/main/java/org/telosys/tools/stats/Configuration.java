package org.telosys.tools.stats;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Configuration {

    public final static String TELOSYS_FS_LOCATION = "telosys_fs_location";

    public final static String VIEW_DATE_FORMAT = "view_date_format";

    public final static String USERS_PER_PAGE = "users_per_page";

    public final static String TELOSYS_DIR = "telosys_dir";

    public final static String MODEL_EXTENSION = "model_extension";

    public final static String TEMPLATES_DIR = "templates_dir";

    public final static String DISK_USAGE_QUOTA = "disk_usage_quota";

    public final static String GENERATIONS_FILENAME = "generations_filename";

    public final static String TELOSYS_STATS_FOLDER = "telosys_stats_folder";

    /*****************************
     * PRIVATE MEMBERS
     ****************************/

    private Properties properties;

    private String telosysFsLocation;
    private int usersPerPage;
    private String modelExtension;
    private String templatesDir;
    private String telosysDir;
    private String viewDateFormat;
    private String generationsFilename;
    private String telosysStatsFolder;
    private long diskUsageQuota;


    public Configuration(InputStream fis) throws IOException {
        this.properties = new Properties();
        properties.load(fis);
        this.telosysFsLocation = properties.getProperty(TELOSYS_FS_LOCATION);
        this.usersPerPage = Integer.parseInt(properties.getProperty(USERS_PER_PAGE));
        this.modelExtension = properties.getProperty(MODEL_EXTENSION);
        this.templatesDir = properties.getProperty(TEMPLATES_DIR);
        this.telosysDir = properties.getProperty(TELOSYS_DIR);
        this.viewDateFormat = properties.getProperty(VIEW_DATE_FORMAT);
        this.diskUsageQuota = Long.parseLong(properties.getProperty(DISK_USAGE_QUOTA));
        this.generationsFilename = properties.getProperty(GENERATIONS_FILENAME);
        this.telosysStatsFolder = properties.getProperty(TELOSYS_STATS_FOLDER);
    }

    // for tests
    public Configuration() {
    }

    public String getTelosysFsLocation() {
        return telosysFsLocation;
    }

    public int getUsersPerPage() {
        return usersPerPage;
    }

    public String getModelExtension() {
        return modelExtension;
    }

    public String getTemplatesDir() {
        return templatesDir;
    }

    public String getTelosysDir() {
        return telosysDir;
    }

    public String getGenerationsFilename() {
        return generationsFilename;
    }

    public void setGenerationsFilename(String generationsFilename) {
        this.generationsFilename = generationsFilename;
    }

    public void setTelosysFsLocation(String telosysFsLocation) {
        this.telosysFsLocation = telosysFsLocation;
    }

    public void setUsersPerPage(int usersPerPage) {
        this.usersPerPage = usersPerPage;
    }

    public void setModelExtension(String modelExtension) {
        this.modelExtension = modelExtension;
    }

    public void setTemplatesDir(String templatesDir) {
        this.templatesDir = templatesDir;
    }

    public void setTelosysDir(String telosysDir) {
        this.telosysDir = telosysDir;
    }

    public String getViewDateFormat(){
    	return viewDateFormat;
    }

    public long getDiskUsageQuota() { return diskUsageQuota; }

    public String getTelosysStatsFolder() {
        return telosysStatsFolder;
    }

    public void setTelosysStatsFolder(String telosysStatsFolder) {
        this.telosysStatsFolder = telosysStatsFolder;
    }
}
