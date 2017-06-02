package creatures;

import java.awt.Color;
import java.util.ArrayList;

import gameBase.Game;
import gameEntities.Entity;
import level.Tile;

public class Wizard extends Monster{

	public Wizard(String aName, String description, int health, int dmg){
		super(aName, description, health, dmg);
		setChar(')');
		setColor(Color.red);
	}
	
	public Wizard(String aName, String description, Color color, int health, int dmg){
		super(aName, description, health, dmg);
		setChar(')');
		setColor(color);
	}
	
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
			
			//if the wizard has direct line of sight to the player, it shoots at it
			for(Tile t : seeable)
			{
				//wizards have a weak 
				
				if(tileHasPlayer(t) && projectileHasDirectPathTo(getTile(), t))
				{
					shoot(getTile().getDirectionToTile(t));
					canAct = false;		//monster must wait one turn after attacking
					return;
				}
			}
		}
		canAct = true;
	
			//if the monster can see the player and has a direct path to the player
			//monster moves towards player
			if(canSeePlayer())
			{
				for (Tile t : seeable)			//checks all seeable tiles for the player
				{
					if(tileHasPlayer(t))
					{
						//stores a simple tile location as instance variable for use in a room
						lastSeen = t;
						
						//if the wizard is in a room and the player is on a door
						//or if the wizard and the player are in a room together, the wizard moves directly away the player
						//no questions asked
						if(getTile().isInRoom() && (t.isInRoom() || t.hasDoor()))
						{
							move((t.getDirectionToTile(getTile())));						
							return;		//these returns indicate the end of the monster's turn
						}
						
						//wizard chases player around a maze
						
						//should only execute if the wizard is in a maze
						if(!getTile().isInRoom() && !getTile().hasDoor())
						{						
							move(getTile().getDirectionToTile(getTile().getNextTileInMazeTowardTile(lastSeen)));
							return;
						}
						
						//if the wizard is in on a door and the player is in a maze, it moves into the maze automatically
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
			
			//if the wizard has reached lastSeen without seeing the player, it forgets lastSeen and stops moving
			if(lastSeen != null && lastSeen.getX() == getTile().getX() && lastSeen.getY() == getTile().getY())
			{
				lastSeen = null;
				return;
			}
			
			//if the wizard has lost sight of the player and is in a room
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
	
	protected void shoot(int direction)
	{
		Game.insertEntity(
				(new Projectile(direction, dmg, getColor(), "magic bolt", "a fast and deadly conjuring by a wizard")), 	//projectile object cast as an Entity
				getTile().getTileInDirection(direction));	//tile to insert projectile at
	}
	
	/*
	 * returns true if this can get to Tile t by a direct path
	 * returns false if otherwise
	 */
	protected boolean projectileHasDirectPathTo(Tile start, Tile end)
	{
		int direction = start.getDirectionToTile(end);
		Tile next = start.getTileInDirection(direction);
		while(true)
		{
			if(next == end)
				return true;
			if(next.getIsRock())
				return false;
			
			next = next.getTileInDirection(direction);
		}
	}
	
	protected boolean move(int direction)
	{
			for (int i = 0; i < speed; i++)
			{
				if (Game.creatureCanMoveInDirection(this, direction)) 
				{
					getTile().getTileInDirection(direction).addEntity(this);
					return true;
				}
				if (getTile().getTileInDirection(direction).getIsRock()) 
				{
					if (Game.creatureCanMoveInDirection(this, direction + 2)) 
					{
						getTile().getTileInDirection(direction + 2).addEntity(this);
						return true;
					}
					if(Game.creatureCanMoveInDirection(this, direction - 2)) 
					{
						getTile().getTileInDirection(direction - 2).addEntity(this);
						return true;
					}
				}
			}
		return false;
	}	
}