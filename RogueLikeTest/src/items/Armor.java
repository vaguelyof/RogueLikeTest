package items;
import java.awt.Color;

import creatures.Creature;
import level.Tile;

/*
 * Armor is an Item that reduces incoming damage
 * Armor must have a positive value
 */
public class Armor extends Item{
	private Tile myTile;
	private String name;
	private int blockStrength;
	
	public Armor(int str, String aName){
		blockStrength = str;
		name = aName;
	}
	
	@Override
	public String getName() {

		return name;
	}
	
	@Override
	public String getDescription() {

		return "Blocks " + blockStrength + " damage.";
	}

	@Override
	public Tile getTile() {
		return myTile;
	}

	@Override
	public void setTile(Tile t) {
		myTile = t;
	}

	@Override
	public char getChar() {
		// TODO Auto-generated method stub
		return 'A';
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return Color.LIGHT_GRAY;
	}

	@Override
	public int getStack() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getValue() {
		// TODO Auto-generated method stub
		return blockStrength;
	}

	@Override
	public void setStack(int stack) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void use(Creature user) {
		
	}

}
