package info.kanlaki101.blockprotection.listeners;

import info.kanlaki101.blockprotection.BlockProtection;
import info.kanlaki101.blockprotection.utilities.BPBlockLocation;
import info.kanlaki101.blockprotection.utilities.BPConfigHandler;

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
		BPConfigHandler.loadConfig();
		if (BPConfigHandler.enableByDefault() == true) pl.Users.add(player); //Enable automatic protection for that player
		if (BPConfigHandler.bypassByDefault() == true) pl.UsersBypass.add(player); //Enable automatic bypass for that admin
	}
	
	public void onPlayerQuit(PlayerQuitEvent event) {
		String player = event.getPlayer().getName();
		if (pl.Users.contains(player)) pl.Users.remove(player); //Remove them from the protection array
		if (pl.UsersBypass.contains(player)) pl.UsersBypass.remove(player); //Remove the admin from the bypass array
	}
	
	
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getItem() == null) return;
		int item = p.getItemInHand().getTypeId();
		
		if (BPConfigHandler.getUtilTool() == item) {
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (!pl.isAuthorized(p, "bp.user")) return;
				blockInfo(e); //If player has permission, show them who owns the block
			}
			
			if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
				if (!pl.isAuthorized(p, "bp.admin")) return;
				addBlock(e); //If player has permission, TRY to add the block to the database
			}
		}
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
			if (!BPConfigHandler.getBlacklist().contains(blockID)) {
				pl.database.put(blockLoc, p.getName()); //Adds block to the database.
				p.sendMessage(YELLOW + "Block added to the database.");
			}
			else {
				p.sendMessage(YELLOW + "Can not add block. It is on the blacklist.");
			}
		}
	}
	
}