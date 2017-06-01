package statusEffects;

import java.awt.Color;

/**
 * Makes the target levitate
 * NOTE: this class does not do anything on its own. Other classes check for this effect and respond accordingly
 * @author Jonathan Knowles
 *
 */
public class LevitationStatusEffect extends StatusEffect {

	public LevitationStatusEffect() {
		super();
	}
	
	public LevitationStatusEffect(int dur) {
		super(dur);
	}
	
	public int getId() {
		return 1;
	}
	
	public Color getColor(){
		return new Color(200,220,255);
	}
	
	public String getName() {
		return "levitation";
	}
	
	public String getTriggerMessage(){
		return "Your feet lift off the ground...";
	}
}
