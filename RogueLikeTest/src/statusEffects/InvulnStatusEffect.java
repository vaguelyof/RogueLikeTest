package statusEffects;

import java.awt.Color;

import creatures.Creature;

/**
 * Renders the target invincible to all damage, but does not remove conditions
 * @author JonathanKnowles
 */
public class InvulnStatusEffect extends StatusEffect{
	
	int targetHealth;
	
	public InvulnStatusEffect() {
		super();
	}
	
	public InvulnStatusEffect(int dur) {
		super(dur);
	}
	
	public int getId() {
		return 3;
	}
	
	@Override
	public boolean start(Creature target) {
		targetHealth = target.getHealth();
		target.deleteAllNegativeEffects();
		return true;
	}
	
	@Override
	protected void act(Creature target) {
		if (target.getHealth()<targetHealth)
			target.setHealth(targetHealth); //the target shouldn't take damage, but just in case
		else if (target.getHealth()>targetHealth)
			targetHealth = target.getHealth();
	}
	
	public Color getColor(){
		return Color.YELLOW;
	}
	
	public String getName() {
		return "invulnerability";
	}
	
	public String getTriggerMessage(){
		return "Strength flows through you!";
	}
}
