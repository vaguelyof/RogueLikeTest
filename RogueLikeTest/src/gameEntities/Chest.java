package gameEntities;
import java.awt.Color;

import level.Tile;

public class Chest implements Entity {
	
	//needs to have an inventory of treasure and gold
	private Tile myT;
	private boolean locked;
	
	public Chest(){
		locked = true;
	}
	
	@Override
	public String getName() {
		return "Chest";
	}

	@Override
	public String getDescription() {
		if(isLocked())
			return "Find a key to unlock what's inside!";
		else
			return "Open it up to see what's inside!";
	}

	@Override
	public Tile getTile() {
		// TODO Auto-generated method stub
		return myT;
	}

	@Override
	public void setTile(Tile t) {
		myT = t;

	}

	@Override
	public char getChar() {
		return '$';
	}

	@Override
	public Color getColor() {
		
		return new Color(255,215,0);
	}

	public boolean isLocked(){
		return locked;
	}
}
