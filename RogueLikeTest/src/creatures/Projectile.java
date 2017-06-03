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
public class Projectile extends Creature{

	private int dir;
	private Item thrownItem;
	
	public Projectile()
	{
		super("dart", "small, fast, and deadly", 999, 1);
		setColor(Color.WHITE);
		dir = 0;
		setChar(determineChar());
	}
	
	public Projectile(int direction)
	{
		super("dart", "small, fast, and deadly", 999, 1);
		setColor(Color.WHITE);
		dir = direction;
		setChar(determineChar());
	}
	
	public Projectile(int direction, int damage, Color color)
	{
		super("dart", "small, fast, and deadly", 999, damage);
		setColor(color);
		dir = direction;
		setChar(determineChar());
	}
	
	//default constructor for magic projectiles
	public Projectile(int direction, int damage, String name, String description)
	{
		super(name, description, 999, damage);
		dir = direction;
		setColor(Color.CYAN);
		setChar('*');
	}
	
	//default constructor for magic projectiles
		public Projectile(int direction, int damage, Color color, String name, String description)
		{
			super(name, description, 999, damage);
			dir = direction;
			setColor(color);
			setChar(determineChar());
		}
	
	public Projectile(int direction, int damage, Color color, String name, String description, char character)
	{
		super(name, description, 999, damage);
		dir = direction;
		setColor(color);
		setChar(character);
	}
	
	public Projectile(int direction, Item thrown){
		super(thrown.getName(), thrown.getDescription(), 999, 0);
		setColor(thrown.getColor());
		dir = direction;
		thrownItem = thrown;
		setChar(determineChar());
	}
	
	public void act(){
		Entity nextEntity = getTile().getTileInDirection(dir).getTopEntity();
		
		//move for a maximum of two spaces per turn until it hits a wall or a player
		for(int i = 1; i <= 2; i++)
		{
			nextEntity = getTile().getTileInDirection(dir).getTopEntity();
			//projectile will move if it can
			if(this.getTile().getTileInDirection(dir).getIsRock()){
				this.die();
				return;
			}
			else if(nextEntity instanceof Creature){
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
			c.takeDamage(getDamage());
			this.die();	//deletes itself when it hits
	}
	
	private void move(){
		(getTile().getTileInDirection(dir)).addEntity(this);
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
	
	/*
	 * (non-Javadoc)
	 * @see gameEntities.Entity#getChar()
	 * 
	 * projectile has character resembling arrow pointing in the direction it moves
	 */
	public char determineChar() {
		System.out.println(dir);
		int dir = this.dir%8;
		if(dir < 0)
			dir = 8 + dir;
		if (thrownItem!=null)
			return thrownItem.getChar();
		else if (dir==0)
			return '^';
		else if (dir==1)
			return 191;
		else if (dir==2)
			return '>';
		else if (dir==3)
			return 217;
		else if (dir==4)
			return 'v';
		else if (dir==5)
			return 192;
		else if (dir==6)
			return '<';
		else
			return 218;
	}
}
