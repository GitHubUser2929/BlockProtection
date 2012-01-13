package info.kanlaki101.blockprotection.utilities;

import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;
import java.io.*;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * Database file is saved in the following format:
 * bytes: 4 - 4 - 4 - variable (null-terminated)
 * data:  x - y - z - player
 */
public class BPDatabase {

	HashMap<BPBlockLocation, String> database = new HashMap<BPBlockLocation, String>();
	File dbFile;
	Logger log = Logger.getLogger("Minecraft");
	JavaPlugin plugin;
	String prefix = "[BlockProtection] ";
	
	// File f is the database file
	public BPDatabase(JavaPlugin plugin, File f) {
		this.plugin = plugin;
		dbFile = f;
		f.getParentFile().mkdirs();
		// load the database's file, or create a new one if it doesn't exist
		try {
			if(dbFile.createNewFile())
				log.info(prefix + "Created new database file.");
			else
				log.info(prefix + "Using existing database file.");
			inflate();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void scheduleAutosave() {
		int time = plugin.getConfig().getInt("save-interval") * 60;
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

			public void run() {
				Bukkit.getServer().broadcastMessage(ChatColor.YELLOW + prefix + "Saving database. Expect some lag ...");
				save();
				log.info(prefix + "Database saved.");
				
			}
			
		}, time * 20L, time * 20L);
	}
	
	private void save() {
		try {
			// delete existing database
			dbFile.delete();
			
			RandomAccessFile rf = new RandomAccessFile(dbFile, "rw");
			
			// helps to iterate over all blocks protected
			Iterator<BPBlockLocation> it = database.keySet().iterator();
			while(it.hasNext()) {
				BPBlockLocation currBlock = it.next();
				rf.writeInt(currBlock.getX());
				rf.writeInt(currBlock.getY());
				rf.writeInt(currBlock.getZ());
				rf.writeChars(database.get(currBlock));
				rf.writeChar('\0'); // null-termination
			}
		}
		catch (IOException ex) {
			log.warning(prefix + "Could not save the database file.");
		}
	}
	
	// Reads the values into the database's hashmap
	private void inflate() {
		try {
			RandomAccessFile rf = new RandomAccessFile(dbFile, "r");
			while(true) {
				int x = rf.readInt();
				int y = rf.readInt();
				int z = rf.readInt();
				String player = "";
				// loop over the player's name, look for null termination
				while(true) {
					char curr = rf.readChar();
					if(curr == '\0')
						break;
					player += curr;
				}
				database.put(new BPBlockLocation(x,y,z), player);
			}
		}
		catch (EOFException ex) {
			log.info(prefix + "Finished loading database.");
		}
		catch (IOException ex) {
			log.warning(prefix + "Error while reading the database.");
			ex.printStackTrace();
		}
	}
	
	public void close() {
		save();
	}
	
	public void put(BPBlockLocation key, String value) {
		database.put(key, value);
	}
	
	public boolean containsKey(BPBlockLocation key) {
		return database.containsKey(key);
	}
	
	public String get(BPBlockLocation key) {
		return database.get(key);
	}
	
	public void remove(BPBlockLocation key) {
		database.remove(key);
	}
}
