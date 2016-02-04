package org.telosys.tools.helper;

import java.io.File;

import org.telosys.tools.stats.Configuration;

public class FileAccessors {
	public static String getUserDirPath(File root, String userId) {
		return root.getPath() + File.separator + userId;
	}

	public static String getProjectDirPath(File root, String userId, String projectId) {
		return getUserDirPath(root, userId) + File.separator + projectId;
	}
	
	public static String getTemplatesDirPath(File root) {
		return getTelosysDirPath(root) + File.separator + Configuration.getTemplateDir();
	}
	
	public static String getTelosysDirPath(File root) {
		return root.getAbsolutePath() + File.separator + Configuration.getTelosysDir();
	}
}
