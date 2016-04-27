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

    /*****************************
     * PRIVATE MEMBERS
     ****************************/

    private Properties properties;

    private String telosysFsLocation;
    private int usersPerPage;
    private String modelExtension;
    private String templatesDir;
    private String telosysDir;


    public Configuration(InputStream fis) throws IOException {
        this.properties = new Properties();
        properties.load(fis);
        this.telosysFsLocation = properties.getProperty(TELOSYS_FS_LOCATION);
        this.usersPerPage = Integer.parseInt(properties.getProperty(USERS_PER_PAGE));
        this.modelExtension = properties.getProperty(MODEL_EXTENSION);
        this.templatesDir = properties.getProperty(TEMPLATES_DIR);
        this.telosysDir = properties.getProperty(TELOSYS_DIR);
        this.viewDateFormat = properties.getProperty(VIEW_DATE_FORMAT);
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

    public String getViewDateFormat(){
    	return viewDateFormat;
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
}
