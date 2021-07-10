package repository;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;

import statistics_tracker.CivilizationCraftStatistics;

public class ConfigurationHandler 
{
	private FileConfiguration config;
	
	public boolean doesConfigExist()
	{
		File configPath = new File(CivilizationCraftStatistics.plugin.getDataFolder(), "config.yml");
		if(configPath.exists())
		{
			config = CivilizationCraftStatistics.plugin.getConfig();
			return true;
		}
		else
			return false;
	}
}