package items;
import java.awt.Color;

import creatures.Creature;
import gameEntities.Entity;
import level.Tile;

public abstract class Item extends Entity
{
	private Tile myTile;
	private int myStack;
	
	public Tile getTile() {
		return myTile;
	}

	public void setTile(Tile t) {
		myTile = t;
	}

	public int getStack() {
		return myStack;
	}
	
	public void setStack(int stack) {
		myStack = stack;
	}
	
	public abstract void use(Creature user);
	
	public abstract int getValue();
}
