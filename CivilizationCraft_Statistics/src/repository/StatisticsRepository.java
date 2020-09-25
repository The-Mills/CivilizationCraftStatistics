package repository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StatisticsRepository implements Repository
{
	private Connection mySQLConnection;
	private Statement queryExecutor;
	private ResultSet queryResult;
	
	public StatisticsRepository() throws SQLException
	{
		mySQLConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/civilizationcraft_statistics", "admin", "admin");
		queryExecutor = mySQLConnection.createStatement();
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