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
import info.kanlaki101.blockprotection.utilities.BPDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockProtection extends JavaPlugin {
	
	public final Logger log = Logger.getLogger("Minecraft");
	private final BPBlockListener blockListener = new BPBlockListener(this);
	private final BPPlayerListener playerListener = new BPPlayerListener(this);
	public List<String> Users = new ArrayList<String>();
	public List<String> UsersBypass = new ArrayList<String>();
	public BPDatabase database;
	public File configFile;
	public File friendslistFile;
	public FileConfiguration config;
    public FileConfiguration friendslist;
    public Permission permission = null;
    public String prefix = "[BlockProtection] ";
	
	public void onEnable() {
		setupPermissions();
		configFile = new File(getDataFolder(), "config.yml");
		friendslistFile = new File(getDataFolder(), "friendslist.yml");
		
		setupYAMLS(); //Create friends list and config file 
		 
		config = new YamlConfiguration();
		friendslist = new YamlConfiguration();
	    
	    loadConfig(); //Load config file
	    loadFriendsList();//Load friends list
	    
	    // start the database
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
		database.close();
		log.info(prefix + "Database saving...");
		log.info(prefix + "Disabling...");
	}
    
    public Boolean setupPermissions() { //Check for permissions plugins (VAULT)
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }
	
	public void setupYAMLS() {
		
	    if(!configFile.exists()){ //If the file doesn't exist, create it
	    	configFile.getParentFile().mkdirs();
	        copy(getResource("config.yml"), configFile);
	    }
	    
	    if(!friendslistFile.exists()){ //If the file doesn't exist, create it
	    	friendslistFile.getParentFile().mkdirs();
	        copy(getResource("friendslist.yml"), friendslistFile);
	    }
	}
	
	private void copy(InputStream in, File file) { //Copy information from existing files
	    try {
	        OutputStream out = new FileOutputStream(file);
	        byte[] buf = new byte[1024];
	        int len;
	        while((len=in.read(buf))>0){
	            out.write(buf,0,len);
	        }
	        out.close();
	        in.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void saveConfig() { //Save configuration file
	    try {
	        config.save(configFile);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void saveFriendsList() { //Save friends list
	    try {
	    	friendslist.save(friendslistFile);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void loadConfig() { //Load configuration file
	    try {
	        config.load(configFile);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void loadFriendsList() { //Load friends list
	    try {
	    	friendslist.load(friendslistFile);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public boolean advLog() {
	Boolean log = config.getBoolean("advlog"); 
		if (log == true) { // If advlog is set to true
			return true;
		} else {
			return false;
		}
	}
	
	public final boolean isAuthorized(CommandSender sender, String node) { //Check if player is an OP or has the correct permissions
		if (permission.has(sender, node) || sender.isOp()) return true;
		return false;
	}
	
}