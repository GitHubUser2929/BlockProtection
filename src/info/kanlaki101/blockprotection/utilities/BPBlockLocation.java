package info.kanlaki101.blockprotection.utilities;

import java.io.Serializable;

import org.bukkit.block.Block;

public class BPBlockLocation implements Serializable{

	private static final long serialVersionUID = 1L;
	private int x,y,z;
	
	
	public BPBlockLocation(Block b) {
		x=b.getX();
		y=b.getY();
		z=b.getZ();
	}
	
	public BPBlockLocation(int x, int y, int z) {
		this.x = x;
		this.y = y; 
		this.z = z;
	}
	
	public boolean equals(Object obj) {
		BPBlockLocation o = (BPBlockLocation)obj;
		if (o.x == x && o.y == y && o.z == z)
			return true;
		return false;
	}
	
	public int hashCode() {
		return x+y+z;
	}
	
	public String toString() {
		return "("+x+","+y+","+z+")";
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getZ() {
		return z;
	}
}

