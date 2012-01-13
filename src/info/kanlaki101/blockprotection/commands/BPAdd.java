package info.kanlaki101.blockprotection.commands;

import info.kanlaki101.blockprotection.BlockProtection;
import info.kanlaki101.blockprotection.utilities.BPConfigHandler;

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
		if (!(sender instanceof Player)) return true; //Sender is not a player
		Player p = (Player)sender;
		String player = p.getName();
		String noperm = "You do not have permission to use this command.";
		ChatColor YELLOW = ChatColor.YELLOW;
		
		if (args.length > 1) return true; //Too many arguments
		
		if (cmd.getName().equalsIgnoreCase("bpadd")) {
			BPConfigHandler.loadConfig();
			BPConfigHandler.loadFriendsList();
			if (!pl.isAuthorized(p, "bp.friend")) { //No permissions
				p.sendMessage(YELLOW + noperm);
				return true;
			}
			if (args.length == 0) { //Not enough arguments
				p.sendMessage(YELLOW + "You must specify a player to add!");
				return true;
			}
			
			if (BPConfigHandler.getFriendslist(player) ==  null) { //Check if the player already has a friends list
				String [] list = {args[0]}; //Since player doens't have a friends list, make them one
				BPConfigHandler.friendslist.set(player, Arrays.asList(list)); 
			}
			else {
				if (BPConfigHandler.getFriendslist(player).contains(args[0])) { //Check if the player is already on their friends list
					p.sendMessage(YELLOW + args[0] + " is already on your friends list.");
					return true;
				}
				BPConfigHandler.getFriendslist(player).add(0, args[0]); //Add player to their friends list
			}
			p.sendMessage(YELLOW + args[0] + " has been added to your friends list.");
			BPConfigHandler.saveFriendsList(); //Save
		}
			
	return true;
	}
}
