package statistics_tracker;

import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import repository.StatsDatabase;

public class CivilizationCraftStatistics extends JavaPlugin
{
	public static CivilizationCraftStatistics plugin;
	public static Logger logger;
	public static StatsDatabase database;
	
	/**
	 * Fired when the plugin is enabled.
	 * Initializes all parts of the plugin that are independent of the database connection,
	 * then calls the method that begins the database connection process.
	 */
	@Override
	public void onEnable()
	{	
		plugin = this;
		logger = plugin.getLogger();
		database = new StatsDatabase(Bukkit.getServer().getScheduler());
		plugin.getCommand(CommandCivStatsAdmin.NAME).setExecutor(new CommandCivStatsAdmin());
		
		database.runTaskOpenOrRepairDatabaseAsynchronously(null);
	}
	
	/**
	 * Fired when the plugin is disabled.
	 */
	@Override
	public void onDisable()
	{}	
	
	
	/**
	 * First method that's part of the chain of establishing database connectivity.
	 * 
	 * Determines whether the plugin's config.yml file exists in the correct directory.
	 * If it does exist, advance to the next step of the plugin boot-up process.
	 * Else, halt plugin execution and throw an error.
	 */
	/*
	public static void onEnableDoesConfigExist()
	{
		File configPath = new File(plugin.getDataFolder(), "config.yml");
		if(configPath.exists())
			System.out.print("");
			//onEnableValidateConfig();
		else
			database.reportErrorStatus(ConnectionError.MISSING_CONFIG);
	}
	*/
	
	/*
	 * NOT REVISED BELOW
	 */
	
	/*
	public static void onEnableValidateConfig()
	{
		config = plugin.getConfig();
		logger.info("config.yml found. Performing setup operations");
		
		try
		{
			statisticsRepository.openConnection(getRepositoryCredentials(), getMaterialList("tracked-mined-materials"), getMaterialList("tracked-placed-materials"), logger);
			plugin.getServer().getPluginManager().registerEvents(new EventListener(statisticsRepository), plugin);
			logger.info("Connection and setup process complete.");
		}
		catch(SQLException e)
		{
			logger.info("FATAL ERROR: Failed to connect to database. See the below exception:");
			logger.info(e.toString());
		}
	}
	*/
	
	/**
	 * Gets all credentials and address information needed to connect to the statistics database
	 * from the configuration file.
	 * @return - A Map whose keys are the YAML variable names and whose values are the key's values.
	 */
	
	/*
	public static Map<String, String> getRepositoryCredentials()
	{
		Map<String, String> credentials = new HashMap<String, String>();
		credentials.put("ip", "");
		credentials.put("port", "");
		credentials.put("database-name", "");
		credentials.put("username", "");
		credentials.put("password", "");

		String value = "";
		for(String key : credentials.keySet())
		{
			value = config.getString(key);
			credentials.put(key, value == null ? "" : value);
		}
		
		return credentials;
	}
	*/
	/**
	 * Retrieves a list of Spigot Materials from config.yml
	 * @param configKeyName - The name of the setting in config.yml containing the Material list
	 * @return The list of Materials, with any invalid or null entries parsed out.
	 */
	
	/*
	public static List<Material> getMaterialList(String configKeyName)
	{
		List<Material> trackedMaterials = new ArrayList<Material>();
		
		List<?> ymlInput = config.getList(configKeyName);
		if(ymlInput != null)
			for(Object mat : ymlInput)
			{	
				try
				{
					trackedMaterials.add(Material.valueOf(mat.toString()));
				}
				catch(IllegalArgumentException e)
				{
					logger.info("ERROR: plugin.yml:" + configKeyName + " - Invalid material '" 
							+ mat.toString() + "' - Ignoring Entry");
				}
				catch(NullPointerException e)
				{
					logger.info("ERROR: plugin.yml:" + configKeyName + " - Null value in list - Ignoring Entry");
				}
			}
		logger.info(configKeyName + ": Found " + trackedMaterials.size() + " valid materials.");
		return trackedMaterials;
	}
	*/
	
	/**
	 * Fired when the plugin is disabled.
	 */
	/*
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
	*/
}