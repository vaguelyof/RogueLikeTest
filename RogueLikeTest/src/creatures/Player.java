package creatures;
import java.awt.Color;

import gameBase.Game;
import items.Armor;
import items.Weapon;

public class Player extends Creature {

	Game game;
	private Armor equippedArmor; 
	private Weapon equippedWeapon;
	private Weapon equippedSpecial;
	
	public Player(String aName, String description, int health, int dmg, Game g) {
		super(aName, description, health, dmg);
		setColor(Color.BLUE);
		setChar((char) 1);
		game = g;
	}
	
	public void equipArmor(Armor arm){
		equippedArmor = arm;
	}
	
	public void equipWeapon(Weapon wep){
		if(wep.isTwoHanded() == true)
		{
			equippedWeapon = wep;
			equippedSpecial = wep;
		}
		else		
		equippedWeapon = wep;
	}
	
	public void equipSpecial(Weapon spec){
		equippedSpecial = spec;
	}
	
	public void takeDamage(int d){
		
		if(equippedArmor == null)
			super.takeDamage(d);
		else
			super.takeDamage(d - equippedArmor.getValue()); //Creature will catch if value is negative
	}
	
	public int getDamage(){
		
		if(equippedWeapon == null)
			super.getDamage();
		
		//equippedWeapon.getValue() will always be positive
		return super.getDamage() + equippedWeapon.getValue();
	}
	
	public void pickUp(){
		/*find out whether the item being picked up
		 * is an Armor or Weapon
		 * then call equipArmor or equipWeapon
		 * and delete item being picked up
		 */
	}
}