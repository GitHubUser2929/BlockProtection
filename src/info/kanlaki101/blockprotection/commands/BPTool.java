package info.kanlaki101.blockprotection.commands;

import info.kanlaki101.blockprotection.BlockProtection;

import org.bukkit.ChatColor;
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
		
		if (commandLabel.equalsIgnoreCase("bptool")) {
			if (!(sender instanceof Player)) return true;
			
			Player p = (Player) sender;
			String player = p.getName();
			String bpp = pl.prefix + player;
			if (!pl.isAuthorized(p, "bp.admin")) { //No permissions
				p.sendMessage(YELLOW + noperm);
				if (pl.advLog()) pl.log.warning(bpp + " attempted to use command: 'bptool'.");
			} 
			else {
				p.getInventory().addItem(new ItemStack(pl.config.getInt("utilitytool"), 1 )); //Give player a "utility tool"
				p.sendMessage(YELLOW + "Utility Tool added to inventory.");
				if (pl.advLog()) pl.log.info(bpp + " has been given a utility tool."); //Log to console
			}
		}
		return true;
	}
}
