package creatures;
import java.awt.Color;
import java.util.ArrayList;

import gameEntities.Entity;
import items.Item;
import level.Tile;
public class Creature implements Entity{
	
	private ArrayList<Item> items = new ArrayList<Item>();
	private String name;
	private String des;
	private int currentHealth;
	private int maxHealth;
	private int dmg;
	protected Tile myT;
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
	
	public void addItem(Item i){
		items.add(i);
	}
	
	public void deleteItem(Item i){
		items.remove(i);
	}
	
	public ArrayList<Item> getItems(){
		return items;
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
    	//creature can't take negative damage
    	if(h <= 0){
    		if (-1*(h-2)*Math.random()<1)
    			h = 1;
    		else
    			return;
    	}
    	
    	currentHealth -= h;
    	if(currentHealth <= 0)
    		die();
    }
    
    public void heal(int h)
    {
    	currentHealth += h;
    	if(currentHealth > maxHealth)
    		currentHealth = maxHealth;
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
