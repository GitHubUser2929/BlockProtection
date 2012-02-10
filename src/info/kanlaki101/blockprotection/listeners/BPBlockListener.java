package info.kanlaki101.blockprotection.listeners;

import info.kanlaki101.blockprotection.BlockProtection;
import info.kanlaki101.blockprotection.utilities.BPBlockLocation;
import info.kanlaki101.blockprotection.utilities.BPConfigHandler;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BPBlockListener extends BlockListener {
	public static BlockProtection pl;

	public BPBlockListener(BlockProtection instance) {
		pl = instance;
	}
	
	public void onBlockPlace(BlockPlaceEvent e) {
		String player = e.getPlayer().getName();
		
		if (pl.Users.contains(player)) { //Checks if user has BlockProctection enabled/disabled
			Block block = e.getBlockPlaced();
			int blockID = block.getTypeId();
			if (!BPConfigHandler.getBlacklist().contains(blockID)) { //Check is the block is in the exclude list
				BPBlockLocation blockLoc = new BPBlockLocation(block);
				pl.database.put(blockLoc, e.getPlayer().getName()); //Saves block to the database
			}
		}
	}
	
	public void onBlockBreak(BlockBreakEvent e) {	
		Block block = e.getBlock();
		BPBlockLocation blockLoc = new BPBlockLocation(block);
		Player p = e.getPlayer();
		String player = p.getName();
		String blockowner = pl.database.get(blockLoc);
		
		if (pl.database.containsKey(blockLoc)) { //Checks to see if the block is in the database
			if (!pl.database.get(blockLoc).equals(player)) { //If player trying to destroy the block isn't the owner
				
				if (!(BPConfigHandler.getFriendslist(blockowner) == null)) { //Check if block owner has a friends list
					if (BPConfigHandler.getFriendslist(blockowner).contains(player)) {
						pl.database.remove(blockLoc); //Allow player to break block
					}
					else {
						e.setCancelled(true); //Cancel the block break. Player is not owner or friend
						p.sendMessage(ChatColor.YELLOW + "Bloco portegido por: " + blockowner);
					}
				}
				else if (pl.UsersBypass.contains(player)) { //Is player has admin bypass enabled
					pl.database.remove(blockLoc); //Allows player to break block and removes it from the database
				}
				else {							
					e.setCancelled(true); //Cancel the block break. Player is not owner, friends, or does not have permission to ignore ownership
					p.sendMessage(ChatColor.YELLOW + "Bloco protegido por: " + blockowner);
				} 
			}
			else //Player is owner of the block
				pl.database.remove(blockLoc); //Break the block and remove it from the database
		}
		
		/*
		*Check if the player is holding the "utility-tool" and has the admin permission. If so, do not let him break blocks"
		*This is used for things that break instantly (redstone, saplings, etc)
		*/
		if (p.getItemInHand().getTypeId() == BPConfigHandler.getUtilTool()) {
			if (pl.isAuthorized(p, "bp.admin")) e.setCancelled(true);
		}
	}
}