package creatures;

public class Wizard extends Monster{

	public Wizard(String aName, String description, int health, int dmg){
		super(aName, description, health, dmg);
		setChar(')');
	}
}