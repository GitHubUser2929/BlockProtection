package info.kanlaki101.blockprotection.commands;

import info.kanlaki101.blockprotection.BlockProtection;
import info.kanlaki101.blockprotection.utilities.BPConfigHandler;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BPAdmin implements CommandExecutor {
	public static BlockProtection pl;
	public BPAdmin(BlockProtection instance) {
		pl = instance;
	}
		
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		String noperm = "You do not have permission to use this command.";
		ChatColor YELLOW = ChatColor.YELLOW;
		
		if (cmd.getName().equalsIgnoreCase("bpadmin")) {
			if (!(sender instanceof Player)) return true;
			
			Player p = (Player) sender;
			String player = p.getName();
			String bpp = pl.prefix + player;
			if (!pl.isAuthorized(p, "bp.admin")) { //No permissions
				p.sendMessage(YELLOW + noperm);
				if (BPConfigHandler.advLog() == true) pl.log.warning(bpp + " attempted to use command: 'bpadmin'."); //Log attempted use of command
				return true;
			}
			if (pl.UsersBypass.contains(player)) { //Check for the player in the bypass protection array
				pl.UsersBypass.remove(player); //Remove them from the array. (Can't break blocks)
				p.sendMessage(YELLOW + "You can no longer break other players blocks.");
				if (BPConfigHandler.advLog() == true) pl.log.info(bpp + " is no longer bypassing BlockProtection"); //Log to console
			}
			else {
				pl.UsersBypass.add(player); //Add them to the array, and allow them to break blocks
				p.sendMessage(YELLOW + "You can now break other players blocks.");
				if (BPConfigHandler.advLog() == true) pl.log.info(bpp + " is now bypassing BlockProtection"); //Log to console
			}
		}
		return true;
	}
}
