package level;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import creatures.Creature;
import creatures.Player;
import gameBase.Game;
import gameEntities.Door;
import gameEntities.Entity;
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
        if (true)
        	setRegion(0);
    }
    
    public boolean getIsRock(){
        return isRock;
    }
    
    public void addEntity(Entity e){
    	if(e != null){
	    	if(e.getTile() != null){
	    		e.getTile().removeEntity(e);
	    	}
	        thingsInTile.add(e);
	        e.setTile(this);
    	}
    }
    public void removeEntity(Entity e){
    	thingsInTile.remove(e);
    	e.setTile(null);
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
    
    public ArrayList<Entity> getEntities(){
    	return thingsInTile;
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
    
    /**
     * finds tiles to the North, North-East, East, South-East, South, South-West, West, and North-West
     * of a given Tile t
     * 
     * precondition: Tile t must be a valid Tile object in containedDungeon
     * 
     *  @param t a Tile object within containedDungeon
     *  
     *  @return an ArrayList<Tile> with adjacent tiles listed clockwise from NORTH; any out of bound tiles are listed as null
     */
    public ArrayList<Tile> getAdjacentTiles(){
    	
    	ArrayList<Tile> Tiles = new ArrayList<Tile>();
    	
    	for(int i = Game.NORTH; i <= Game.NORTH_WEST; i++)
    	{
    		Tiles.add(getTileInDirection(i));
    	}
    	
    	return Tiles;
    }
    
    public String toString(){
    	String des;
    	if(isRock){
    		return "There is an impassable wall here";
    	}
    	if(getTopEntity() != null){
    		des = new String(getTopEntity().getName());
    		if(getTopEntity().getDescription() != null && getTopEntity().getDescription().length() > 0){
    			des = des + ": " + getTopEntity().getDescription();
    		}
    		return des;
    	}
    	
    	return "There is nothing here.";
    }
    
    public int getDirectionToTile(Tile t){
    	int dx = t.getY() - getY();
        int dy = t.getX() - getX();
        // y axis points opposite to mathematical orientation
        int angle = (int) Math.toDegrees(Math.atan2(-dy, dx));
        // mathematical angle is counterclockwise from x-axis,
        // compass angle is clockwise from y-axis
        int compassAngle = Game.EAST - angle/45;
        // prepare for truncating division by 45 degrees
        //compassAngle += Game.NORTH_EAST / 2;
        // wrap negative angles
        compassAngle%=8;
        return compassAngle;
    }
    /*
     * https://en.wikipedia.org/wiki/Pathfinding
     * 
     */
    public ArrayList<StepTile> getRouteToTile(Tile t)
    {
    	int currentStep = 1;
    	
    	ArrayList<StepTile> route = new ArrayList<StepTile>();
    	ArrayList<StepTile> temp = new ArrayList<StepTile>();
    	while(routeHasTarget(route, t))
    	{
	    	temp = getAdjacentStepTiles(currentStep);
    		route = removeDuplicates(route, temp);
    	}
    	
    	return route;
    }
    
    /*
     * merges arrays and removes duplicates
     */
    private ArrayList<StepTile> removeDuplicates(ArrayList<StepTile> a1, ArrayList<StepTile> a2)
    {
    	// add everything to a HashSet
    	Set<StepTile> hs = new HashSet<StepTile>();	//set does not allow duplicates
    	hs.addAll(a1);
    	hs.addAll(a2);
    	a2.clear();		//reset array
    	a2.addAll(hs);	//add the set onto the array
    					//array now has the entire route  
    	return a2;
    }
    
    private boolean routeHasTarget(ArrayList<StepTile> route, Tile t)
    {
    	for(Tile k : route)
    	{
    		if(k == t)
    			return true;
    	}
    	return false;
    }
    
    /*
     * currentStep must be between 0 and route.size() - 1, inclusive
     */
    public Tile getNextTile(Tile t)
    {
    	for(StepTile k : getRouteToTile(t))
    	{
    		if (k.getStep() == 1)
    			return k;
    	}
    	//should only execute if already at target tile
    	return t;
    }
    
    private ArrayList<StepTile> getAdjacentStepTiles(int step)
    {
    	Tile t = null;
    	ArrayList<StepTile> Tiles = new ArrayList<StepTile>();
    	
    	for(int i = Game.NORTH; i <= Game.NORTH_WEST; i++)
    	{
    		t = getTileInDirection(i);
    		
    		if(!isRock && !(t.getTopEntity() instanceof Creature))
    			Tiles.add(new StepTile(t.getIsRock(), t.getDungeon(), t.getX(), t.getY(), step));
    	}
    	
    	return Tiles;
    }
    
    public boolean isInRoom()
    {
    	return containedDungeon.isRegionRoom(myRegion);
    }
    
    /*
	 * returns true if a tile with a door
	 */
	public boolean hasOpenDoor()
	{
		boolean hasDoor = false;
		boolean hasPlayer = false;
		
		for(Entity e : getEntities())
		{
			if(e instanceof Door)
				hasDoor = true;
			
			if(e instanceof Player)
				hasPlayer = true;
				
		}
		return hasDoor && hasPlayer;
	}
}
