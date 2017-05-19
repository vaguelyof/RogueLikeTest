import java.awt.Color;
public class Creature implements Entity{
	
	private String name;
	private String des;
	private int currentHealth;
	private int maxHealth;
	private int dmg;
	private Tile myT;
	private Color myColor;
	private char myChar;
	
	public Creature(String aName, String description, int health, int damage)
	{
		name = aName;
		des = description;
		currentHealth = health;	//creature always starts with max health
		maxHealth = health;
		dmg = damage;
		myColor = Color.BLACK;
		myChar = '@';
	}
	
	public Creature(String aName, String description, int health, int damage, Color c, char letter)
	{
		name = aName;
		des = description;
		currentHealth = health;	//creature always starts with max health
		maxHealth = health;
		dmg = damage;
		myColor = c;
		myChar = letter;
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
    
    public void takeDamage(int h)
    {
    	currentHealth -= h;
    }
    
    public int getDamage()
    {
    	return dmg;
    }
    
    public Tile getTile()
    {
    	return myT;
    }
    
    public void die()
    {
    	myT.removeEntity(this);
    }
    
    //same functionality as a "move" method
    public void setTile(Tile t)
    {
    	myT = t;
    }

	@Override
	public char getChar() {
		// TODO Auto-generated method stub
		return myChar;
	}

	@Override
	public Color getColor() {
		return myColor;
	}
	
	protected void setColor(Color c){
		myColor = c;
	}
    
    protected void setChar(char c) {
    	myChar = c;
    }
}
