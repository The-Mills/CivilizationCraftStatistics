package repository;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Logger;
import org.bukkit.Material;

public class StatisticsRepository implements Repository
{
	private Connection mySQLConnection;
	private Statement queryExecutor;
	private ResultSet queryResult;
	private DatabaseMetaData metaData;

	private List<Material> trackedMinedMaterials;
	
	public void openConnection(Map<String, String> credentials, List<Material> tracked_mined_materials, Logger pluginLogger)
	{
		try
		{
			mySQLConnection = DriverManager.getConnection("jdbc:mysql://" + credentials.get("ip") + ":" + credentials.get("port") 
				+ "/" + credentials.get("database-name") + "", credentials.get("username"), credentials.get("password"));
			queryExecutor = mySQLConnection.createStatement();
			metaData = mySQLConnection.getMetaData();
			pluginLogger.info("Database connection established.");
			
			
			List<String> newColumnNames = new ArrayList<String>();
			for(Material currentMaterial: tracked_mined_materials)
				if(!metaData.getColumns(null, null, null, "mined" + currentMaterial).next())
					newColumnNames.add("mined" + currentMaterial);
			
			if(newColumnNames.size() > 0)
			{
				pluginLogger.info("Creating " + newColumnNames.size() + " new columns in 'PlayerStats' table.");
				
				String createColumnsQuery = "ALTER TABLE PlayerStats ";
				for(String newColumn : newColumnNames)
					createColumnsQuery += "ADD COLUMN " + newColumn + " INT UNSIGNED NOT NULL DEFAULT 0,";
				createColumnsQuery = createColumnsQuery.substring(0, createColumnsQuery.length() - 1) + ";";
				queryExecutor.execute(createColumnsQuery);
				
				metaData = mySQLConnection.getMetaData();
			}
			
			trackedMinedMaterials = tracked_mined_materials;
			
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
			queryExecutor.execute("INSERT INTO playerstats(UUID) VALUES('" + UUID + "')");
	}
	
	public void incrementMinedCount(String UUID, Material blockType) throws SQLException
	{
		if(trackedMinedMaterials.contains(blockType))
			queryExecutor.execute("UPDATE playerstats SET mined" + blockType + " = mined" + blockType + " + 1 WHERE UUID = '" + UUID + "'");
	}
}