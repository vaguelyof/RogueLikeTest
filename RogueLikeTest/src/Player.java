import java.awt.Color;

public class Player extends Creature {

	public Player(String aName, String description, int health, int dmg) {
		super(aName, description, health, dmg);
		setColor(Color.BLUE);
		setChar('@');
	}
	

	public void move(String dir){
		
	}
	
}
