package gameEntities;

import java.awt.Color;

import creatures.Creature;
import creatures.Player;
import statusEffects.StoneskinStatusEffect;

public class SpikeTrap extends Trap {
	int damage;
	boolean isTutorial;
	
	public SpikeTrap(){
		this(false);
	}
	
	public SpikeTrap(boolean visible){
		super("Spike Trap","Deals 1 damage",null);
		isTutorial = visible;
		if (isTutorial)
			reveal();
	}
	
	public SpikeTrap(int dam){
		super("Spike Trap","Deals "+dam+" damage",null);
		damage = dam;
	}

	public String getDescription() {
		if (isTutorial&&isArmed)
			return "A trap that injures the player when stepped on";
		return super.getDescription();
	}

	public Color getColor() {
		if (isArmed)
			return new Color(150,100,0);
		return new Color(150,150,150);
	}
	
	public void trigger(Creature user){
		if (isArmed){
			if (isTutorial&& user instanceof Player){
				if (user.getHealth()>1){
					user.setHealth(user.getHealth()-1);
					((Player)user).game.logMessage("You were hit by the trap!",Color.RED);
				}
				else
					((Player)user).game.logMessage("The trap broke...");
				isArmed = false;
			}
			else if (!isTutorial){
				user.takeDamage(damage);
				isArmed = false;
			}
		}
	}
}
