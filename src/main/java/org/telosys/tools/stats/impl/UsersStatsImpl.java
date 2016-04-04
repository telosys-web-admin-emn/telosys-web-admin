package org.telosys.tools.stats.impl;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.telosys.tools.stats.UserStats;
import org.telosys.tools.users.User;

public class UsersStatsImpl implements UserStats {
	private User user;
	private File root;
	private File userDir;

	public UsersStatsImpl(User user, File root) throws IOException, ParseException
	{
		this.user = user;
		this.root = root;
		this.userDir = new File(this.getUserDirPath());
	}

	@Override
	public String getLogin() {
		return this.user.getLogin();
	}

	@Override
	public String getMail() {
		return this.user.getMail();
	}

	@Override
	public Date getCreationDate() {
		return this.user.getCreationDate();
	}

	@Override
	public String getCountry() {
		return this.user.getCountry();
	}

	@Override
	public String getLanguage() {
		return this.user.getLanguage();
	}

	@Override
	public int getProjectsCount() {
		if (this.userDir.exists()) {
			return this.userDir.listFiles(File::isDirectory).length;
		}
		return 0;
	}

	@Override
	public List<String> getProjectsNames() {
		if (this.userDir.exists()) {
			return asList(this.userDir.listFiles())
				.stream()
				.map(File::getName)
				.collect(toList());
		}
		return new ArrayList<String>();
	}

	@Override
	public int getModelsCount() {
		int count = 0;
		if (this.userDir.exists()) {
			for(File project:this.userDir.listFiles()){
				File telosysToolsDir = new File(project.getAbsolutePath() + "/TelosysTools");
				if (telosysToolsDir.isDirectory()) {
					count += telosysToolsDir.listFiles(f -> f.getName().toLowerCase().endsWith(".model")).length;
				}			
			}
		}
		return count;
	}

	@Override
	public int getBundlesCount() {
		int count = 0;
		if (this.userDir.exists()) {
			for(File project:this.userDir.listFiles()){
				File templatesDir = new File(project.getAbsolutePath() + "/TelosysTools/templates");
				count += templatesDir.listFiles(File::isDirectory).length;
				
			}
		}
		return count;
	}

	@Override
	public List<String> getBundlesNames() {
		List<String> bundlesNames = new ArrayList<String>();
		if (this.userDir.exists()) {
			for(File project:this.userDir.listFiles()){
				File templatesDir = new File(project.getAbsolutePath() + "/TelosysTools/templates");
				bundlesNames.addAll(asList(templatesDir.listFiles(File::isDirectory))
						.stream()
						.map(File::getName)
						.collect(toList()));
			}
		}
		return bundlesNames;
	}

	@Override
	public long getDiskUsage() {
		if (this.userDir.exists()) {
			return FileUtils.sizeOfDirectory(this.userDir);
		}
		return 0;
		
	}

	@Override
	public int getGenerationsCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * Get the path of the user
	 * @return string
	 */
	private String getUserDirPath() {
		// TODO : check if the path exists
		return this.root.getAbsolutePath() + File.separator + this.user.getLogin();
	}

}
