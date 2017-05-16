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
	}
}
