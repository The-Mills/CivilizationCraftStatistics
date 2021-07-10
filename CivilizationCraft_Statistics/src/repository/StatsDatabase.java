package repository;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;


import statistics_tracker.CivilizationCraftStatistics;

public class StatsDatabase implements RunnableDatabase, RepositoryHandler, ConnectionHandler
{
	private static final String LOG_PLAYER_PREFACE     = "[CivStats] ";
	private static final String LOG_REPOSITORY_PREFACE = "Repository - ";
	private static final String LOG_CONNECTION_PREFACE = "Connection - ";
	private static final String LOG_ERROR              = "ERROR FOUND: ";
	private static final String LOG_ERROR_FIX          = " Reboot after fixing with /civStatsAdmin autoReboot";
	private static final String REPAIR_ALREADY_PENDING = "Already initializing or repairing.";
	private static final String REPAIR_NO_ERROR        = "No error detected.";
	private static final String REPAIR_SUCCESS         = "Successfully booted. No errors found.";
	
	public StatsDatabase(BukkitScheduler scheduler)
	{
		serverScheduler = scheduler;
		config = new ConfigurationHandler();
	}
	
	
	
	
	/*
	 * RUNNABLE DATABASE
	 */
	
	private BukkitScheduler serverScheduler;
	private ConfigurationHandler config;
	
	@Override
	public BukkitTask runTaskOpenOrRepairDatabaseAsynchronously(CommandSender sender)
	{
		return serverScheduler.runTaskLaterAsynchronously(CivilizationCraftStatistics.plugin, new Runnable() {
			@Override
			public void run()
			{
				logMessage(sender, new String[] {openOrRepairRepository()}, true);
				if(repositoryStatus != RepositoryError.MISSING_CONFIG)
				{
					logMessage(sender, new String[] {openOrRepairConnection()}, true);
				}
			}
		}, 0);
	}
	
	@Override
	public void runTaskGetDatabaseStatusSynchronously(CommandSender sender)
	{
		String[] logMessages = new String[2];
		logMessages[0] = LOG_REPOSITORY_PREFACE + repositoryStatus.toString();
		logMessages[1] = LOG_CONNECTION_PREFACE + connectionStatus.toString();
		logMessage(sender, logMessages, false);
		
	}
	
	private void logMessage(CommandSender sender, String[] msg, boolean sendToConsole)
	{
		if(sender != null && !(sender instanceof ConsoleCommandSender))
		{
			sender.sendMessage(LOG_PLAYER_PREFACE + msg);
		}
		if(sendToConsole)
			for(String m : msg)
				CivilizationCraftStatistics.logger.info(m);
	}
	
	/*
	 * REPOSITORY
	 */
	
	private RepositoryError repositoryStatus = RepositoryError.UNINITIALIZED;
	

	@Override
	public String openOrRepairRepository()
	{
		String logInfo = LOG_REPOSITORY_PREFACE;
		// Avoid unnecessary modifications to the repositoryStatus variable when possible.
		switch(repositoryStatus)
		{
		case PENDING:
			return logInfo + REPAIR_ALREADY_PENDING;
		case NORMAL:
			return logInfo + REPAIR_NO_ERROR;
		default:
			break;
		}
		
		RepositoryError tempRepositoryStatus = repositoryStatus;
		repositoryStatus = RepositoryError.PENDING;
		switch(tempRepositoryStatus)
		{
		case UNINITIALIZED: case MISSING_CONFIG:
			if(!config.doesConfigExist())
			{
				repositoryStatus = RepositoryError.MISSING_CONFIG;
				return logInfo + LOG_ERROR + RepositoryError.MISSING_CONFIG.toString() + LOG_ERROR_FIX;
			}
		case PENDING: case NORMAL:
			break;
		}
		repositoryStatus = RepositoryError.NORMAL;
		return logInfo + REPAIR_SUCCESS;
	}
	
	@Override
	public void setRepositoryStatus(RepositoryError status)
	{
		repositoryStatus = status;
	}
	
	@Override
	public RepositoryError getRepositoryStatus()
	{
		return repositoryStatus;
	}
	
		
	/*
	 * CONNECTION
	 */
	
	private ConnectionError connectionStatus = ConnectionError.UNINITIALIZED;
	
	@Override
	public String openOrRepairConnection()
	{
		String logInfo = LOG_CONNECTION_PREFACE;
		switch(connectionStatus)
		{
		case PENDING:
			return logInfo + REPAIR_ALREADY_PENDING;
		case NORMAL:
			return logInfo + REPAIR_NO_ERROR;
		default:
			break;
		}
		
		ConnectionError tempConnectionStatus = connectionStatus;
		connectionStatus = ConnectionError.PENDING;
		switch(tempConnectionStatus)
		{
		case UNINITIALIZED:
		case PENDING: case NORMAL:
			break;
		}
		connectionStatus = ConnectionError.NORMAL;
		return logInfo + REPAIR_SUCCESS;
	}
	
	@Override
	public void setConnectionStatus(ConnectionError status)
	{
		connectionStatus = status;
	}
	
	@Override
	public ConnectionError getConnectionStatus()
	{
		return connectionStatus;
	}
	
	
	/*
	 * WAREHOUSE
	 */
	private Map<String, Warehouse> sessionData = new HashMap<String, Warehouse>();
}
