package repository;
import java.sql.SQLException;

public interface Repository 
{
	/**
	 * Closes the connection to the database.
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
	 * @throws SQLException
	 */
	void incrementMinedCount(String UUID, String blockType) throws SQLException;
}