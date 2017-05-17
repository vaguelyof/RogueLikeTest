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
		
		//if the monster is directly next to the player, it attacks without doing anything else
		if(super.getTile().getTileInDirection(getDirectionToPlayer()).getTopEntity() instanceof Player){
			attack();
			return;
		}
		
		move(getDirectionToPlayer());
		
	}
	
	private void move(int direction){
		if (Game.creatureCanMoveInDirection(this, direction)) {
			this.getTile().getTileInDirection(direction).addEntity(this);
		}
	}
	
	private int getDirectionToPlayer(){
		return 0;
	}
	
}
