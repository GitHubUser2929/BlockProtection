package info.kanlaki101.blockprotection.commands;

import info.kanlaki101.blockprotection.BlockProtection;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BPAdd implements CommandExecutor {
	public static BlockProtection pl;
	public BPAdd(BlockProtection instance) {
		pl = instance;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!(sender instanceof Player)) return true; //Sender is not in-game
		Player p = (Player)sender;
		String player = p.getName();
		String noperm = "You do not have permission to use this command.";
		ChatColor YELLOW = ChatColor.YELLOW;
		
		if (args.length > 1) return true; //Too many arugments
		if (commandLabel.equalsIgnoreCase("bpadd")) {
			pl.loadFriendsList();
			if (!pl.isAuthorized(p, "bp.friend")) { //No permissions
				p.sendMessage(YELLOW + noperm);
				return true;
			}
			if (args.length == 0) { //Not enough arguments
				p.sendMessage(YELLOW + "You must specify a player to add!");
				return true;
			}
			
			if (pl.friendslist.getList(player) ==  null) { //Check if the player already has a buddy list
				String [] list = {args[0]}; //Since player doens't have a friends list, make them one
				pl.friendslist.set(player, Arrays.asList(list)); 
			}
			else {
				if (pl.friendslist.getList(player).contains(args[0])) { //Check if the player is already on their buddy list
					p.sendMessage(YELLOW + args[0] + " is already on your friends list.");
					return true;
				}
				pl.friendslist.getList(player).add(0, args[0]); //Add player to the database
			}
			p.sendMessage(YELLOW + args[0] + " has been added to your friends list.");
			pl.saveFriendsList(); //Save
		}
			
	return true;
	}
}
