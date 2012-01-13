package info.kanlaki101.blockprotection.commands;

import info.kanlaki101.blockprotection.BlockProtection;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BPClear implements CommandExecutor {
	public static BlockProtection pl;
	public BPClear(BlockProtection instance) {
		pl = instance;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!(sender instanceof Player)) return true; //Sender is not in-game
		Player p = (Player)sender;
		String player = p.getName();
		String noperm = "You do not have permission to use this command.";
		ChatColor YELLOW = ChatColor.YELLOW;
		
		if (args.length > 1) return true; //Too many arugments
		if (commandLabel.equalsIgnoreCase("bpclear")) {
			pl.loadFriendsList();
			if (args.length > 0) return true;
			if (!pl.isAuthorized(p, "bp.friend")) { //No permissions
				p.sendMessage(YELLOW + noperm);
				return true;
			}
			if (pl.friendslist.getList(player) == null) { //Check if player has a friends list
				p.sendMessage(YELLOW + "You do not have a friends list.");
			}
			else {
				pl.friendslist.set(player, null); //Remove everyone from the list
				p.sendMessage(YELLOW + "Your friends list has been cleared.");
				pl.saveFriendsList(); //Save friends list
			}
		}
	return true;
	}
}
