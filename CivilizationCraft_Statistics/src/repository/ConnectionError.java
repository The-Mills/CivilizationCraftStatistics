package repository;

/**
 * Describes all possible states of the database connection using errors.
 * 
 * TO ADD A NEW ERROR:
 * 1. Define it here.
 * 2. Define how to resolve it in CommandCivStatsAdmin.onCommandAutoReboot()
 * 
 * @author Daniel Milligan
 */
public enum ConnectionError 
{
	NORMAL("No database connection errors detected."),
	PENDING("Currently opening or repairing connection."),
	UNINITIALIZED("No attempt has been made to initialize the database connection.");
	
	private final String errorDescription;
	
	private ConnectionError(String s)
	{errorDescription = s;}
	
	@Override
	public String toString()
	{
		return name() + " - " + errorDescription;
	}
}