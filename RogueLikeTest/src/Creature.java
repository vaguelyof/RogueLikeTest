
public class Creature implements Entity{
	
	private String name;
	private String des;
	private int currentHealth;
	private int maxHealth;
	private int damage;
	private Tile myT;
	
	public Creature(String aName, String description, int health, int dmg)
	{
		name = aName;
		des = description;
		currentHealth = health;	//creature always starts with max health
		maxHealth = health;
		damage = dmg;
	}
	
	public String getName()
	{
		return name;
	}
    
    public String getDescription()
    {
    	return des;
    }
    
    public int getHealth()
    {
    	return currentHealth;
    }
    
    public int getMaxHealth()
    {
    	return maxHealth;
    }
    
    public int attack()
    {
    	return damage;
    }
    
    public Tile getTile()
    {
    	return myT;
    }
    
    public void die()
    {
    	//stop existing
    }
    
    //same functionality as a "move" method
    public void setTile(Tile t)
    {
    	myT = t;
    }
}
