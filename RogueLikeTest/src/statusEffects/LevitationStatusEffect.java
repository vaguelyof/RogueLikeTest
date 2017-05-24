package statusEffects;

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
}
