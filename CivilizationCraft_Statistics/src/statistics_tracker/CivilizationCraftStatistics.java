package statistics_tracker;
import org.bukkit.plugin.java.JavaPlugin;
import repository.Repository;
import repository.StatisticsRepository;

import java.sql.SQLException;


public class CivilizationCraftStatistics extends JavaPlugin
{
	private Repository statisticsRepository;
	
	/**
	 * Fired when the plugin is enabled
	 */
	@Override
	public void onEnable()
	{
		try
		{
			statisticsRepository = new StatisticsRepository(); 
		}
		catch(SQLException e)
		{
			// Handle something like this appropriately. To be implemented.
			System.out.println(e);
		}
		
		getServer().getPluginManager().registerEvents(new StatisticsEventListener(statisticsRepository), this);
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
		catch(SQLException e)
		{
			// Handle something like this appropriately. To be implemented.
			System.out.println(e);
		}
	}
}