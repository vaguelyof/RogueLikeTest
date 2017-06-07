package gameEntities;

import java.awt.Color;

import creatures.Creature;
import creatures.Player;
import statusEffects.StoneskinStatusEffect;

public class SpikeTrap extends Trap {
	int damage;
	
	public SpikeTrap(){
		super("Spike Trap","A basic damage-dealing trap",null);
		damage = 0;
	}
	
	public SpikeTrap(int dam){
		super("Spike Trap","Deals "+dam+" damage",null);
		damage = dam;
	}

	public String getDescription() {
		if (isArmed)
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
			if (damage==0)
				if (user.getMaxHealth()/4<1)
					user.setHealth(user.getHealth()-1);
				else
					user.setHealth(user.getHealth()-(user.getMaxHealth()/4));
			else
				user.setHealth(user.getHealth()-damage);
			if (user instanceof Player)
				((Player) user).getGame().logMessage("You were hit!", Color.RED);
			isVisible = true;
			isArmed = false;
		}
	}
}
