import java.awt.Color;

public class Chest implements Entity {
	
	private Tile myTile;
	
	public Chest(){
		
	}
	
	@Override
	public String getName() {
		return "Chest";
	}

	@Override
	public String getDescription() {
		return "Open it up to see what's inside!";
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
		return '$';
	}

	@Override
	public Color getColor() {
		
		return new Color(255,215,0);
	}

}
