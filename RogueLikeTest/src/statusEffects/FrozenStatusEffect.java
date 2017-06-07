package statusEffects;

import java.awt.Color;

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
	public boolean start(Creature target) {
		target.deleteEffect(2);
		target.addStun();
		return true;
	}
	
	@Override
	public void end(Creature target) {
		target.removeStun();
	}
	
	public Color getColor(){
		return Color.WHITE;
	}
	
	public String getName() {
		return "freeze";
	}
	
	public String getAdjective() {
		return "Frost";
	}
	
	public String getTriggerMessage(){
		return "You were frozen!";
	}
	
	public String getEndMessage(){
		return "You thawed out";
	}
	
	public boolean isHazardous(){
		return true;
	}
}
