import java.awt.Color;

/**
 * Acts as a transition between rooms.
 * 
 * @author Nikolai Trintchouk
 * @version 5/6/2017
 */
public class Door implements Entity
{
    private Tile myTile;
    
    public Door(){
        
    }
    public String getName(){
        return "Door";
    }
    public String getDescription(){
        return "";
    }
    public Tile getTile(){
        return myTile;
    }
    public void setTile(Tile t){
        myTile = t;
    }
	@Override
	public char getChar() {
		// TODO Auto-generated method stub
		return '+';
	}
	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return Color.GRAY;
	}
}
