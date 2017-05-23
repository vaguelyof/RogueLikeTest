package creatures;

public class ShootingMonster extends Monster{

	ShootingMonster(String aName, String description, int health, int dmg){
		super(aName, description, health, dmg);
		setChar('~');
	}
}