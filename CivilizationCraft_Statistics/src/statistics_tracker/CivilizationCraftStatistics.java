package statistics_tracker;

import java.io.File;
import java.sql.SQLException;
import java.util.Map;
import java.util.HashMap;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import repository.Repository;
import repository.StatisticsRepository;

public class CivilizationCraftStatistics extends JavaPlugin
{
	private Repository statisticsRepository;
	
	/**
	 * Fired when the plugin is enabled
	 */
	@Override
	public void onEnable()
	{	
		statisticsRepository = new StatisticsRepository(getRepositoryCredentials()); 
		getServer().getPluginManager().registerEvents(new StatisticsEventListener(statisticsRepository), this);
	}
	
	public boolean doesConfigExist()
	{
		File filePath = new File(getDataFolder(), "config.yml");
		if(!getDataFolder().exists() || !filePath.exists())
			return false;

		return true;
	}
	
	public Map<String, String> getRepositoryCredentials()
	{
		Map<String, String> credentials = new HashMap<String, String>();
		credentials.put("ip", "");
		credentials.put("port", "");
		credentials.put("database-name", "");
		credentials.put("username", "");
		credentials.put("password", "");
		
		if(doesConfigExist())
		{
			FileConfiguration configFile = this.getConfig();
			getLogger().info("./plugins/CivilizationCraftStatisticsTracker/config.yml found. "
					+ "Connecting to database using provided credentials.");
			
			String value = "";
			for(String key : credentials.keySet())
			{
				value = configFile.getString(key);
				credentials.put(key, value == null ? "" : value);
			}
		}
		else
			getLogger().info("ERROR: ./plugins/CivilizationCraftStatisticsTracker/config.yml not found. "
					+ "Plugin will be unable to connect to the database!");
		
		for(String key : credentials.keySet())
			System.out.println(key + ": '" + credentials.get(key) +"'");
		
		return credentials;
	}
	
	/**
	 * Fired when the plugin is disabled.
	 */
	@Override
	public void onDisable()
	{
		try
		{
			statisticsRepository.closeConnection();
		}
		catch(NullPointerException e) {System.out.println(e);}
		catch(SQLException e)
		{
			// Handle something like this appropriately. To be implemented.
			System.out.println(e);
		}
	}
}