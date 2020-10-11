package repository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import org.bukkit.Material;

public class StatisticsRepository implements Repository
{
	private Connection mySQLConnection;
	private Statement queryExecutor;
	private ResultSet queryResult;
	

	public void openConnection(Map<String, String> credentials)
	{
		try
		{
			mySQLConnection = DriverManager.getConnection("jdbc:mysql://" + credentials.get("ip") + ":" + credentials.get("port") 
				+ "/" + credentials.get("database-name") + "", credentials.get("username"), credentials.get("password"));
			queryExecutor = mySQLConnection.createStatement();
		}
		catch(SQLException e)
		{
			System.out.println(e);
		}
	}
	
	public void closeConnection() throws SQLException
	{
		mySQLConnection.close();
	}
	
	public void registerNewUser(String UUID) throws SQLException
	{
		queryResult = queryExecutor.executeQuery("SELECT UUID FROM PlayerStats WHERE UUID = '" + UUID + "'");
		if(!queryResult.next())
		{
			queryExecutor.execute("INSERT INTO playerstats(UUID) VALUES('" + UUID + "')");
			System.out.println("Added new UUID");
		}
	}
	
	public void incrementMinedCount(String UUID, Material blockType) throws SQLException
	{
		queryExecutor.execute("UPDATE playerstats SET mined" + blockType + " = mined" + blockType + " + 1 WHERE UUID = '" + UUID + "'");
	}
}