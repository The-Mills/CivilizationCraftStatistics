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
	 * @param pluginLogger - The plugin's logger.
	 */
	void openConnection(Map<String, String> credentials, List<Material> tracked_mined_materials, Logger pluginLogger);
	

	/**
	 * Closes the connection to the database.
	 * @throws SQLException
	 */
	void closeConnection() throws SQLException;
	
	
	/**
	 * Adds a new player's UUID to the statistics database.
	 * @param UUID - The player's 36 character UUID.
	 * @throws SQLException - Most likely caused by the player's UUID already being in the database.
	 */
	void registerNewUser(String UUID) throws SQLException;

	/**
	 * Increments the total number of a tracked block type a player has mined in the statistics database.
	 * @param UUID - The player's 36 character UUID
	 * @param blockType - The type of the block in all capital letters.
	 */
	void incrementMinedCount(String UUID, Material blockType) throws SQLException;
	
}