
/**
 * Write a description of class Door here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
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
}
