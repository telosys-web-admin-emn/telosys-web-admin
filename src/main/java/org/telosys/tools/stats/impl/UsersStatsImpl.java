package org.telosys.tools.stats.impl;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.telosys.tools.helper.FileUnit;
import org.telosys.tools.stats.PathHelper;
import org.telosys.tools.stats.UserStats;
import org.telosys.tools.users.User;

public class UsersStatsImpl implements UserStats {
	private User user;
	private File userDir;
	private PathHelper pathHelper;

	public UsersStatsImpl(PathHelper pathHelper, User user) throws IOException, ParseException
	{
		this.user = user;
		this.pathHelper = pathHelper;
		this.userDir = pathHelper.getUserDir(user.getLogin());
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
			for (String project : this.getProjectsNames()) {
				count += pathHelper.getTelosysDir(user.getLogin(), project).listFiles(pathHelper::isModel).length;
			}
		}
		return count;
	}

	@Override
	public List<String> getModelsNames() {
		List<String> modelsNames = new ArrayList<>();
		if(this.userDir.exists()) {
			for (String project : this.getProjectsNames()) {
				File telosys = pathHelper.getTelosysDir(user.getLogin(), project);
				modelsNames.addAll(stream(telosys.listFiles(pathHelper::isModel))
						.map(pathHelper::getModelName).collect(toList()));
			}
		}
		return modelsNames;
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
	public String getDiskUsageMB() {
		NumberFormat formatter = new DecimalFormat("#0.00");
		return formatter.format(getDiskUsage() / FileUnit.MEGABYTE);
	}

	@Override
	public int getGenerationsCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Date getLastConnectionDate() {
		return this.user.getLastConnectionDate();
	}
	
}
