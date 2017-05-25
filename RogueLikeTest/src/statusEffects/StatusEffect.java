package statusEffects;

import creatures.Creature;

/**
 * Status effect superclass
 * @author Jonathan Knowles
 *
 */

public class StatusEffect {
	
	private int duration;
	
	public StatusEffect(){
		duration = -1;
	}
	
	public StatusEffect(int dur){
		duration = dur;
	}
	
	public int getDuration() {
		if (duration < 0)
			return Integer.MAX_VALUE; //so that negative durations are treated as though they are infinite
		return duration;
	}
	
	/**
	 * Sets the duration. A negative duration lasts forever
	 * @param dur the duration
	 */
	public void setDuration(int dur) {
		duration = dur;
	}
	
	/**
	 * Returns the ID number used for identifying effects
	 * Should be overwritten by subclasses
	 * @return the ID number
	 */
	public int getId() {
		return -1;
	}
	
	/**
	 * Makes the effect run for 1 turn
	 * @param target the creature that is affected
	 * @return true if the effect runs out; false otherwise
	 */
	public boolean tick(Creature target) {
		if (duration==0){
			return true;
		}
		act(target);
		if (duration>0){
			duration--;
			if (duration==0){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * What the effect does when it is first added
	 * Should be overwritten by subclasses
	 * @param target the creature that is affected
	 */
	public void start(Creature target) {
	}
	
	/**
	 * What the effect does
	 * Should be overwritten by subclasses
	 * @param target the creature that is affected
	 */
	protected void act(Creature target) {
	}
	
	/**
	 * What the effect does when it ends
	 * Should be overwritten by subclasses
	 * @param target the creature that is affected
	 */
	public void end(Creature target) {
	}
}
