package statistics_tracker;

public enum ConnectionError 
{
	NORMAL("No database connection errors detected."),
	MISSING_CONFIG("The configuration file ./plugins/CivilizationCraftStatisticsTracker/config.yml does not exist. Cannot connect to database or perform critical setup operations.");
	
	private final String errorDescription;
	
	private ConnectionError(String s)
	{errorDescription = s;}
	
	@Override
	public String toString()
	{
		return name() + " - " + errorDescription;
	}
	
	
	private static ConnectionError currentStatus = NORMAL;
	
	public static void reportErrorStatus(ConnectionError error)
	{
		CivilizationCraftStatistics.logger.info("CONNECTION ERROR: " + error.toString() + " Reboot after fixing with /civStatsAdmin autoReboot");
		currentStatus = error;
	}
	
	public static void setNormalStatus()
	{
		currentStatus = NORMAL;
	}
	
	public static ConnectionError getCurrentStatus()
	{
		return currentStatus;
	}
}