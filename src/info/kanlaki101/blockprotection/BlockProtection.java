package info.kanlaki101.blockprotection;

import info.kanlaki101.blockprotection.commands.BP;
import info.kanlaki101.blockprotection.commands.BPAdd;
import info.kanlaki101.blockprotection.commands.BPAdmin;
import info.kanlaki101.blockprotection.commands.BPClear;
import info.kanlaki101.blockprotection.commands.BPList;
import info.kanlaki101.blockprotection.commands.BPReload;
import info.kanlaki101.blockprotection.commands.BPRemove;
import info.kanlaki101.blockprotection.commands.BPTool;
import info.kanlaki101.blockprotection.listeners.BPBlockListener;
import info.kanlaki101.blockprotection.listeners.BPPlayerListener;
import info.kanlaki101.blockprotection.utilities.BPConfigHandler;
import info.kanlaki101.blockprotection.utilities.BPDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockProtection extends JavaPlugin {
	
	public final Logger log = Logger.getLogger("Minecraft");
	private final BPBlockListener blockListener = new BPBlockListener(this);
	private final BPPlayerListener playerListener = new BPPlayerListener(this);
	public final BPConfigHandler configHandler = new BPConfigHandler(this);
	public List<String> Users = new ArrayList<String>();
	public List<String> UsersBypass = new ArrayList<String>();
	public BPDatabase database;
    public Permission permission = null;
    public String prefix = "[BlockProtection] ";
	
	public void onEnable() {
		/*
		 * Create/Load config.yml and friendslist.yml
		 */
		configHandler.setupConfig();
		configHandler.setupFriendslist();
		
		//Setup permissions via Vault
		setupPermissions();
	    
	    //Start the database
	    File dbFile = new File(getDataFolder(), "database.db"); 
		database = new BPDatabase(this, dbFile);
		
		database.scheduleAutosave();
		
		/*
		 * Register events from the block/player listener
		 */
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.BLOCK_PLACE, blockListener, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_BREAK, blockListener, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Event.Priority.Normal, this);
		/*
		 * Register commands from both command classes
		 */
		getCommand("bp").setExecutor(new BP(this));
		getCommand("bpadmin").setExecutor(new BPAdmin(this));
		getCommand("bpreload").setExecutor(new BPReload(this));
		getCommand("bpadd").setExecutor(new BPAdd(this));
		getCommand("bpremove").setExecutor(new BPRemove(this));
		getCommand("bplist").setExecutor(new BPList(this));
		getCommand("bptool").setExecutor(new BPTool(this));
		getCommand("bpclear").setExecutor(new BPClear(this));
		log.info(prefix + "Enabling...");
	}

	public void onDisable() {
		log.info(prefix + "Database saving...");
		database.close();
		log.info(prefix + "Disabling...");
	}
    
    public Boolean setupPermissions() { //Check for permissions plugin (VAULT)
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }
	
	public boolean isAuthorized(CommandSender sender, String node) { //Check if the sender is an OP or has the correct permissions
		if (permission.has(sender, node) || sender.isOp()) return true;
		return false;
	}
	
	public boolean isAuthorized(Player player, String node) { //Check if player is an OP or has the correct permissions
		if (permission.has(player, node) || player.isOp()) return true;
		return false;
	}
	
}