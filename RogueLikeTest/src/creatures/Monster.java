package creatures;
import java.awt.Color;
import java.util.ArrayList;

import gameBase.Game;
import gameEntities.Door;
import level.Tile;
import statusEffects.StatusEffect;

public class Monster extends Creature{
	
	Player player;
	Game game;
	private int speed;	//how many blocks the creature moves per turn
	private boolean isSlow;
	private boolean canMove;
	private int stuns;
	private Tile lastSeen;

	public Monster(String aName, String description, int health, int dmg)
	{
		super(aName, description, health, dmg);
		setChar('!');
		setColor(Color.RED);
		speed = 1;
		isSlow = false;
		stuns = 0;
		canMove = true;
		lastSeen = null;
	}
	
	public Monster(String aName, String description, int health, int dmg, int moveSpeed, boolean slow)
	{
		super(aName, description, health, dmg);
		setChar('!');
		setColor(Color.RED);
		speed = moveSpeed;
		isSlow = slow;
		stuns = 0;
		canMove = true;
		lastSeen = null;
	}
	
	/*
	 * monster acts in a step by step process
	 * 1. applies any current status effects
	 * 2. dies if its health is <= 0
	 * 3. checks if it can attack a player, and attacks if it can
	 * 4. checks if it can see the player, and moves toward it if it can
	 * 5. moves toward last known location if it has seen the player
	 */
	public void act()
	{
		//for (StatusEffect e:status){
		//	if (e.tick(this))
		//		deleteEffect(e.getId());
		//}
		tickAllEffects();
		if(getHealth() <= 0){
			die();
			return;
		}
		if (stuns>0)
			return;
		ArrayList<Tile> seeable = new ArrayList<Tile>();
		
		seeable = Game.calcFOV(this,14);
		
		if(canSeePlayer()){
			
			//if the monster is next to the player, it attacks
			for(Tile t : getTile().getAdjacentTiles())
			{
				if(tileHasPlayer(t))
				{
					attack((Creature)t.getTopEntity());
					return;
				}
			}
			
			//makes the monster skip a turn to move when it's slow
			if(isSlow = true)
				slow();
	
			//if the monster can see the player and has a direct path to the player
			//monster moves towards player
			if(canMove && canSeePlayer())
			{
				for (Tile t : seeable)
				{
					if(tileHasPlayer(t))
					{
						move(getTile().getDirectionToTile(getTile().getNextTile(t)));
						return;//butter my buns and call me a biscuit if this is all I needed
					}
				}
			}
		}
				/*for(Tile t: seeable)
				{
						if(tileHasPlayer(t))
						{
							move((getTile().getDirectionToTile(t)));
							lastSeen = t;
							return;
						}
						
						//monster will chase players to the door if it's in a room
						if (justSawPlayer)
						{
							if(t.getTopEntity() instanceof Door){
								lastSeen = t;
								justSawPlayer = false;
								moveToLastKnownLocation();
								return;
							}
						}
				}
			
					//should only execute if monster is in a maze
					else if(tileHasPlayer(t) && hasDirectPathToTile(getTile(), t, getTile().getDirectionToTile(t)))
					{
						move(getTile().getDirectionToTile(t));
						lastSeen = t;
						return;
					}
				}
		
			}
		
		//if monster can't see the player
		//moves to last known location if it has one
		if(lastSeen != null)
		{
			moveToLastKnownLocation();
			return;
		}
		/*
		//will only execute if the player is on a door
		//the monster "Hears" the player opening the door
		//TODO: create a findDistance method in Tile to prevent the monster from "Hearing" a Player across the dungeon
		for(Tile t : seeable)
		{
			if (t.hasOpenDoor()){
				game.logMessage("The Door creaks loudly");
				lastSeen = t;
			}
		}
		*/
	}

	private boolean tileHasPlayer(Tile t){
		return (t.getTopEntity() instanceof Player);
	}
	
	/*
	 * checks if monster can see player
	 * 
	 * @return true if monster can see player
	 * @return false if monster cannot see player
	 * 
	 */
	private boolean canSeePlayer(){
		for(Tile t : Game.calcFOV(this,14)){
			if(tileHasPlayer(t))
				return true;
		}
		return false;
	}

	private void move(int direction){
		for (int i = 0; i < speed; i++){
			if (Game.creatureCanMoveInDirection(this, direction)) {
				getTile().getTileInDirection(direction).addEntity(this);
				return;
			}
			//checks if it can move right if it can't move directly ahead
			if (Game.creatureCanMoveInDirection(this, direction + 1)) {
				getTile().getTileInDirection(direction + 1).addEntity(this);
				return;
			}
			//if it can't move directly ahead or right, tries to turn left
			if (Game.creatureCanMoveInDirection(this, direction - 1)) {
				getTile().getTileInDirection(direction - 1).addEntity(this);
				return;
			}
		}
	}	
	
	private void attack(Creature c){
		c.takeDamage(getDamage());
	}
	
	private void slow(){
		canMove = !canMove;
	}
	
	public void addStun() {
		stuns++;
	}
	
	public void removeStun() {
		stuns--;
	}
	
	/*
	 * checks if place can reach end by calling itself until place reaches end
	 * if place hits a rock, returns false
	 * 
	 * returns true if monster has uninterrupted path to end
	 * returns false if monster cannot reach end by moving directly toward it
	 *
	private boolean hasDirectPathToTile(Tile place, Tile end, int dir)
	{
		//if place has reached end, place has direct path to end
		if(end == place)
		{
			return true;
		}
		
		//if the next tile is not a rock
		if(!place.getTileInDirection(dir).getIsRock())
		{
			//keep going toward end along the same direction
			return hasDirectPathToTile(place.getTileInDirection(dir), end, dir);
		}
		
			else
			{
				//otherwise place has no direct path to end
				return false;
			}
		
	}
	
	/*
	 * monster moves toward tile lastSeen
	 * if it reaches lastSeen, sets it to null
	 * 
	 * precondition: lastSeen must not be null
	 *
	private void moveToLastKnownLocation()
	{
		if(getTile() == lastSeen)
		{
			lastSeen = null;
			return;
		}
		
		move(getTile().getDirectionToTile(lastSeen));
	}
	*/
}
