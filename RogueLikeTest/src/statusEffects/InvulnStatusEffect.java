package statusEffects;

import creatures.Creature;

/**
 * Renders the target invincible to all damage, but does not remove conditions
 * @author JonathanKnowles
 */
public class InvulnStatusEffect extends StatusEffect{
	
	public InvulnStatusEffect() {
		super();
	}
	
	public InvulnStatusEffect(int dur) {
		super(dur);
	}
	
	public int getId() {
		return 3;
	}
}
