package gameEntities;
import java.awt.Color;

import level.Tile;
/**
 * Used for all "things" in the dungeon. Doors, monsters, items, etc. Has a name, description, and a location.
 * 
 * @author Nikolai Trintchouk
 * @version 5/6/2017
 */
public abstract class Entity
{
	private Tile myTile;
	
    public abstract String getName();
    
    public abstract String getDescription();
    
    public abstract char getChar();
    
    public abstract Color getColor();
	
	public Tile getTile() {
		return myTile;
	}

	public void setTile(Tile t) {
		myTile = t;
	}
    
}
