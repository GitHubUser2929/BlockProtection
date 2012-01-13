package info.kanlaki101.blockprotection.listeners;

import info.kanlaki101.blockprotection.BlockProtection;
import info.kanlaki101.blockprotection.utilities.BPBlockLocation;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;

public class BPPlayerListener extends PlayerListener{
	
	public static BlockProtection pl;

	public BPPlayerListener(BlockProtection instance) {
		pl = instance;
	}
	ChatColor YELLOW = ChatColor.YELLOW;
	
	public void onPlayerJoin(PlayerJoinEvent event) {
		String player = event.getPlayer().getName();
		if (pl.config.getBoolean("enable-by-default") == true) { //If block protection is enabled by default
			pl.Users.add(player); //Add player name to the array list
		}
		if (pl.config.getBoolean("enable-bypass-by-default") == true) { //If they want admin bypass on by default
			pl.UsersBypass.add(player); //Add player name to the bypass array list
		}
	}
	
	public void onPlayerQuit(PlayerQuitEvent event) {
		String player = event.getPlayer().getName();
		if (pl.Users.contains(player)) { //Check if the block protection array contains that player
			pl.Users.remove(player); //Remove player name from the array list
		} 
		else if (pl.UsersBypass.contains(player)) {
			pl.UsersBypass.remove(player); //Remove mod/admin from the bypass array list
		}
	}
	
	
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getItem() == null)
			return;
		
		//Checks if the player is using the utility tool, and if they have the permission to view block information
		if (e.getItem().getTypeId() == pl.config.getInt("utilitytool"))
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK)
				if (p.hasPermission("bp.user"))
					blockInfo(e);
		
		//Checks if the player is using the utility tool, and if they have the permission to add blocks to the database
		if (e.getItem().getTypeId() == pl.config.getInt("utilitytool"))
			if (e.getAction() == Action.LEFT_CLICK_BLOCK)
				if (p.hasPermission("bp.admin"))
					addBlock(e);
		
	}
	
	
	private void blockInfo(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		BPBlockLocation blockLoc = new BPBlockLocation(e.getClickedBlock());
		if (pl.database.containsKey(blockLoc)) { //Look for the block in the database and display info if available
			p.sendMessage(YELLOW + "Block owned by: " + pl.database.get(blockLoc) + ".");
		}
		else {
			p.sendMessage(YELLOW + "Block not owned.");
		}
	}
	
	private void addBlock(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		BPBlockLocation blockLoc = new BPBlockLocation(e.getClickedBlock());
		int blockID = e.getClickedBlock().getTypeId();
		
		if (pl.database.containsKey(blockLoc)) { //Check for the block in the database
			p.sendMessage(YELLOW + "Can not add. Block already owned.");
		}
		else {
			if (!pl.config.getList("exclude").contains(blockID)) {
				pl.database.put(blockLoc, e.getPlayer().getName()); //Adds block to the database.
				p.sendMessage(YELLOW + "Block added to the database.");
			}
			else {
				p.sendMessage(YELLOW + "Can not add block. It is on the exclude list.");
			}
		}
	}
	
}