package creatures;

import java.awt.Color;
import java.util.ArrayList;

import gameBase.Game;
import gameEntities.Entity;
import level.Tile;

public class Wizard extends Monster{

	Game game;
	
	public Wizard(String aName, String description, int health, int dmg){
		super(aName, description, health, dmg);
		setChar(')');
		setColor(new Color(200, 0, 0));
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
			for(Tile t : getTile().getAdjacentTiles())
			{
				if(tileHasPlayer(t) && hasDirectPathTo(getTile(), t))
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
	
	protected void attack(Creature c)
	{
		game.insertEntity(
				(Entity)(new Projectile(dmg, getTile().getDirectionToTile(c.getTile()), "magic bolt", "a fast and deadly conjuring by a wizard")), 	//projectile object cast as an Entity
				getTile().getTileInDirection(getTile().getDirectionToTile(c.getTile())));	//tile to insert projectile at
	}
	
	/*
	 * returns true if this can get to Tile t by a direct path
	 * returns false if otherwise
	 */
	protected boolean hasDirectPathTo(Tile start, Tile end)
	{
		if(start.equals(end))
		{
			return true;
		}
		if(Game.creatureCanMoveInDirection(this, start.getDirectionToTile(end)))
		{
			return hasDirectPathTo(end, end.getTileInDirection(start.getDirectionToTile(end)));
		}
		return false;
	}
}