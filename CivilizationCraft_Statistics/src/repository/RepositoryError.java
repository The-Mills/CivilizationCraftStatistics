package repository;

public enum RepositoryError 
{
	NORMAL("No repository errors detected."),
	PENDING("Currently initializing or repairing repository."),
	UNINITIALIZED("No attempt has been made to initialize the repository."),
	MISSING_CONFIG("The configuration file ./plugins/CivilizationCraftStatisticsTracker/config.yml does not exist. Cannot connect to database or perform critical setup operations.");
	
	private final String errorDescription;
	
	private RepositoryError(String s)
	{errorDescription = s;}
	
	@Override
	public String toString()
	{
		return name() + " - " + errorDescription;
	}
}