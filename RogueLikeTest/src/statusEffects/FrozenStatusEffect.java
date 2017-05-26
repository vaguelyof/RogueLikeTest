package statusEffects;

import creatures.Creature;

public class FrozenStatusEffect extends StatusEffect {

	public FrozenStatusEffect() {
		super();
	}
	
	public FrozenStatusEffect(int dur) {
		super(dur);
	}
	
	public int getId() {
		return 5;
	}
	
	@Override
	public void start(Creature target) {
		target.deleteEffect(2);
	}
	
	@Override
	public void end(Creature target) {
	}
}
