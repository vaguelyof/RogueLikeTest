package statusEffects;

import java.awt.Color;

import creatures.Creature;

/**
 * Renders the target invincible to external damage, and makes the user inflammable. Internal damage still applies
 * @author JonathanKnowles
 */
public class StoneskinStatusEffect extends StatusEffect {
	
	public StoneskinStatusEffect() {
		super();
	}
	
	public StoneskinStatusEffect(int dur) {
		super(dur);
	}
	
	public int getId() {
		return 4;
	}
	
	@Override
	public void start(Creature target) {
		target.deleteEffect(2);
	}
	
	public Color getColor(){
		return Color.GRAY;
	}
	
	public String getName() {
		return "stoneskin";
	}
	
	public String getTriggerMessage(){
		return "Your skin hardens!";
	}
}
