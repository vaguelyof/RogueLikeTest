import java.util.ArrayList;

public class Monster extends Creature{

	Monster(String aName, String description, int health, int dmg)
	{
		super(aName, description, health, dmg);
	}
	
	public void act()
	{
		if(super.getHealth() == 0){
			super.die();
			return;
		}
	
		ArrayList<Tile> seeable = new ArrayList<Tile>();
		
		seeable = Game.calcFOV(this);
		
		//if the monster is next to the player, it attacks
		for(Tile t : super.getTile().getAdjacentTiles())
		{
			if(t.getTopEntity() instanceof Player)
			{
				attack((Creature)t.getTopEntity());
				return;
			}
		}
		
		//if the monster can see the player, it moves towards it
		for(Tile t: seeable)
		{
			if(t.getTopEntity() instanceof Player)
			{
				move((int)(super.getTile().getDirectionToTile(t)));
				return;
			}
		}
	}
	
	private void move(int direction){
		if (Game.creatureCanMoveInDirection(this, direction)) {
			this.getTile().getTileInDirection(direction).addEntity(this);
		}
	}	
	
	private void attack(Creature c){
		c.takeDamage(super.getDamage());
	}
}
