package statistics_tracker;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import java.util.List;
import java.util.ArrayList;

/**
 * Implements the functionality of all CivCraftStatsTracker administrator commands.
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
	 * Part of the message sent to CommandSender when the status command used.
	 */
	private static final String STATUS_MESSAGE      = "Current Status: ";
	/**
	 * Sent to CommandSender when autoReboot is needlessly executed or already running.
	 */
	private static final String AUTOREBOOT_NO_ERROR = "No connection error exists or autoReboot is already running.";
	/**
	 * Sent to CommandSender when autoReboot successfully repairs database connection.
	 */
	private static final String AUTOREBOOT_SUCCESS  = "Connection issue resolved.";
	/**
	 * Sent to CommandSender when autoReboot fails to repair database connection.
	 */
	private static final String AUTOREBOOT_FAILURE  = "Failed to repair connection. For more info, use /" + NAME +  " status";
	
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
				onCommandAutoReboot(sender);
				break;
			case "status":
				sender.sendMessage(STATUS_MESSAGE + ConnectionError.getCurrentStatus().toString());
				break;
			default:
				sender.sendMessage(INVALID_COMMAND);
		}
		return true;
	}
	
	/**
	 * Executes when the autoReboot command is used. 
	 * @param sender - The CommandSender from onCommand
	 */
	private void onCommandAutoReboot(CommandSender sender)
	{
		/*
		 *  Resets currentStatus to NORMAL before trouble shooting. Prevents duplicate runs
		 *  of autoReboot. Should another error pop up during reboot, the currentStatus will
		 *  change again.
		 */
		ConnectionError error = ConnectionError.getCurrentStatus();
		ConnectionError.setNormalStatus();
		switch(error)
		{
			case NORMAL:
				sender.sendMessage(AUTOREBOOT_NO_ERROR);
				return;
			case MISSING_CONFIG:
				CivilizationCraftStatistics.plugin.onEnableDoesConfigExist();
				break;
		}
		if(ConnectionError.getCurrentStatus() == ConnectionError.NORMAL)
			sender.sendMessage(AUTOREBOOT_SUCCESS);
		else
			sender.sendMessage(AUTOREBOOT_FAILURE);
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