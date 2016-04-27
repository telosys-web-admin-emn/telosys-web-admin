package org.telosys.tools.stats;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

/**
 * Created by maeln on 04/02/16.
 */
public class PathHelper
{
	/*****************************
	 * PRIVATE MEMBERS
	 ****************************/

	private String rootPath;
	private File root;

	private Configuration conf;

	/*****************************
	 * SINGLETON GETTER
	 ****************************/

	public static PathHelper getInstance() {
		return instance;
	}

	/*****************************
	 * BUILDERS
	 ****************************/

	private static PathHelper instance;

	static {
		try {
			InputStream is = PathHelper.class.getResourceAsStream("/META-INF/webadmin.properties");
			Configuration conf = new Configuration(is);
			instance = new PathHelper(conf);
		} catch(IOException e) {
			System.err.println("Failed to parse webadmin.properties in META-INF");
			e.printStackTrace();
		}
	}

	public PathHelper(Configuration configuration) {
		this.conf = configuration;
		this.rootPath = configuration.getTelosysFsLocation();
		this.root = new File(rootPath);
	}

	/*****************************
	 * PATH GETTERS
	 ****************************/

	public File getUserDir(String user) {
		return resolve(rootPath, user);
	}

	public File getBundlesDir(String user, String project) {
		return resolve(rootPath, user, project, conf.getTelosysDir(), conf.getTemplatesDir());
	}

	public File getTelosysDir(String user, String project) {
		return resolve(rootPath, user, project, conf.getTelosysDir());
	}

	public File getProjectDir(String user, String project) {
		return resolve(rootPath, user, project);
	}

	public File getModelFile(String user, String project, String model) {
		return resolve(rootPath, user, project, conf.getTelosysDir(), model + conf.getModelExtension());
	}

	public int getUsersPerPage() {
		return conf.getUsersPerPage();
	}

	public String getModelExtension() {
		return conf.getModelExtension();
	}

	public File getCsvFile() {
		return resolve(rootPath, "users.csv");
	}

	public File getRoot() {
		return root;
	}

	public boolean isModel(File file) {
		return file.getName().endsWith(conf.getModelExtension());
	}

	public String getModelName(File modelFile) {
		return modelFile.getName().replaceAll(conf.getModelExtension(), "");
	}

	private File resolve(String... files) {
		return new File(stream(files).collect(joining(File.separator)));
	}
	
	public String getViewDateFormat(){
		return conf.getViewDateFormat();
	}


}
