import java.awt.Color;

public class Player extends Creature {

	private Game myGame;
	
	public Player(String aName, String description, int health, int dmg, Game g) {
		super(aName, description, health, dmg);
		setColor(Color.BLUE);
		setChar('@');
		myGame = g;
	}
	

	public void move(String dir){
		
	}
	
	public void die(){
		super.die();
		
	}
}
