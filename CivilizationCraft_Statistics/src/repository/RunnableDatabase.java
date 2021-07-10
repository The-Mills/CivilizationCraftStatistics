package repository;

import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitTask;

public interface RunnableDatabase 
{
	BukkitTask runTaskOpenOrRepairDatabaseAsynchronously(CommandSender sender);
	
	void runTaskGetDatabaseStatusSynchronously(CommandSender sender);
}