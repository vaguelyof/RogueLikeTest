package items;

import java.awt.Color;

import creatures.Creature;
import level.Tile;

public class Gold extends Item {
	
	public Gold(int amount){
		setStack(amount);
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return getStack() + " gold";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "";
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
	public void use(Creature user) {
		
	}

	@Override
	public int getValue() {
		// TODO Auto-generated method stub
		return 0;
	}



}
