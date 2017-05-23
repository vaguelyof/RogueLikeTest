import java.awt.Color;

public class Player extends Creature {

	Game game;
	private Item equippedArmor; 
	private Item equippedWeapon;
	
	public Player(String aName, String description, int health, int dmg, Game g) {
		super(aName, description, health, dmg);
		setColor(Color.BLUE);
		setChar('@');
		game = g;
	}
	
	public void equipWeapon(Weapon wep){
		
	}
	
	public void equipArmor(Armor arm){
		
	}
	
	public void takeDamage(int d){
		
		//Creature will catch if value is negative
		super.takeDamage(d - equippedArmor.getValue());
	}
	
	public int getDamage(){
		
		//equippedWeapon.getValue() will always be positive
		return super.getDamage() + equippedWeapon.getValue();
	}
}
