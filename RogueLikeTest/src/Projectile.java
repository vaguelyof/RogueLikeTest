import java.awt.Color;

/*
 * Projectile must be created with a direction
 * moves up to two spaces per turn until it hits a player or a wall
 * then it deals damage and deletes itself
 */
public class Projectile implements Entity{

	private int dir;
	private Tile myT;
	private int myDamage;
	private Color myColor;
	private Item thrownItem;
	
	public Projectile(int direction, int damage){
		myColor = Color.WHITE;
		dir = direction;
		myDamage = damage;
	}
	
	public Projectile(int direction, int damage, Color color){
		myColor = color;
		dir = direction;
		myDamage = damage;
	}
	
	public Projectile(int direction, Item thrown){
		myColor = thrown.getColor();
		dir = direction;
		thrownItem = thrown;
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
		return "Dart";
	}
	
	public String getDescription()
    {
    	return "Small, fast and deadly";
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
	
	@Override
	public char getChar() {
		if (thrownItem!=null)
			return thrownItem.getChar();
		else
			return '^';
	}
}
