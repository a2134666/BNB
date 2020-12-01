import java.awt.Graphics;
import javax.swing.ImageIcon;

public abstract class Item {
	private Zone location;
	private boolean wall;
	
	public Item(Zone l, boolean wall){
		location = l;
		this.wall = wall;
	}
	
	
	public Zone getLocation() {
		return location;
	}
	public void setLocation(Zone location) {
		this.location = location;
	}
	public boolean isWall() {		//destroyable, moveable
		return wall;
	}

	public abstract void draw(Graphics g);
}
