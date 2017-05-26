package items;

import java.awt.Color;

import creatures.Creature;
import level.Tile;

public class Key extends Item{

	int myStack;
	int myValue;	//what is this for?
	String name;
	String des;
	Tile myT;
	char myChar;
	Color myColor;
	
	public Key() {
		myStack = 1;
		myValue = 0;
		name = "Key";
		des = "A magic key that can open one chest to unlock the treasures within";	//is this too long? Technically no, but it will take up sever lines

		myChar = 'K';
		myColor = Color.ORANGE;
	}
	
	public String getName() {
		return name;
	}

	public String getDescription() {
		return des;
	}

	public Tile getTile() {
		return myT;
	}

	public void setTile(Tile t) {;
		myT = t;
	}

	public char getChar() {
		return myChar;
	}

	public Color getColor() {
		return myColor;
	}

	public int getStack() {
		return myStack;
	}

	//not sure what this is for
	public int getValue() {
		return myValue;
	}

	public void setStack(int stack) {
		myStack = stack;
	}

	public void use(Creature user) {
		//unlocks chests
		//one - time use
	}

	//not sure what this is for
	public boolean canRevive() {
		return false;
	}

}
