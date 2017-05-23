package gameEntities;
import java.awt.Color;

import level.Tile;

public class UpStairs implements Entity {
	private Tile myTile;
	
	public UpStairs(){
		
	}
	
	public String getName() {
		// TODO Auto-generated method stub
		return "Stairs";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Lead to the level above";
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
		// TODO Auto-generated method stub
		return '<';
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return Color.GRAY;
	}

}
