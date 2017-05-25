package statusEffects;

import creatures.Creature;

/**
 * Basically PoisonStatusEffect, but does extra damage and is affected by resistances
 * @author 1839208
 *
 */
public class BurnStatusEffect extends StatusEffect {
	
	int damage;
	
	public BurnStatusEffect() {
		super();
		damage = 2;
	}
	
	public BurnStatusEffect(int dur) {
		super(dur);
		damage = 2;
	}
	
	public BurnStatusEffect(int dur, int dam) {
		super(dur);
		damage = dam;
	}
	
	public int getId() {
		return 2;
	}
	
	@Override
	public void start(Creature target) {
		if (target.hasEffect(4))
			setDuration(0);
	}
	
	@Override
	protected void act(Creature target) {
		target.takeDamage(damage);
	}
}
