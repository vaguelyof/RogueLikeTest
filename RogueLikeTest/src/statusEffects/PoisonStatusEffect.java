package statusEffects;

import java.awt.Color;

import creatures.Creature;

/**
 * Deals damage and ignores resistances
 * @author Jonathan Knowles
 */
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
		target.setHealth(target.getHealth()-(damage*target.getMaxHealth()/20));
	}
	
	public Color getColor(){
		return new Color(0,180,0);
	}
	
	public String getName() {
		return "poison";
	}
	
	public boolean isHazardous(){
		return true;
	}
}
