package statusEffects;

import creatures.Creature;

public class PoisonStatusEffect extends StatusEffect {
	
	int damage;
	
	public PoisonStatusEffect() {
		super();
		damage = 1;
	}
	
	public PoisonStatusEffect(int dur) {
		super(dur);
		damage = 1;
	}
	
	public PoisonStatusEffect(int dur, int dam) {
		super(dur);
		damage = dam;
	}
	
	public int getId() {
		return 0;
	}
	
	@Override
	protected void act(Creature target) {
		target.lowerHealth(damage);
	}
}
