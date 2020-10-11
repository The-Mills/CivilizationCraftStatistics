package repository;

import java.sql.SQLException;
import java.util.Map;
import org.bukkit.Material;

public interface Repository 
{
	/**
	 * Performs the initial connection to the database server. Must be called before using any other methods.
	 * @param credentials - Necessary credentials for the database server: IP address, port, database name, username, and password.
	 */
	void openConnection(Map<String, String> credentials);
	

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