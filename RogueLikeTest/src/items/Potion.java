package items;
import java.awt.Color;

import creatures.Creature;
import level.Tile;

public abstract class Potion extends Item {

	/*@Override
	public String getName() {
		return "Potion";
	}

	@Override
	public String getDescription() {
		return "";
	}*/

	@Override
	public char getChar() {
		return '+';
	}
	
	public boolean isDrinkable(){
		return !isThrowable();
	}
	
	public boolean isThrowable(){
		return false;
	}
}
