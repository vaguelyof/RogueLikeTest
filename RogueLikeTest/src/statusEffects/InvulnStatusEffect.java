package statusEffects;

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
	public void start(Creature target) {
		targetHealth = target.getHealth();
	}
	
	@Override
	protected void act(Creature target) {
		if (target.getHealth()<targetHealth)
			target.setHealth(targetHealth); //the target shouldn't take damage, but just in case
		else if (target.getHealth()>targetHealth)
			targetHealth = target.getHealth();
	}
}
