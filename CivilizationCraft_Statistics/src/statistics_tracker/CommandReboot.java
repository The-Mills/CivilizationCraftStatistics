package statistics_tracker;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandReboot implements CommandExecutor
{
	private static RebootError previousBootError;
	
	public enum RebootError
	{
		MISSING_CONFIG("The configuration file [SERVER DIRECTORY]/plugins/CivilizationCraftStatisticsTracker/config.yml does not exist. Cannot connect to database or perform critical setup operations.");
		
		private final String errorDescription;
		
		private RebootError(String s)
		{errorDescription = s;}
		
		@Override
		public String toString()
		{
			return name() + " - " + errorDescription + " Resolve problem and reboot plugin with ./civStatsTrackerReboot";
		}
	}
	
	public static void logError(RebootError error)
	{
		CivilizationCraftStatistics.logger.info("FATAL ERROR: " + error.toString());
		previousBootError = error;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		// Not necessary, as Spigot will prevent you from running the command in the first place.
		if(!sender.hasPermission("CivilizationCraftStatisticsTracker.administrator")) 
		{
			sender.sendMessage("Insufficient Permissions");
			return true;
		}
		if(args.length != 1)
		{
			sender.sendMessage("Please specify exactly one argument.");
			return true;
		}
		switch(args[0])
		{
			case "showError":
				sender.sendMessage("Current Error: " + previousBootError.toString());
				break;
			case "autoReboot":
				onCommandAutoReboot();
				break;
			default:
				sender.sendMessage("Invalid Parameter");
		}
		return true;
	}
	
	public void onCommandAutoReboot()
	{
		switch(previousBootError)
		{
			case MISSING_CONFIG:
				CivilizationCraftStatistics.plugin.onEnableDoesConfigExist();
				break;
		}
	}
}