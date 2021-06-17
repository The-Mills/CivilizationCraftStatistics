package statistics_tracker;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.logging.Logger;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import repository.Repository;
import repository.StatisticsRepository;
import statistics_tracker.CommandReboot.RebootError;

public class CivilizationCraftStatistics extends JavaPlugin
{
	public static CivilizationCraftStatistics plugin;
	public static Logger logger;
	public static FileConfiguration config;
	
	private Repository statisticsRepository = new StatisticsRepository();
	
	/**
	 * Fired when the plugin is enabled
	 */
	@Override
	public void onEnable()
	{	
		plugin = this;
		logger = plugin.getLogger();
		plugin.getCommand("civStatsTrackerReboot").setExecutor(new CommandReboot());
		onEnableDoesConfigExist();
	}
	
	/**
	 * Determines whether the plugin's config.yml file exists in the correct directory.
	 * If it does exist, advance to the next step of the plugin boot-up process.
	 * Else, halt plugin execution and throw an error.
	 */
	public void onEnableDoesConfigExist()
	{
		File configPath = new File(getDataFolder(), "config.yml");
		if(configPath.exists())
		{
			onEnableReadConfig();
		}
		else
		{
			CommandReboot.logError(RebootError.MISSING_CONFIG);
		}
	}
	
	public void onEnableReadConfig()
	{
		getLogger().info("./plugins/CivilizationCraftStatisticsTracker/config.yml found. "
				+ "Performing setup operations");
		
		try
		{
			statisticsRepository.openConnection(getRepositoryCredentials(), getMaterialList("tracked-mined-materials"), getMaterialList("tracked-placed-materials"), getLogger());
			getServer().getPluginManager().registerEvents(new EventListener(statisticsRepository), this);
			getLogger().info("Connection and setup process complete.");
		}
		catch(SQLException e)
		{
			getLogger().info("FATAL ERROR: Failed to connect to database. See the below exception:");
			getLogger().info(e.toString());
		}
	}
	
	/**
	 * Gets all credentials and address information needed to connect to the statistics database
	 * from the configuration file.
	 * @return - A Map whose keys are the YAML variable names and whose values are the key's values.
	 */
	public Map<String, String> getRepositoryCredentials()
	{
		Map<String, String> credentials = new HashMap<String, String>();
		credentials.put("ip", "");
		credentials.put("port", "");
		credentials.put("database-name", "");
		credentials.put("username", "");
		credentials.put("password", "");
		
		FileConfiguration configFile = this.getConfig();

		String value = "";
		for(String key : credentials.keySet())
		{
			value = configFile.getString(key);
			credentials.put(key, value == null ? "" : value);
		}
		
		return credentials;
	}
	
	/**
	 * Retrieves a list of Spigot Materials from config.yml
	 * @param configKeyName - The name of the setting in config.yml containing the Material list
	 * @return The list of Materials, with any invalid or null entries parsed out.
	 */
	public List<Material> getMaterialList(String configKeyName)
	{
		List<Material> trackedMaterials = new ArrayList<Material>();
		
		List<?> ymlInput = this.getConfig().getList(configKeyName);
		if(ymlInput != null)
			for(Object mat : ymlInput)
			{	
				try
				{
					trackedMaterials.add(Material.valueOf(mat.toString()));
				}
				catch(IllegalArgumentException e)
				{
					getLogger().info("ERROR: plugin.yml:" + configKeyName + " - Invalid material '" 
							+ mat.toString() + "' - Ignoring Entry");
				}
				catch(NullPointerException e)
				{
					getLogger().info("ERROR: plugin.yml:" + configKeyName + " - Null value in list - Ignoring Entry");
				}
			}
		getLogger().info(configKeyName + ": Found " + trackedMaterials.size() + " valid materials.");
		return trackedMaterials;
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