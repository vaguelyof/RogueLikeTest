package items;

import level.Tile;

public abstract class SpecialItem extends Item  {

	@Override
	public String getDescription() {
		return "";
	}
	
	@Override
	public char getChar() {
		return 'O';
	}
}
