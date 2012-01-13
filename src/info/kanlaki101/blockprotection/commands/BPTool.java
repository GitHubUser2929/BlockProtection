package info.kanlaki101.blockprotection.commands;

import info.kanlaki101.blockprotection.BlockProtection;
import info.kanlaki101.blockprotection.utilities.BPConfigHandler;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BPTool implements CommandExecutor {
	public static BlockProtection pl;
	public BPTool(BlockProtection instance) {
		pl = instance;
	}
		
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		String noperm = "You do not have permission to use this command.";
		ChatColor YELLOW = ChatColor.YELLOW;
		
		if (cmd.getName().equalsIgnoreCase("bptool")) {
			if (!(sender instanceof Player)) return true;
			
			Player p = (Player) sender;
			String player = p.getName();
			String bpp = pl.prefix + player;
			
			BPConfigHandler.loadConfig();
			if (!pl.isAuthorized(p, "bp.admin")) { //No permissions
				p.sendMessage(YELLOW + noperm);
				if (BPConfigHandler.advLog() == true) pl.log.warning(bpp + " attempted to use command: 'bptool'.");
			} 
			else {
				if (args.length > 1) return true; //Too many arguments
				
				if (p.getInventory().firstEmpty() == -1) { //If the player's inventory is full
					p.sendMessage(YELLOW + "Can not add tool. Inventory is full.");
					return true;
				}
				
				Material item = Material.getMaterial(BPConfigHandler.getUtilTool());
				if (p.getInventory().contains(item)) {
					p.sendMessage(YELLOW + "You already have a " + item + ".");
					return true;
				}
				
				p.getInventory().addItem(new ItemStack(item, 1 ));
				p.sendMessage(YELLOW + "" + item + " has been added to your inventory.");
				
				if (BPConfigHandler.advLog() == true) pl.log.info(bpp + " has been given a utility tool."); //Log to console
				return true;
			}
		}
		return true;
	}
}
