package items;

import java.awt.Color;

import creatures.Creature;
import level.Tile;

public class Gold implements Item {

	private Tile myTile;
	private int myStack;
	
	public Gold(int amount){
		myStack = amount;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return myStack + " gold";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public Tile getTile() {
		// TODO Auto-generated method stub
		return myTile;
	}

	@Override
	public void setTile(Tile t) {
		// TODO Auto-generated method stub
		myTile = t;
	}

	@Override
	public char getChar() {
		// TODO Auto-generated method stub
		return '.';
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return new Color(255,215,0);
	}

	@Override
	public int getStack() {
		// TODO Auto-generated method stub
		return myStack;
	}

	@Override
	public int getValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setStack(int stack) {
		myStack = stack;

	}

	@Override
	public void use(Creature user) {
		
	}

	@Override
	public boolean canRevive() {
		return false;
	}

}
