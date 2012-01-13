package info.kanlaki101.blockprotection.commands;

import info.kanlaki101.blockprotection.BlockProtection;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BP implements CommandExecutor {
	public static BlockProtection pl;
	public BP(BlockProtection instance) {
		pl = instance;
	}
		
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		String noperm = "You do not have permission to use this command.";
		ChatColor YELLOW = ChatColor.YELLOW;
		
		if (args.length > 0) return true;
		
		if (cmd.getName().equalsIgnoreCase("bp")) {
			if (!(sender instanceof Player)) return true;
			
			Player p = (Player) sender;
			String player = p.getName();
			if (!pl.isAuthorized(p, "bp.user")) { //No permissions
				p.sendMessage(YELLOW + noperm);
				return true;
			}
			if (pl.Users.contains(player)) { //Check for the player in the protection array
				pl.Users.remove(player); //Remove player from the array list and stop protecting their blocks
				p.sendMessage(YELLOW + "Your blocks are no longer protected!");
			} 
			else {
				pl.Users.add(player); //Add player to the array list and start protecting their blocks
				p.sendMessage(YELLOW + "Your blocks are now protected!");
			}
		}
		return true;
	}
}
