package creatures;
import java.awt.Color;

import gameEntities.Entity;
import items.Item;
import level.Tile;

/*
 * Projectile must be created with a direction
 * moves up to two spaces per turn until it hits a player or a wall
 * then it deals damage and deletes itself
 */
public class Projectile extends Entity{

	private String myName;
	private String myDescription;
	private int dir;
	private Tile myT;
	private int myDamage;
	private Color myColor;
	private Item thrownItem;
	private char icon;
	
	public Projectile(int direction, int damage)
	{

		myName = "dart";
		myDescription = "small, fast, and deadly";
		myColor = Color.WHITE;
		dir = direction;
		myDamage = damage;
		icon = determineChar();
		
	}
	
	public Projectile(int direction, int damage, Color color)
	{
		myName = "dart";
		myDescription = "small, fast, and deadly";
		myColor = color;
		dir = direction;
		myDamage = damage;
		icon = determineChar();
	}
	
	//default constructor for magic projectiles
	public Projectile(int direction, int damage, String name, String description)
	{
		myName = name;
		myDescription = description;
		dir = direction;
		myDamage = damage;
		myColor = Color.CYAN;
		icon = '*';
	}
	
	public Projectile(int direction, int damage, Color color, String name, String description, char character)
	{
		myName = name;
		myDescription = description;
		dir = direction;
		myDamage = damage;
		myColor = color;
		icon = character;
	}
	
	public Projectile(int direction, Item thrown){
		myName = thrown.getName();
		myDescription = thrown.getDescription();
		myColor = thrown.getColor();
		dir = direction;
		thrownItem = thrown;
		icon = determineChar();
	}
	
	public void act(){
		Entity nextEntity = getTile().getTileInDirection(dir).getTopEntity();
		
		//move for a maximum of two spaces per turn until it hits a wall or a player
		for(int i = 1; i <= 2; i++)
		{
			//projectile will move if it can
			if(this.getTile().getTileInDirection(dir).getIsRock()){
				getTile().removeEntity(this);
				return;
			}
			else if(nextEntity != null){
				attack((Creature)nextEntity);
				return;
			}
			else
				move();
		}
	}
	
	private void attack(Creature c){
		if (thrownItem!=null)
			thrownItem.use(c);
		else
			c.takeDamage(myDamage);
		getTile().removeEntity(this);	//deletes itself when it hits
	}
	
	private void move(){
		getTile().getTileInDirection(dir).addEntity(this);
	}
	
	public String getName()
	{
		return myName;
	}
	
	public String getDescription()
    {
    	return myDescription;
    }
	
	public Tile getTile()
    {
    	return myT;
    }
	
	public void setTile(Tile t)
    {
    	myT = t;
    }
	
	public int getDirection(){
		return dir;
	}
	
	@Override
	public Color getColor() {
		return myColor;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gameEntities.Entity#getChar()
	 * 
	 * projectile has character resembling arrow pointing in the direction it moves
	 */
	public char determineChar() {
		if (thrownItem!=null)
			return thrownItem.getChar();
		else if (dir==0)
			return '^';
		else if (dir==1)
			return '7';
		else if (dir<4)
			return '>';
		else if (dir==4)
			return 'v';
		else if (dir==5)
			return 'L';
		else
			return '<';
	}
	
	public void setChar(char c)
	{
		icon = c;
	}
	
	public char getChar()
	{
		return icon;
	}
}
