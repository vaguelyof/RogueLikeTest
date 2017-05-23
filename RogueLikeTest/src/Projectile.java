import java.awt.Color;

/*
 * Projectile must be created with a direction
 * moves up to two spaces per turn until it hits a player or a wall
 * then it deals damage and deletes itself
 */
public class Projectile extends Creature{

	private int dir;
	
	public Projectile(int direction){
		super("dart", "small, sharp, fast, and deadly", 1, 2, Color.WHITE, '-');
		dir = direction;
	}
	
	public Projectile(String aName, String description, int health, int damage, Color c, char letter, int direction){
		super(aName, description, health, damage, c, letter);
		dir = direction;
	}
	
	public void act(){
		Entity nextEntity = getTile().getTileInDirection(dir).getTopEntity();
		
		//move for a maximum of two spaces per turn until it hits a wall or a player
		for(int i = 1; i <= 2; i++)
		{
			//projectile will move if it can
			if(!Game.creatureCanMoveInDirection(this, dir)){
				//if the tile that won't let it move is a Creature, it attacks
				if(nextEntity != null){
					attack((Creature)nextEntity);
					return;
				}
				//otherwise it's hitting a wall, and it just has to die
				die();
				return;
			}
			else
				move();
		}
	}
	
	private void attack(Creature c){
		c.takeDamage(getDamage());
		die();	//deletes itself when it hits
	}
	
	private void move(){
		getTile().getTileInDirection(dir).addEntity(this);
	}
	
	//prevents the player from being able to attack a projectile
	public void takeDamage(int i){
		return;
	}
	
	public int getDirection(){
		return dir;
	}
}
