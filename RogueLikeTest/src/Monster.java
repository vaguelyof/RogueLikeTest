import java.awt.Color;
import java.util.ArrayList;

public class Monster extends Creature{
	
	private int speed;	//how many blocks the creature moves per turn
	private boolean isSlow;
	private boolean canMove = true;

	Monster(String aName, String description, int health, int dmg)
	{
		super(aName, description, health, dmg);
		setChar('!');
		setColor(Color.RED);
		speed = 1;
	}
	
	Monster(String aName, String description, int health, int dmg, int moveSpeed, boolean slow)
	{
		super(aName, description, health, dmg);
		setChar('!');
		setColor(Color.RED);
		speed = moveSpeed;
		isSlow = slow;
	}
	
	public void act()
	{
		if(getHealth() == 0){
			die();
			return;
		}
	
		ArrayList<Tile> seeable = new ArrayList<Tile>();
		
		seeable = Game.calcFOV(this);
		
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
				if(t.getTopEntity() instanceof Player)
				{
					move((int)(getTile().getDirectionToTile(t)));
					return;
				}
			}
		}
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
