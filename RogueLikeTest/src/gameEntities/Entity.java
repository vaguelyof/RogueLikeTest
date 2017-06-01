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
    public String getName();
    
    public String getDescription();
    
    public Tile getTile();
    
    public void setTile(Tile t);
    
    public char getChar();
    
    public Color getColor();
    
}
