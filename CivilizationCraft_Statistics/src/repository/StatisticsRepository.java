package repository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class StatisticsRepository implements Repository
{
	private Connection mySQLConnection;
	private Statement queryExecutor;
	private ResultSet queryResult;
	
	/**
	 * Establishes connection to the database and instantiates query objects.
	 * @param connectionInformation - An array of exactly 5 strings listing, in order: IP, Port, database name, username, and password.
	 * @throws SQLException
	 */
	public StatisticsRepository(Map<String, String> credentials)
	{
		try
		{
			mySQLConnection = DriverManager.getConnection("jdbc:mysql://" + credentials.get("ip") + ":" + credentials.get("port") 
				+ "/" + credentials.get("database-name"), credentials.get("username"), credentials.get("password"));
			queryExecutor = mySQLConnection.createStatement();
		}
		catch(SQLException e)
		{
			System.out.println(e);
		}
	}
	
	// See Repository interface for documentation on the below methods..
	public void closeConnection() throws SQLException
	{
		mySQLConnection.close();
	}
	
	public void registerNewUser(String UUID) throws SQLException
	{
		queryExecutor.execute("INSERT INTO playerstats(UUID) VALUES('" + UUID + "')");
	}
	
	public void incrementMinedCount(String UUID, String blockType) throws SQLException
	{
		queryExecutor.execute("UPDATE playerstats SET " + blockType + "Mined = " + blockType + "Mined + 1 WHERE UUID = '" + UUID + "'");
	}
}