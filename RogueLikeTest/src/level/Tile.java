package level;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import creatures.Creature;
import creatures.Player;
import gameBase.Game;
import gameEntities.Door;
import gameEntities.Entity;
import gameEntities.Trap;
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
    	if (e instanceof Creature){
    		if (hasTrap())
    			activateTrap((Creature)e);
    		if (e instanceof Player)
    			checkForNearbyTraps((Player)e);
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
     * of a given Tile
     * 
     * precondition: Tile must be a valid Tile object in containedDungeon
     *   
     *  @return an ArrayList<Tile> with adjacent tiles listed clockwise from NORTH
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
    	if (hasTrap()){
	    	for (int i=0;i<thingsInTile.size();i++){
	    		if (thingsInTile.get(i) instanceof Trap && !((Trap)thingsInTile.get(i)).isVisible()){
	    			Trap.reveal();
	    			return "Found a trap!"; //there should only be one trap per tile
	    		}
	    	}
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
    
    public boolean hasTrap(){
    	for (int i=0;i<thingsInTile.size();i++){
    		if (thingsInTile.get(i) instanceof Trap)
    			if (((Trap) thingsInTile.get(i)).isArmed())
    				return true;
    	}
    	return false;
    }
    
    private void activateTrap(Creature e){
		if (e.hasEffect(1)) {
	    	for (int i=0;i<thingsInTile.size();i++){
	    		if (thingsInTile.get(i) instanceof Trap && ((Trap)thingsInTile.get(i)).isRevealable() && e instanceof Player){
	    			((Trap)thingsInTile.get(i)).reveal();
	    			((Player)e).getGame().logMessage("You noticed a trap!",Color.YELLOW);
	    			return; //there shouldn't be more than one trap
	    		}
	    	}
		}
		else {
	    	for (int i=0;i<thingsInTile.size();i++){
	    		if (thingsInTile.get(i) instanceof Trap){
		    		if (e instanceof Player)
	    				((Player)e).getGame().logMessage("You stepped on a "+thingsInTile.get(i).getName()+"!", Color.RED);
	    			((Trap)thingsInTile.get(i)).trigger((Creature)e);
	    			return; //there shouldn't be more than one trap
	    		}
	    	}
		}
    }
    
    private void checkForNearbyTraps(Player e){
    	Tile t;
		for (int j=0;j<8;j++){
			t = this.getTileInDirection(j);
			if (t.hasTrap()){
		    	for (Entity ent: t.thingsInTile){
		    		if (ent instanceof Trap && ((Trap)ent).isRevealable() && Math.random()<0.5){
		    			((Trap)ent).reveal();
		    			e.getGame().logMessage("You noticed a trap!",Color.YELLOW);
		    		}
		    	}
			}
		}
    }
    
    public boolean disarmTrap(){
		if (this.hasTrap()){
		    for (Entity ent: thingsInTile){
		    	if (ent instanceof Trap && ((Trap)ent).isVisible()){
		    		((Trap)ent).disarm();
		    		return true;
		    	}
		    }
		}
		return false;
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
     * this idea is garbage when in a room because it tries to fill all the spaces as if they're each a valid step to the goal
     * which technically, they are
     * 
     */
    //public ArrayList<StepTile> getRouteToTile(Tile t)
   // {
    	/*StepTile st;
    	int currentStep = 0;
    	ArrayList<StepTile> route = new ArrayList<StepTile>();
    	do	
    	{
    		st = new StepTile(getIsRock(), getDungeon(), getX(), getY(), currentStep);
    		route.add(st.getNextTileTowardsTarget());
    		currentStep++;
    	}
    	while(!st.isNextTo(t));
    	
    	return route;*/
  //  }
    
    public ArrayList<Tile> getEmptyAdjacentMazeTiles()
    {
    	Tile t;
		ArrayList<Tile> Tiles = new ArrayList<Tile>();
    	
    	//looks all around this tile for empty spaces in a maze
    	for(int i = Game.NORTH; i <= Game.NORTH_WEST; i++)
    	{
    		t = getTileInDirection(i);
    		
    		if(!t.getIsRock() && !(t.getTopEntity() instanceof Creature) && !t.isInRoom())
    			Tiles.add(t);
    	}
    	
    	return Tiles;
    }
    
    public ArrayList<Tile> getEmptyAdjacentRoomTiles()
    {
    	Tile t;
		ArrayList<Tile> Tiles = new ArrayList<Tile>();
    	
    	//looks all around this tile for empty spaces in a maze
    	for(int i = Game.NORTH; i <= Game.NORTH_WEST; i++)
    	{
    		t = getTileInDirection(i);
    		
    		if(!t.getIsRock() && !(t.getTopEntity() instanceof Creature) && t.isInRoom())
    			Tiles.add(t);
    	}
    	
    	return Tiles;
    }
    
	public Tile getNextTileInMazeTowardTile(Tile t)
    {		
		for(Tile k : getEmptyAdjacentMazeTiles())
    	{
			//if Tile k can reach the target within 10 steps, it's good enough to be the next one to go to
    		if (k.reachesTile(this, t))
    			return k;
    	}
		return getTileInDirection(getDirectionToTile(t));
    }
	
	/*
	 * checks if a route to a tile is possible within the next 10 steps
	 * 
	 * returns true if route to Tile t can be reached within 10 steps
	 * returns false if route does not reach Tile t within 10 steps
	 */
	private boolean reachesTile(Tile previous, Tile target)
	{
		ArrayList<Tile> possibleTiles = new ArrayList<Tile>();
		
		//creates an array of 10 tiles ahead of current tile
		for(int i = 0; i < 10; i++)
		{
			for(Tile t: getEmptyAdjacentMazeTiles())
			{
				//will not add tile to array if it is the same as the previous tile
				if(!(t.getX() == previous.getX() && t.getY() == previous.getY()))
				{
					possibleTiles.add(t);
					previous = new Tile(t.getIsRock(), t.getDungeon(), t.getX(), t.getY());	//updates previous to last tile added
																							//this way no repeats are added
				}
			}
		}
		
		//if the target Tile t is within the newly created possibleTiles array
		//returns true
		for(Tile k: possibleTiles)
		{
			if(k.getX() == target.getX() && k.getY() == target.getY())
				return true;
		}
		return false;
	}
	
    public boolean isInRoom()
    {
    	return containedDungeon.isRegionRoom(myRegion);
    }
    
    public Tile getAdjacentDoor()
    {
    	for(Tile t : getAdjacentTiles())
    	{
    		if(t.hasDoor())
    			return t;
    	}
    	return this;
    }
    
    /*
     * returns true if this tile is adjacent to the target tile
     * false is otherwise
     */
    public boolean isNextTo(Tile target)
    {
    	for(Tile t : target.getAdjacentTiles())
    	{
    		if(getX() == t.getX() && getY() == t.getX())
    			return true;
    	}
    	return false;
    }
    
    /*
	 * returns true if a tile with a door
	 */
	public boolean hasDoor()
	{
		boolean hasDoor = false;
		
		for(Entity e : getEntities())
		{
			if(e instanceof Door)
				hasDoor = true;
				
		}
		return hasDoor;
	}
}
