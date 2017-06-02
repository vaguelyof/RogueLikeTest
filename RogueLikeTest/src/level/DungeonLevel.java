package level;
import java.util.ArrayList;

import creatures.Monster;
import gameBase.Game;
import gameEntities.Chest;
import gameEntities.Door;
import gameEntities.DownStairs;
import gameEntities.Entity;
import gameEntities.SpikeTrap;
import gameEntities.Trap;
import gameEntities.UpStairs;
import items.*;
import statusEffects.*;
/**
 * Stores all of the tiles in a single level of a dungeon. Also contains the methods for randomly generating a dungeon.
 * 
 * @author Nikolai Trintchouk, Jonathan Knowles
 * @version (a version number or a date)
 */
public class DungeonLevel
{
    private Tile[][] map;
    private ArrayList<int[]> rooms;
    private int level;
    private int region;
    private ArrayList<Integer> connectedRegions;
    private int[][] regionConnections;
    private Tile entry;
    private Tile exit;
    
    /**
     * Creates a square dungeon level of width 101 spaces.
     * @param depth Affects difficulty of spawns on the level
     */
    public DungeonLevel(int depth){
        this(depth,101);
    }
    
    /**
     * Creates a square dungeon level.
     * @param depth Affects difficulty of spawns on the level.
     * @param x width of the level
     */
    public DungeonLevel(int depth, int x){
        map = new Tile[x][x];
        level = depth;
        rooms = new ArrayList<int[]>();
        region = 1;
        connectedRegions = new ArrayList<Integer>();
    }
    
    public ArrayList<Monster> getAllMonsters(){
    	ArrayList<Monster> monsters = new ArrayList<Monster>();
    	for(int i = 0; i < map.length; i++){
    		for(int j = 0; j < map.length; j ++){
    			if( map[i][j].getTopEntity() instanceof Monster){
    				monsters.add((Monster)map[i][j].getTopEntity());
    			}
    		}
    	}
    	return monsters;
    }
    
    /**
     * Generates the dungeon level.
     */
    public void generateLevel(){
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                map[i][j] = new Tile(true, this, i, j); //runs through each tile and initializes every tile as a "rock" tile
            }
        }
        createRooms(100);
        createMaze();
    	regionConnections = new int[region][region];
        createConnectors();
        cullMaze();
        entry = map[rooms.get(0)[0]][rooms.get(0)[1]];
        entry.addEntity(new UpStairs());
        int choice = 1 + (int)((rooms.size() - 1) * Math.random());
        exit = map[rooms.get(choice)[0]][rooms.get(choice)[1]];
        exit.addEntity(new DownStairs());
        populateLevel(30);
    }
    
    private void populateLevel(int times){
    	int chests = 0;
    	int choice;
    	Entity e;
    	if (level==1){
	    	for (int i=1;i<map.length-1;i++){
	    		for (int j=1;j<map.length-1;j++){
	        		if (map[i][j].getRegion()==-1){
	        			for (int[] loc:getValidSpots(i,j)){
	        				if (map[loc[0]][loc[1]].getRegion()==1){
	        					map[loc[0]][loc[1]].addEntity(new SpikeTrap(true));
	        					i = map.length-1;
	        					j = map.length-1;
	        					break;
	        				}
	        			}
	        		}
	        	}
	    	}
    	}
    	if (level>0){
	    	for (int i=1;i<map.length-1;i++){
	    		for (int j=1;j<map.length-1;j++){
	        		if (map[i][j].getRegion()==-1&&Math.random()<0.2){
	        			addTrap(map[i][j]);
	        		}
	        	}
	    	}
    	}
    	for(int i = 0; i < times; i++){
    		choice = (int)(Math.random() * 4);
    		switch(choice){
    		case 0:
    			e = Game.createMonsterOfLevel((int)(Math.random() * 4 - 2 + level));
    			break;
    		case 1:
    			switch((int)(Math.random()*4)){
    			case 0:
    				//e = new Chest(new Armor(2, "Leather Armor"));
    				if(Math.random() > 0.5){
    					e = new Chest(new Armor(2, "Leather Armor"));
    				}
    				else if(Math.random() > 0.5){
    					e = new Chest(new Armor(4, "Mail Armor"));
    				}
    				else if(Math.random()>0.1){
    					e = new Chest(new Armor(6, "Scale Armor"));
    				}
    				else{
    					e = new Chest(new Armor(10, "Plate Armor"));
    				}
    				break;
    			case 1:
    				if(Math.random() > 0.5){
    					e = new Chest(new Weapon(1, "Sharp Stick"));
    				}
    				else if(Math.random() > 0.5){
    					e = new Chest(new Weapon(3, "Worn Club"));
    				}
    				else if(Math.random()>0.5){
    					e = new Chest(new Weapon(5, "Sword"));
    				}
    				else if(Math.random() > 0.1){
    					e = new Chest(new Weapon(7, "Greataxe"));
    				}
    				else{
    					e = new Chest(new Armor(10, "Greatsword"));
    				}
    				//e = new Chest(new Armor(6, "Scale Armor"));
    				break;
    			case 2:
    				e = new Chest(new RevivalCharm());
    				break;
    			default:
    				if (Math.random() > 0.75)
        				e = new Chest(new HealthPotion());
        			else if(Math.random() > 0.5)
        				e = new Chest(new StatusPotion("Makes the user levitate", new LevitationStatusEffect(10)));
        			else if(Math.random() > 0.2)
        				e = new Chest(new LifePotion());
        			else
        				e = new Chest(new RevivePotion());
    			}
    			chests++;
    			break;
    		case 2:
    			e = new HealthPotion();
    			break;
    		case 3:
    			e = new Gold((int)(Math.random() * 50 * (level+1))+1);
    			break;
    		default:
    			e = Game.createLevel1Monster();
    		}
    		getRandomEmptyTileInARoomExcludingSpawnRegion().addEntity(e);
    	}
    	for(int i = 0; i< chests; i++){
    		getRandomEmptyTileInARoomExcludingSpawnRegion().addEntity(new Key());
    	}
    }
    private Tile getRandomEmptyTileInARoom(){
    	int x;
    	int y;
    	Tile t;
    	while(true){
    		x = (int)(Math.random() * map.length);
    		y = (int)(Math.random() * map.length);
    		t = map[x][y];
    		if(!t.getIsRock() && t.getTopEntity() == null && isRegionRoom(t.getRegion())){
    			return t;
    		}
    	}
    }
    private Tile getRandomEmptyTileInARoomExcludingSpawnRegion(){
    	int x;
    	int y;
    	Tile t;
    	while(true){
    		x = (int)(Math.random() * map.length);
    		y = (int)(Math.random() * map.length);
    		t = map[x][y];
    		if(!t.getIsRock() && t.getTopEntity() == null && isRegionRoom(t.getRegion()) && t.getRegion() != entry.getRegion()){
    			return t;
    		}
    	}
    }
    
    /**
     * Creates rectangular rooms in the open spaces of the level.
     * @param attemptLimit The number of times the generator will attempt to place rooms.
     */
    private void createRooms(int attemptLimit){
        int x;
        int y;
        int xLen;
        int yLen;
        for(int i = 0; i < attemptLimit; i++){
            xLen = generateValidLen();
            yLen = generateValidLen();
            x = generateValidMidpoint(xLen);
            y = generateValidMidpoint(yLen);
            if(validSpace(x,y,xLen,yLen)){
                rooms.add(new int[]{x,y});
                for(int j = x-xLen; j <= x+xLen; j++){
                    for(int k = y-yLen; k <= y+yLen; k++){
                        map[j][k].setType(false);
                        map[j][k].setRegion(region);
                    }
                }
                region++;
            }
        }
    }
    
    /**
     * Prints an "image" of the dungeon level to console. Used to debug dungeon generator.
     */
    public void printMap(){
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                if(map[i][j].getIsRock())
					if (map[i][j].getRegion()==0)
						System.out.print("[]");
					else
						System.out.print("XX");
                else
                	if (map[i][j].equals(entry))
                		System.out.print("^^");
                	else if (map[i][j].equals(exit))
                		System.out.print("vv");
                	else
                		System.out.print("  ");
            }
            System.out.println();
        }
        System.out.println("Rooms: " + rooms.size());
    }
    
    /**
     * Checks that a point is a valid position for a room to be generated.
     * @param x The horizontal position of the room center
     * @param y The vertical position of the room center
     * @param xLen The horizontal width of the room
     * @param yLen The vertical length of the room
     * @return
     */
    private boolean validSpace(int x, int y, int xLen, int yLen){
        for(int i = x-xLen-1; i <= x+xLen+1; i++){
            for(int j = y-yLen-1; j <= y+yLen+1; j++){
                if(!map[i][j].getIsRock()){
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Generates a random number for an ordinate of the room of a certain dimension.
     * @param len the dimension of the room
     * @return a random ordinate for the room to exist.
     */
    private int generateValidMidpoint(int len){
        return 1+len+(2 * (int)(Math.random() * ((map.length / 2)-len)));
    }
    /**
     * generates a valid room length
     * @return a random number 2 to 7
     */
    private int generateValidLen(){
        return 2 + (int)(Math.random() * 4);
    }
    /**
     * Goes to every other point and if it is rock, starts to fill a maze.
     */
    private void createMaze(){
        for(int i = 1; i < map.length - 1; i+=2){
            for(int j = 1; j < map[0].length; j+=2){
                if(map[i][j].getIsRock()){
                    startMaze(i,j);
                    region++;
                }
            }
        }
    }
    /**
     * begins flood fill
     * @param x the starting horizontal ordinate
     * @param y the starting vertical ordinate
     */
    private void startMaze(int x, int y){
        map[x][y].setType(false);
        map[x][y].setRegion(region);
        ArrayList<int[]> validSpots = getValidSpotsOverOne(x,y);
        while(validSpots.size() > 0){
            
            int chosenOne = (int)(Math.random() * validSpots.size());
            int tempX = (validSpots.get(chosenOne)[0])/2;
            int tempY = (validSpots.get(chosenOne)[1])/2;
            map[tempX+x][tempY+y].setType(false);
            map[tempX+x][tempY+y].setRegion(region);
            fill(validSpots.get(chosenOne)[0]+x,validSpots.get(chosenOne)[1]+y,validSpots.get(chosenOne),0);
            validSpots = getValidSpotsOverOne(x,y);
        }

    }
    /**
     * recursive flood fill method for generating a maze.
     * @param x the starting horizontal ordinate
     * @param y the starting vertical ordinate
     */
    private void fill(int x, int y, int[] dir, int t){
    	if (t<0)
    		t = 0;
        map[x][y].setType(false);
        map[x][y].setRegion(region);
        ArrayList<int[]> validSpots = getValidSpotsOverOne(x,y);
    	if(dir[0]+x > 0 && dir[0]+x < map.length - 1 && dir[1]+y > 0 && dir[1]+y < map.length - 1){
    		if (map[dir[0]+x][dir[1]+y].getIsRock()){
	    		for (int i=0;i<=t*2;i++){
	        		validSpots.add(dir);
	        	}
    		}
        }
    	else{
    		t = 3;
    	}
        while(validSpots.size() > 0){
            int chosenOne = (int)(Math.random() * validSpots.size());
            int tempX = (validSpots.get(chosenOne)[0])/2;
            int tempY = (validSpots.get(chosenOne)[1])/2;
            map[tempX+x][tempY+y].setType(false);
            map[tempX+x][tempY+y].setRegion(region);
            if (!validSpots.get(chosenOne).equals(dir)){
            	if (t>2){
                    fill(validSpots.get(chosenOne)[0]+x,validSpots.get(chosenOne)[1]+y,validSpots.get(chosenOne),0);
            	}
            	else{
                    fill(validSpots.get(chosenOne)[0]+x,validSpots.get(chosenOne)[1]+y,dir,t+1);
            	}
            }
            else{
                fill(validSpots.get(chosenOne)[0]+x,validSpots.get(chosenOne)[1]+y,dir,t-1);
                t = 3;
            }
            validSpots = getValidSpotsOverOne(x,y);
        	if(dir[0]+x > 0 && dir[0]+x < map.length - 1 && dir[1]+y > 0 && dir[1]+y < map.length - 1 && map[dir[0]+x][dir[1]+y].getIsRock()){
            	for (int i=0;i<=t;i++){
            		validSpots.add(dir);
            	}
            }
        }

    }
    /**
     * Returns a list of coordinates which are valid places for the maze to continue
     * @param x horizontal middle ordinate
     * @param y vertical middle ordinate
     * @return list of valid coordinates for the maze.
     */
    private ArrayList<int[]> getValidSpotsOverOne(int x, int y){
        int[][] spots = {{-2, 0}, {2,0}, {0,-2} ,{0, 2}};
        for(int i = 0; i < 4; i++){
            if(spots[i][0]+x < 1 || spots[i][0]+x > map.length - 2 || spots[i][1]+y < 1 || spots[i][1]+y > map.length - 2 || !map[spots[i][0]+x][spots[i][1]+y].getIsRock()){
                spots[i] = null;
            }
        }
        ArrayList<int[]> validSpots = new ArrayList<int[]>();

        for(int[] a : spots){
            if(a != null){
                validSpots.add(a);
            }
        }
        return validSpots;
    }
    /**
     * Creates all doors necessary to connect all regions of the maze, plus a random number of extra doors to make the dungeon more interesting.
     */
    private void createConnectors(){
        connectedRegions.add(1);

        while(openConnector()){}
        createRandomConnectors(50);
    }
    /**
     * Generates random connectors
     * @param tries number of attemptes
     */
    private void createRandomConnectors(int tries){
        int choice;
        double doors;
        int[] region = new int[2];
        int[] t;
        ArrayList<Tile> connectors;
        for(int i = 0; i < tries; i++){
            connectors = findAllConnectors();
            if(connectors.size() == 0)
                break;
            choice = (int)(Math.random() * connectors.size());
            doors = 0;
            for (int j=0;j<getValidSpots(connectors.get(choice).getX(), connectors.get(choice).getY()).size();j++){
            	t = getValidSpots(connectors.get(choice).getX(), connectors.get(choice).getY()).get(j);
            	region[j] = map[t[0]][t[1]].getRegion();
            	for (int k=0;k<regionConnections[map[t[0]][t[1]].getRegion()].length;k++){
            		if (region[j]<=rooms.size()&&k<=rooms.size())
            			doors+=regionConnections[map[t[0]][t[1]].getRegion()][k];
            		else
            			doors+=regionConnections[map[t[0]][t[1]].getRegion()][k]*0.65;
            	}
            }
            if (areRegionsConnected(region[0],region[1])){
            	if (region[0]<=rooms.size()&&region[1]<=rooms.size())
            		doors *= 1.5;
            	else
            		doors *= 1.3;
            }
            else if (region[0]>rooms.size()||region[1]>rooms.size()){
            	doors*=0.9;
            }
            if (Math.random()*doors<2){
            	connectors.get(choice).setType(false);
            	connectors.get(choice).setRegion(-1);
            	connectors.get(choice).addEntity(new Door());
            	//if (Math.random()<0.2)
            	//	addTrap(connectors.get(choice));
                regionConnections[region[0]][region[1]]++;
                regionConnections[region[1]][region[0]]++;
            }
        }
    }
    
    /**
     * Finds all tiles which are valid doors. 
     * A valid door is connected by two empty spaces of different regions, and is not adjacent to another door.
     * @return List of all Tiles which can be connectors
     */
    private ArrayList<Tile> findAllConnectors(){
        ArrayList<Tile> connectors = new ArrayList<Tile>();
        ArrayList<Integer> nearRegions;
        ArrayList<int[]> spotsAroundTile;
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                nearRegions = new ArrayList<Integer>();
                spotsAroundTile = getValidSpots(i,j);
                for(int[] place : spotsAroundTile){
                    if(map[place[0]][place[1]].getRegion() > 0 ){
                        
                        nearRegions.add(map[place[0]][place[1]].getRegion());
                    }
                }

                for(int k = 0; k < nearRegions.size() - 1; k++){
                    if(nearRegions.get(k).equals(nearRegions.get(k+1))){
                        nearRegions.remove(k);
                        k--;
                    }
                }

                if(nearRegions.size() == 2){

                    connectors.add(map[i][j]);
                }
                for(int[] place: spotsAroundTile){
                	if(map[place[0]][place[1]].getRegion() == -1)
                		connectors.remove(map[i][j]);
                }
            }
        }
        return connectors;
    }
    /**
     * Finds all tiles connected to the currently working regions.
     * @return a List of Tile connected to the currently worked regions.
     */
    private ArrayList<Tile> getConnectorsToMainRegion(){
        ArrayList<Tile> connectors = findAllConnectors();
        //ArrayList<Integer> nearRegions;
        ArrayList<int[]> spotsAroundTile;
        int count = 0;
        for(int i = 0; i < connectors.size(); i++){
            count = 0;
            //nearRegions = new ArrayList<Integer>();
            spotsAroundTile = getValidSpots(connectors.get(i).getX(), connectors.get(i).getY());
            for(int[] place : spotsAroundTile){
                if(isMainRegion(map[place[0]][place[1]].getRegion())){
                    count++;
                }
            }
            if(count != 1){
                connectors.remove(i);
                i--;
            }
        }
        return connectors;
    }
    /**
     * Takes a random valid connector to main region and opens it. 
     * @return whether or not there were connectors to open during execution.
     */
    private boolean openConnector(){
        ArrayList<Tile> connectors = getConnectorsToMainRegion();

        if(connectors.size() == 0){
            return false;
        }
        int choice = (int)(connectors.size() * Math.random());
        
        Tile tileToOpen = connectors.get(choice);
        tileToOpen.setType(false);
        tileToOpen.addEntity(new Door());
        tileToOpen.setRegion(-1);
        //if (Math.random()<0.2)
        //	addTrap(tileToOpen);
        int[] place0 = getValidSpots(tileToOpen.getX(), tileToOpen.getY()).get(0);
        int[] place1 = getValidSpots(tileToOpen.getX(), tileToOpen.getY()).get(1);
        regionConnections[map[place0[0]][place0[1]].getRegion()][map[place1[0]][place1[1]].getRegion()]++;
        regionConnections[map[place1[0]][place1[1]].getRegion()][map[place0[0]][place0[1]].getRegion()]++;
        for(int[] place : getValidSpots(tileToOpen.getX(), tileToOpen.getY())){
            connectedRegions.add(map[place[0]][place[1]].getRegion());
        }
        return true;
    }
    /**
     * checks if a region is in the currently working regions
     * @param r region about which to inquire
     * @return true if the region is in the current regions.
     */
    private boolean isMainRegion(int r)
    {
        for (Integer i:connectedRegions){
            if (r==i){
                return true;
            }
        }
        return false;
    }
    /**
     * finds all empty spots cardinally adjacent to a point
     * @param x horizontal ordinate of point
     * @param y vertical ordinate of point
     * @return List of empty spots
     */
    private ArrayList<int[]> getValidSpots(int x, int y){
        int[][] spots = {{x-1, y}, {x+1,y}, {x,y-1} ,{x, y+1}};
        for(int i = 0; i < 4; i++){
            if(spots[i][0] < 1 || spots[i][0] > map.length - 2 || spots[i][1] < 1 || spots[i][1] > map.length - 2 || map[spots[i][0]][spots[i][1]].getIsRock()){
                spots[i] = null;
            }
        }
        ArrayList<int[]> validSpots = new ArrayList<int[]>();

        for(int[] a : spots){
            if(a != null){
                validSpots.add(a);
            }
        }
        return validSpots;
    }
    /**
     * Removes all 1 wide dead end paths.
     */
    private void cullMaze(){
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                if(!map[i][j].getIsRock() && getValidSpots(i,j).size() <= 1){
                    map[i][j].setType(true);
                    i=0;
                    j=0;
                }
            }
        }
    }
    
    public Tile[][] getMap(){
    	return map;
    }
    
    public Tile getTile(int x, int y){
		if(x >= 0 && x < map.length && y >= 0 && y < map.length){
			return map[x][y];
		}
		else
			return null;
    }
    
    private boolean areRegionsConnected(int r0, int r1){
    	if (regionConnections[r0][r1]>0){
    		return true;
    	}
    	return false;
    }
    
    private int regionConnections(int r){
    	int r0 = 0;
    	for (int i:regionConnections[r]){
    		r0+=i;
    	}
    	return r0;
    }
    
    public boolean isRegionRoom(int region){
    	return region<=rooms.size()&&region>0;
    }
    
    public Tile getEntrance(){
    	return entry;
    }
    public Tile getExit(){
    	return exit;
    }
    
    private void addTrap(Tile tile){
    	for (int[] t:getValidSpots(tile.getX(),tile.getY())){
    		if (map[t[0]][t[1]].getRegion()<=rooms.size()
    				&& map[t[0]][t[1]].getTopEntity() == null
    				&& map[t[0]][t[1]].getRegion()>0
    				&& !areRegionsConnected(map[t[0]][t[1]].getRegion(),0)
    				&& regionConnections(map[t[0]][t[1]].getRegion())>1){
    			if (level==1){
    				map[t[0]][t[1]].addEntity(new Trap());
    				return;
    			}
    			Trap trap;
    			switch((int)(Math.random()*4)){
    			case 1:
    				trap = new Trap(new FrozenStatusEffect(3));
    				break;
    			case 2:
    				trap = new Trap(new BurnStatusEffect(5));
    				break;
    			case 3:
    				trap = new SpikeTrap(2);
    				break;
    			default:
    				trap = new Trap(new PoisonStatusEffect(5));
    				break;
    			}
    			map[t[0]][t[1]].addEntity(trap);
    		}
    	}
    }
   
}
