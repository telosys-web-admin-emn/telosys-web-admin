package org.telosys.tools.stats;

import java.io.File;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

/**
 * Created by maeln on 04/02/16.
 */
public class Configuration
{

	/*****************************
	 * CONF CONSTANTS
	 ****************************/

	public final static String TELOSYS_SAAS_LOCATION = "../telosys-saas/";

	public final static int USERS_PER_PAGE = 20;

	public final static String TELOSYS_DIR = "TelosysTools";

	public final static String MODEL_EXTENSION = ".model";

	public final static String TEMPLATE_DIR = "templates";

	/*****************************
	 * PRIVATE MEMBERS
	 ****************************/

	private String rootPath;
	private File root;

	/*****************************
	 * SINGLETON GETTER
	 ****************************/

	public static Configuration getInstance() {
		return instance;
	}

	/*****************************
	 * BUILDERS
	 ****************************/

	private static Configuration instance;

	static {
		instance = new Configuration(TELOSYS_SAAS_LOCATION);
	}

	public Configuration(String rootPath) {
		this.rootPath = rootPath;
		this.root = new File(rootPath);
	}

	/*****************************
	 * PATH GETTERS
	 ****************************/

	public File getUserDir(String user) {
		return resolve(rootPath, user);
	}

	public File getBundlesDir(String user, String project) {
		return resolve(rootPath, user, project, TELOSYS_DIR, TEMPLATE_DIR);
	}

	public File getTelosysDir(String user, String project) {
		return resolve(rootPath, user, project, TELOSYS_DIR);
	}

	public File getProjectDir(String user, String project) {
		return resolve(rootPath, user, project);
	}

	public File getModelFile(String user, String project, String model) {
		return resolve(user, project, TELOSYS_DIR, model + MODEL_EXTENSION);
	}

	public File getCsvFile() {
		return resolve(rootPath, "users.csv");
	}

	public File getRoot() {
		return root;
	}

	private File resolve(String... files) {
		return new File(stream(files).collect(joining(File.separator)));
	}

}
