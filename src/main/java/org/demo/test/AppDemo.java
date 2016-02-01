package org.demo.test;

import java.io.File;
import java.util.List;

import org.telosys.tools.stats.BundleStats;
import org.telosys.tools.stats.FilesystemStatsOverview;
import org.telosys.tools.stats.ModelStats;
import org.telosys.tools.stats.ProjectStats;
import org.telosys.tools.stats.StatsProvider;
import org.telosys.tools.stats.StatsProviderFactory;
import org.telosys.tools.stats.UserStats;

public class AppDemo {

	public static void main(String[] args) {
		
		StatsProvider statsProvider = StatsProviderFactory.getStatsProvider() ;

		File root = statsProvider.getRoot();
		
		FilesystemStatsOverview filesystemStatsOverview = statsProvider.getFilesystemStatsOverview();
		
		//--- User 
		String userId = "toto";
		UserStats userStats = statsProvider.getUserStats(userId);
		
		//--- Projects stats
		for ( String projectName : userStats.getProjectsNames() ) {
			ProjectStats projectStats = statsProvider.getProjectStats(userId, projectName);
			
			//--- Models stats
			for ( String modelName : projectStats.getModelsNames() ) {
				ModelStats modelStats = statsProvider.getModelStats(userId, projectStats.getProjectName(), modelName);
			}
			
			//--- Bundles stats
			for ( String bundleName : projectStats.getBundlesNames() ) {
				BundleStats bundleStats = statsProvider.getBundleStats(userId, projectStats.getProjectName(), bundleName);
			}
		}

		//--- All stats for a user :
		List<ProjectStats> projectsStats = statsProvider.getProjectsStats(userId);
		List<ModelStats>   modelsStats   = statsProvider.getModelsStats(userId);
		List<BundleStats>  bundlesStats  = statsProvider.getBundlesStats(userId);
		
		//--- Specific stats
		ProjectStats projectStats = statsProvider.getProjectStats("toto", "project1");
		
		ModelStats modelStats = statsProvider.getModelStats("toto", "project1", "model1");
		
		BundleStats bundleStats = statsProvider.getBundleStats("toto", "project1", "bundle2");
	}

}
