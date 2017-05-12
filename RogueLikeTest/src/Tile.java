import java.util.ArrayList;
/**
 * A Tile in a dungeon. Has a coordinate position and a list of Entities in it.
 * 
 * @author Nikolai Trintchouk
 * @version 5/6/2017
 */
public class Tile
{
    private ArrayList<Entity> thingsInTile;
    private boolean isRock; 
    private DungeonLevel containedDungeon;
    private int myRegion;
    private int myY;
    private int myX;
    
    /**
     * Creates a tile.
     * @param type true if impassable rock
     * @param dungeon the DungeonLevel in which the tile exists
     * @param x horizontal ordinate
     * @param y vertical ordinate
     */
    public Tile(boolean type, DungeonLevel dungeon, int x, int y){
        isRock = type;
        containedDungeon = dungeon;
        myRegion = 0;
        thingsInTile = new ArrayList<Entity>();
        myY = y;
        myX = x;
    }
    
    /**
     * Sets whether or not the tile is an impassable rock
     * @param type true if rock
     */
    public void setType(boolean type){
        isRock = type;
    }
    
    public boolean getIsRock(){
        return isRock;
    }
    
    public void addEntity(Entity e){
        thingsInTile.add(e);
        e.setTile(this);
    }
    
    public void setRegion(int region){
        myRegion = region;
    }
    
    public int getRegion(){
        return myRegion;
    }
    
    public int getX(){
        return myX;
    }
    
    public int getY(){
        return myY;
    }
    
    public DungeonLevel getDungeon(){
    	return containedDungeon;
    }
}
