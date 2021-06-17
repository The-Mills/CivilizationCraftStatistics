package repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.bukkit.Material;

public interface Repository 
{

	/**
	 * Performs the initial connection to the database server and creates any needed columns. Must be called before using any other methods.
	 * @param credentials - Necessary credentials for the database server: IP address, port, database name, username, and password.
	 * @param tracked_mined_materials - List of Materials that will be tracked when a player breaks them and triggers a BlockBreakEvent
	 * @param tracked_placed_materials - List of Materials that will be tracked when a player places them and triggers a BlockPlaceEvent
	 * @param pluginLogger - The plugin's logger.
	 * @throws SQLException
	 */
	void openConnection(Map<String, String> credentials, List<Material> tracked_mined_materials, List<Material> tracked_placed_materials, Logger pluginLogger) throws SQLException;
	

	/**
	 * Closes the connection to the database.
	 * @throws SQLException
	 */
	void closeConnection() throws SQLException;
	
	
	/**
	 * Adds a new player's UUID to the statistics database.
	 * @param UUID - The player's 36 character UUID.
	 * @throws SQLException
	 */
	void registerNewUser(String UUID) throws SQLException;

	/**
	 * Increments the total number of a tracked block type a player has mined in the statistics database.
	 * @param UUID - The player's 36 character UUID
	 * @param blockType - The type of the block in all capital letters.
	 * @throws SQLException
	 */
	void incrementMinedCount(String UUID, Material blockType) throws SQLException;
	
	/**
	 * Increments the total number of a tracked block type a player has placed in the statistics database.
	 * @param UUID - The player's 36 character UUID
	 * @param blockType - The type of the block in all capital letters.
	 * @throws SQLException
	 */
	void incrementPlacedCount(String UUID, Material blockType) throws SQLException;
	
}