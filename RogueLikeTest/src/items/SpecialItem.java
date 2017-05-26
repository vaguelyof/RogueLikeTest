package items;

import level.Tile;

public abstract class SpecialItem extends Item  {
	private Tile myTile;
	private int myStack;

	@Override
	public String getDescription() {
		return "";
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
		return 'O';
	}
	
	@Override
	public int getStack() {
		return myStack;
	}
	
	@Override
	public void setStack(int stack) {
		myStack = stack;
	}
}
