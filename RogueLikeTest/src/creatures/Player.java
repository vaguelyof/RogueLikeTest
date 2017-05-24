package creatures;
import java.awt.Color;

import gameBase.Game;
import items.Inventory;
import items.Item;

public class Player extends Creature {
	Inventory myInv;
	Game game;

	
	public Player(String aName, String description, int health, int dmg, Game g) {
		super(aName, description, health, dmg);
		setColor(Color.BLUE);
		setChar((char) 1);
		game = g;
		myInv = new Inventory();
	}
	
	public void takeDamage(int d){
		
		if(myInv.getMyArmor() == null)
			super.takeDamage(d);
		else
			super.takeDamage(d - myInv.getMyArmor().getValue()); //Creature will catch if value is negative
	}
	
	public int getDamage(){
		
		if(myInv.getMyWeapon() != null)
			return super.getDamage() + myInv.getMyWeapon().getValue();
		
		return super.getDamage();
	}
	
	public void pickUp(){
		/* TODO: find out whether the item being picked up
		 * 		is an Armor or Weapon
		 * 		then call equipArmor or equipWeapon
		 * 		and delete item being picked up
		 */
		
	}
	
	public void dropItem(Item e){
		
	}
	
	public void die(){
		//drop all items
		//drop all gold
		super.die();
		game.revertToBeginning();
	}
}
