package info.kanlaki101.blockprotection.commands;

import info.kanlaki101.blockprotection.BlockProtection;
import info.kanlaki101.blockprotection.utilities.BPConfigHandler;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BPReload implements CommandExecutor {
	public static BlockProtection pl;
	public BPReload(BlockProtection instance) {
		pl = instance;
	}
		
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		String noperm = "You do not have permission to use this command.";
		ChatColor YELLOW = ChatColor.YELLOW;

		String reload = "[BlockProtection] Reloaded from config.";
		if (cmd.getName().equalsIgnoreCase("bpreload")) {
			if (!(sender instanceof Player)) { //If command is sent from console
				pl.log.info(reload); //Log to console
				BPConfigHandler.loadConfig(); //Reload settings from the config.yml file
			}
			else {
				Player p = (Player) sender;
				String bpp = pl.prefix + p.getName();
				if (p.hasPermission("bp.reload")) { //No permissions
					p.sendMessage(YELLOW + pl.prefix + "Reloaded from config.");
					BPConfigHandler.loadConfig(); //Reload settings from the config.yml file
					pl.log.info(bpp +" reloaded config.");
				}
				else {
					p.sendMessage(YELLOW + noperm);
					if (BPConfigHandler.advLog() == true) pl.log.warning(bpp + " attempted to use command: 'bpreload'.");
				}
			}
		}
		return true;
	}
}
