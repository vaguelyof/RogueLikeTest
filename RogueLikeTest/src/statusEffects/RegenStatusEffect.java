package statusEffects;

import java.awt.Color;

import creatures.Creature;

public class RegenStatusEffect extends StatusEffect {
	
	int healing;
	double interval;
	double count;
	
	public RegenStatusEffect() {
		this(-1,1);
	}
	
	public RegenStatusEffect(int dur) {
		this(dur,1);
	}
	
	public RegenStatusEffect(int dur, int heal) {
		super(dur);
		healing = heal;
		interval = 1;
		count = 0.0;
	}
	
	public RegenStatusEffect(int dur, double heal) {
		this(dur,(int)Math.ceil(heal));
		if ((int)heal>=1)
			return;
		interval = 1.0/heal;
	}
	
	public int getId() {
		return 6;
	}
	
	@Override
	protected void act(Creature target) {
		count++;
		if (count>=interval){
			count -= (int)count;
			target.heal(healing);
		}
	}
	
	public Color getColor(){
		return new Color(255,0,255);
	}
	
	public String getName() {
		return "regeneration";
	}
	
	public String getTriggerMessage(){
		return "You feel your strength returning...";
	}
}
