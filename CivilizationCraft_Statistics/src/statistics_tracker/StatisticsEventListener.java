package statistics_tracker;

import java.sql.SQLException;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import repository.Repository;


public class StatisticsEventListener implements Listener
{
	private Repository statisticsRepository;
	
	public StatisticsEventListener(Repository statsRepo)
	{
		statisticsRepository = statsRepo;
	}
	
	/**
	 * When a player mines a block, increments the total number of that block they've mined by one in the statistics database.
	 * If that type of block is not being tracked, do nothing.
	 * @param event - The event thrown by the Spigot APU when a block is broken.
	 */
	@EventHandler
	public void onMineBlock(BlockBreakEvent event)
	{
		try 
		{
			if(event.getBlock().getType().name().equals("STONE"))
				statisticsRepository.incrementMinedCount(event.getPlayer().getUniqueId().toString(), event.getBlock().getType().name());
		}
		catch(NullPointerException e) {System.out.println(e);}
		catch(SQLException e)
		{
			// Handle this appropriately. To be determined.
			System.out.println(e);
		}
	}
	
	
	/**
	 * When a player connects to the server, adds them to the statistics database with their UUID as the primary key.
	 * If the player's UUID is not stored in the database yet (new player), they are added without issue.
	 * If the player's UUID is already stored in the database, an SQLException is thrown (and caught).
	 * @param event - The event thrown by the Spigot API when the player joins the server.
	 */
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		try
		{
			statisticsRepository.registerNewUser(event.getPlayer().getUniqueId().toString());
		}
		catch(NullPointerException e) {System.out.println(e);}
		catch(SQLException e)
		{
			System.out.println(e);
		}
	}
	
}