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
    	if(e.getTile() != null){
    		e.getTile().removeEntity(e);
    	}
        thingsInTile.add(e);
        e.setTile(this);
    }
    private void removeEntity(Entity e){
    	thingsInTile.remove(e);
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
    
    public Entity getTopEntity(){
    	if(thingsInTile.size() == 0)
    		return null;
    	return thingsInTile.get(thingsInTile.size() - 1);
    }
    
    public Tile getTileInDirection(int direction){
    	direction += 2;
    	direction %= 8;
    	int xOff = 0;
    	int yOff = 0;
    	
    	if(direction >= 3 && direction <= 5){
    		yOff++;
    	}
    	if(direction <=1 || direction >= 7){
    		yOff--;
    	}
    	if(direction >=1 && direction <= 3){
    		xOff--;
    	}
    	if(direction <=7 && direction >= 5){
    		xOff++;
    	}
    	xOff += getX();
    	yOff += getY();
    	if(yOff >= 0 && xOff >= 0 && yOff < getDungeon().getMap().length && xOff < getDungeon().getMap().length)
    		return getDungeon().getTile(xOff, yOff);
    	return null;
    }
}
