package statistics_tracker;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;
import java.util.ArrayList;

/**
 * Implements the functionality of all CivCraftStatsTracker administrator commands.
 * 
 * TO ADD A NEW COMMAND:
 * 1. Add it to List COMMANDS and String[] COMMANDS_DESCRIPTIONS in alphabetical order.
 * 2. Add it to the switch statement in *ALL LOWERCASE* and define it's logic in onCommand().
 * 3. Adjust behavior of onTabComplete() if needed.
 * 
 * @author Daniel Milligan
 */
public class CommandCivStatsAdmin implements CommandExecutor, TabCompleter
{	
	/**
	 * The command name used to access all administrator commands.
	 */
	public static final String NAME = "civStatsAdmin";
	
	/**
	 * List of all admin command names. Used by TabCompleter.
	 */
	private static final List<String> COMMANDS = new ArrayList<String>() {{
		add("autoReboot");
		add("status");
	}};
	
	/**
	 * List of all admin command names and descriptions of what they do.
	 * Sent to the CommandSender when the administrator command is called with no arguments.
	 */
	private static final String[] COMMANDS_DESCRIPTIONS = new String[]{
		"autoReboot - Tries repairing database connection using error status.",
		"status - Checks database connection status."
	};
	
	/**
	 * Sent to CommandSender when an invalid command is entered.
	 */
	private static final String INVALID_COMMAND     = "Invalid Command. Use /" + NAME + " for a full list.";
	/**
	 * Sent to CommandSender when an invalid number of arguments are passed for the given command.
	 */
	private static final String INVALID_NUMBER_ARGS = "Invalid number of arguments. Use /" + NAME + " to show command formats.";
	
	/**
	 * Executes when the administrator command is used.
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if(args.length == 0)
		{
			sender.sendMessage(COMMANDS_DESCRIPTIONS);
			return true;
		}
		switch(args[0].toLowerCase())
		{
			case "autoreboot":
				CivilizationCraftStatistics.database.runTaskOpenOrRepairDatabaseAsynchronously(sender);
				break;
			case "status":
				CivilizationCraftStatistics.database.runTaskGetDatabaseStatusSynchronously(sender);
				break;
			default:
				sender.sendMessage(INVALID_COMMAND);
		}
		return true;
	}
	
	/**
	 * Executes when tab-completing the administrator command.
	 */
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args)
	{
		if(args.length == 1)
			return COMMANDS;
		else
			return new ArrayList<String>(){};
	}
}