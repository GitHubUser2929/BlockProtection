package info.kanlaki101.blockprotection.utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import info.kanlaki101.blockprotection.*;

public class BPConfigHandler {
	
    private static BlockProtection plugin;

    public BPConfigHandler(BlockProtection plugin) {
        BPConfigHandler.plugin = plugin;
    }
    
    public static File configFile;
    public static File friendslistFile;
    public static FileConfiguration config;
    public static FileConfiguration friendslist;
    

	public void setupConfig() {
		
		configFile = new File(plugin.getDataFolder(), "config.yml");
	    config = new YamlConfiguration();
	    
	    if(!configFile.exists()) {
	    	configFile.getParentFile().mkdirs();
	        copy(plugin.getResource("config.yml"), configFile);
	    }
	}
	
	public void setupFriendslist() {
		
		friendslistFile = new File(plugin.getDataFolder(), "friendslist.yml");
	    friendslist = new YamlConfiguration();
		
		if(!friendslistFile.exists()) {
			friendslistFile.getParentFile().mkdirs();
			copy(plugin.getResource("friendslist.yml"), friendslistFile);
		}
	}
	
	private void copy(InputStream in, File file) {
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
	
	public static void saveConfig() { //Save configuration file
	    try {
	        config.save(configFile);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public static void saveFriendsList() { //Save friends list
	    try {
	    	friendslist.save(friendslistFile);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public static void loadConfig() { //Load configuration file
	    try {
	        config.load(configFile);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public static void loadFriendsList() { //Load friends list
	    try {
	    	friendslist.load(friendslistFile);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	/*
	 * Pull data from the configuration file
	 */
	
	public static boolean enableByDefault() {
		return config.getBoolean("enable-by-default");
	}
	
	public static boolean bypassByDefault() {
		return config.getBoolean("enable-bypass-by-default");
	}
	
	public static boolean advLog() {
		return config.getBoolean("advlog"); 
	}
	
	public static List<Object> getBlacklist() {
		return config.getList("blacklist");
	}
	
	public static int getUtilTool() {
		return config.getInt("utility-tool");
	}
	
	/*
	 * Pull information from a player's friends list
	 */
	
	public static List<Object> getFriendslist(String player) {
		return friendslist.getList(player);
	}
	
}
