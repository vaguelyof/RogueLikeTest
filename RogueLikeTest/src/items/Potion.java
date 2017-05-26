package items;
import java.awt.Color;

import creatures.Creature;
import level.Tile;

public abstract class Potion implements Item {
	private Tile myTile;
	private int myStack;
	private int healAmount;
	
	public Potion(){
		healAmount = 0;
	}
	
	public Potion(int health){
		healAmount = health;
	}
	
	@Override
	public String getName() {
		return "Potion";
	}

	@Override
	public String getDescription() {
		return "";
	}

	@Override
	public Tile getTile() {
		// TODO Auto-generated method stub
		return myTile;
	}

	@Override
	public void setTile(Tile t) {
		myTile = t;

	}

	@Override
	public char getChar() {
		return '+';
	}
	
	@Override
	public int getStack() {
		return myStack;
	}
	
	@Override
	public void setStack(int stack) {
		myStack = stack;
	}
	
	public boolean isDrinkable(){
		return isThrowable();
	}
	
	public boolean isThrowable(){
		return false;
	}
}
