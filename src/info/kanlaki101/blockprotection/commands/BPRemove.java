package info.kanlaki101.blockprotection.commands;

import info.kanlaki101.blockprotection.BlockProtection;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BPRemove implements CommandExecutor {
	public static BlockProtection pl;
	public BPRemove(BlockProtection instance) {
		pl = instance;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!(sender instanceof Player)) return true; //Sender is not in-game
		Player p = (Player)sender;
		String player = p.getName();
		String noperm = "You do not have permission to use this command.";
		ChatColor YELLOW = ChatColor.YELLOW;
		
		if (args.length > 1) return true; //Too many arugments

		if (commandLabel.equalsIgnoreCase("bpremove")) {
			pl.loadFriendsList();
			if (!pl.isAuthorized(p, "bp.friend")) { //No permissions
				p.sendMessage(YELLOW + noperm);
				return true;
			}
			if (pl.friendslist.getList(player) == null) { //Check if a friends list exist for them
				p.sendMessage(YELLOW + "You do not have a friends list.");
				return true;
			}
			if (args.length == 0) { //Not enough arguments
				p.sendMessage(YELLOW + "You must specify a player to remove!");
				return true;
			}
			
			if (pl.friendslist.getList(player).contains(args[0])) { //If player is already in your friends list
				pl.friendslist.getList(player).remove(args[0]); //Remove him
		        if (pl.friendslist.getList(player).isEmpty()) { //If the list is empty
		        	pl.friendslist.set(player, null); //Delete it
		        }
		        pl.saveFriendsList(); //Save
		        p.sendMessage(YELLOW + args[0] + " has been removed from your friends list.");
			} 
			else {
				p.sendMessage(YELLOW + args[0] + " is not on your friends list.");
			}
		}	
	return true;
	}
}
