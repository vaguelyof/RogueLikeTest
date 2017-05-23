import java.awt.Color;

public class Searcher implements Entity{
	private Tile myTile;
	
	public Searcher(Tile t){
		myTile = t;
	}
	
	public String getName() {
		return "this text should never be displayed";
	}

	public String getDescription() {
		return "this text should never be displayed";
	}

	public Tile getTile() {
		return myTile;
	}


	public void setTile(Tile t) {
		myTile = t;
		
	}

	public char getChar() {
		return 0;
	}

	public Color getColor() {
		return null;
	}
	
	public String getPropertiesOfTile(){
		return getTile().toString();
	}
	
}
