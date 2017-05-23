import java.awt.Color;

public class Player extends Creature {

	Game game;
	public Player(String aName, String description, int health, int dmg, Game g) {
		super(aName, description, health, dmg);
		setColor(Color.BLUE);
		setChar((char) 1);
		game = g;
	}
	

	public void move(String dir){
		
	}
	
	public void pickUp(){
		
	}
}
