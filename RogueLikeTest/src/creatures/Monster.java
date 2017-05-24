package creatures;
import java.awt.Color;
import java.util.ArrayList;

import gameBase.Game;
import level.Tile;
import statusEffects.StatusEffect;

public class Monster extends Creature{
	
	private int speed;	//how many blocks the creature moves per turn
	private boolean isSlow;
	private boolean canMove;

	public Monster(String aName, String description, int health, int dmg)
	{
		super(aName, description, health, dmg);
		setChar('!');
		setColor(Color.RED);
		speed = 1;
		isSlow = false;
		canMove = true;
	}
	
	public Monster(String aName, String description, int health, int dmg, int moveSpeed, boolean slow)
	{
		super(aName, description, health, dmg);
		setChar('!');
		setColor(Color.RED);
		speed = moveSpeed;
		isSlow = slow;
		canMove = true;
	}
	
	public void act()
	{
		//for (StatusEffect e:status){
		//	if (e.tick(this))
		//		deleteEffect(e.getId());
		//}
		tickAllEffects();
		if(getHealth() == 0){
			die();
			return;
		}
	
		ArrayList<Tile> seeable = new ArrayList<Tile>();
		
		seeable = Game.calcFOV(this,14);
		
		//if the monster is next to the player, it attacks
		for(Tile t : getTile().getAdjacentTiles())
		{
			if(t.getTopEntity() instanceof Player)
			{
				attack((Creature)t.getTopEntity());
				return;
			}
		}
		
		//makes the monster skip a turn to move when it's slow
		if(isSlow = true)
			slow();

		if(canMove)
		{
			//if the monster can see the player, it moves towards it
			for(Tile t: seeable)
			{
				if(canAttackPlayer(t))
				{
					move((int)(getTile().getDirectionToTile(t)));
					return;
				}
			}
		}
	}
	
	private boolean canAttackPlayer(Tile t){
		return (t.getTopEntity() instanceof Player);
	}
	
	private void move(int direction){
		for (int i = 0; i < speed; i++){
			if (Game.creatureCanMoveInDirection(this, direction)) {
				getTile().getTileInDirection(direction).addEntity(this);
			}
		}
	}	
	
	private void attack(Creature c){
		c.takeDamage(getDamage());
	}
	
	private void slow(){
		canMove = !canMove;
	}
}
