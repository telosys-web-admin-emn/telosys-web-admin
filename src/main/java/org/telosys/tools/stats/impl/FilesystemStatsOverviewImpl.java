package org.telosys.tools.stats.impl;

import org.telosys.tools.helper.CSVReader;
import org.telosys.tools.stats.Configuration;
import org.telosys.tools.stats.FilesystemStatsOverview;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FilesystemStatsOverviewImpl implements FilesystemStatsOverview  {
	private File root;

	public FilesystemStatsOverviewImpl(File root)
	{
		this.root = root;
	}

	@Override
	public int getUsersCount() {
		FileReader user_file = new FileReader(this.root.getAbsolutePath()+'/fs/users.csv');
		BufferedReader buffer = new BufferedReader(user_file);
		StringBuffer res = new StringBuffer();

		try
		{
			for(String line = buffer.readLine(); line != null; line = buffer.readLine())
			{
				res.append(line + "\n");
			}
			CSVReader csv = new CSVReader(res.toString());
			return csv.numberOfLines();
		}
		catch (IOException e)
		{
			return -1;
		}
	}

	@Override
	public int getProjectsCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getModelsCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getDiskUsage() {
		// TODO Auto-generated method stub
		return 0;
	}

}
