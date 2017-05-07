
/**
 * Used for all "things" in the dungeon. Doors, monsters, items, etc. Has a name, description, and a location.
 * 
 * @author Nikolai Trintchouk
 * @version 5/6/2017
 */
public interface Entity
{
    public String getName();
    
    public String getDescription();
    
    public Tile getTile();
    
    public void setTile(Tile t);
}
