package creatures;
import java.awt.Color;
import java.util.ArrayList;

import gameBase.Game;
import gameEntities.Door;
import level.StepTile;
import level.Tile;
import statusEffects.StatusEffect;

public class Monster extends Creature{
	
	protected int speed;	//how many blocks the creature moves per turn
	protected boolean canAct;
	protected Tile lastSeen;
	
	/**
	 * Monster constructor
	 * @param aName Monster name
	 * @param description Description text
	 * @param health MaxHealth
	 * @param dmg attack power
	 */
	public Monster(String aName, String description, int health, int dmg)
	{
		super(aName, description, health, dmg);
		setChar('!');
		setColor(Color.RED);
		speed = 1;
		canAct = true;
		lastSeen = null;
	}
	
	/**
	 * monster acts in a step by step process
	 * 1. applies any current status effects
	 * 2. dies if its health is <= 0
	 * 3. checks if it can attack a player, and attacks if it can
	 * 4. checks if it can see the player, and moves toward it if it can
	 * 5. moves toward last known location if it has seen the player
	 */
	public void act()
	{		
		tickAllEffects();
		if(getHealth() <= 0){
			die();
			return;
		}
		
		if (isStunned())
			return;
		
		ArrayList<Tile> seeable = new ArrayList<Tile>();
		
		seeable = Game.calcFOV(this,14);
		
		//remove the canAct check if you want the monster to be able to attack every turn
		//but still wait to move
		if(canSeePlayer() && canAct){
			
			//if the monster is next to the player, it attacks
			for(Tile t : getTile().getAdjacentTiles())
			{
				if(tileHasPlayer(t))
				{
					attack((Creature)t.getTopEntity());
					canAct = false;		//monster must wait one turn after attacking
					return;
				}
			}
		}
	
			//if the monster can see the player and has a direct path to the player
			//monster moves towards player
			if(canSeePlayer())
			{
				for (Tile t : seeable)			//checks all seeable tiles for the player
				{
					if(tileHasPlayer(t))
					{
						//stores a simple tile location as instance variable for use in a room
						lastSeen = new Tile(t.getIsRock(), t.getDungeon(), t.getX(), t.getY());
						
						//if the monster is in a room and the player is on a door
						//or if the monster and the player are in a room together, the monster moves directly toward the player
						//no questions asked
						if(getTile().isInRoom() && (t.isInRoom() || t.hasDoor()))
						{
							move((getTile().getDirectionToTile(t)));						
							return;		//these returns indicate the end of the monster's turn
						}
						
						//should only execute if the monster is in a maze
						if(!getTile().isInRoom() && !getTile().hasDoor())
						{						
							move(getTile().getDirectionToTile(getTile().getNextTileInMazeTowardTile(lastSeen)));
							return;
						}
						
						//if the monster is in on a door and the player is in a maze, it moves into the maze automatically
						if(getTile().hasDoor() && !t.isInRoom())
						{
							move(getTile().getDirectionToTile(getTile().getEmptyAdjacentMazeTiles().get(0)));
							return;
						}
						
						if(getTile().hasDoor() && t.isInRoom())
						{
							//tries to move directly toward the tile, but moves to the nearest room if it can't
							if(!move(getTile().getDirectionToTile(t)))
									move(getTile().getDirectionToTile(getTile().getEmptyAdjacentRoomTiles().get(0)));
							return;
						}
					}
				}
			}
			//to continue to chase the player, the monster must have a tile stored in lastSeen
			
			//if the monster has reached lastSeen without seeing the player, it forgets lastSeen and stops moving
			if(lastSeen != null && lastSeen.getX() == getTile().getX() && lastSeen.getY() == getTile().getY())
			{
				lastSeen = null;
				return;
			}
			
			//if the monster has lost sight of the player and is in a room
			//it goes directly toward door near lastSeen
			else if(getTile().isInRoom() && lastSeen != null)
			{
				move(getTile().getDirectionToTile(lastSeen.getAdjacentDoor()));
				return;
			}
			
			//if the monster has lost sight of a player in a maze
			//it sets a route to lastSeen
			//increasing the step of the tile it's at each time it moves
			else if(lastSeen != null)
			{
				move(getTile().getDirectionToTile(getTile().getNextTileInMazeTowardTile(lastSeen)));
				return;
			}
		}
	

	protected boolean tileHasPlayer(Tile t){
		return (t.getTopEntity() instanceof Player);
	}
	
	/*
	 * checks if monster can see player
	 * 
	 * @return true if monster can see player
	 * @return false if monster cannot see player
	 * 
	 */
	protected boolean canSeePlayer(){
		for(Tile t : Game.calcFOV(this,14)){
			if(tileHasPlayer(t))
				return true;
		}
		return false;
	}

	/*
	 * returns true if monster changed tile
	 * returns false otherwise
	 */
	protected boolean move(int direction){
		if(canAct){
			for (int i = 0; i < speed; i++){
				if (Game.creatureCanMoveInDirection(this, direction)) {
					getTile().getTileInDirection(direction).addEntity(this);
					return true;
				}
				if (Game.creatureCanMoveInDirection(this, direction - 2)) {
					getTile().getTileInDirection(direction - 2).addEntity(this);
					return true;
				}
				if (Game.creatureCanMoveInDirection(this, direction + 2)) {
					getTile().getTileInDirection(direction + 2).addEntity(this);
					return true;
				}
				if (Game.creatureCanMoveInDirection(this, direction - 1)) {
					getTile().getTileInDirection(direction - 1).addEntity(this);
					return true;
				}
				if (Game.creatureCanMoveInDirection(this, direction + 1)) {
					getTile().getTileInDirection(direction + 1).addEntity(this);
					return true;
				}
			}
		}
		canAct = true;	//monster will always be able to act after it tries to move
		return false;
	}	
	
	protected void attack(Creature c){
		c.takeDamage(getDamage());
	}
}
