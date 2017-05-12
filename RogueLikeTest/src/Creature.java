
public class Creature implements Entity{
	
	String name;
	String des;
	Tile myT;
	
	public Creature(String aName, String description, Tile t)
	{
		name = aName;
		des = description;
		myT = t;
	}
	
	public String getName()
	{
		return name;
	}
    
    public String getDescription()
    {
    	return des;
    }
    
    public Tile getTile()
    {
    	return myT;
    }
    
    public void setTile(Tile t)
    {
    	myT = t;
    }
}
