package statusEffects;

import creatures.Creature;

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
	
	@Override
	public void start(Creature target) {
		target.deleteEffect(2);
	}
}
