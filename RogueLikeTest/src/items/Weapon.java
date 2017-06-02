package items;
import java.awt.Color;

import creatures.Creature;
import level.Tile;

/* Weapon is an Item with a value that adds to a Creature's damage
 * Weapon must have a positive value 
 */
public class Weapon extends Item{

	boolean twoHanded;	//if true, takes up both of the Player's inventory spots
	private String name;
	private int damage;
	
	public Weapon(int dam, String aName){
		damage = dam;
		name = aName;
	}
	
	public int getDamage() {
		return damage;
	}

	public boolean isTwoHanded(){
		return twoHanded;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return "Deals "+damage+" damage";
	}

	@Override
	public char getChar() {
		return 'W';
	}

	@Override
	public Color getColor() {
		return Color.LIGHT_GRAY;
	}

	@Override
	public void use(Creature user) {
		
	}

	@Override
	public int getValue() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void upgrade(){
		damage+=2;
	}
}
