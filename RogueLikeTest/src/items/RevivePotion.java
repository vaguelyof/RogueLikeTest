package items;
import java.awt.Color;

import creatures.Creature;
import statusEffects.*;

public class RevivePotion extends Potion implements RevivalItem {
	
	public RevivePotion(){
	}
	
	@Override
	public Color getColor() {
		return new Color(255,180,180);
	}
	
	@Override
	public void revive(Creature user){
		user.setHealth(user.getMaxHealth()/2);
		user.addEffect(new InvulnStatusEffect(3));
		user.addEffect(new LevitationStatusEffect(3));
	}

	@Override
	public void use(Creature user){
	}
	
	public String getName(){
		return "Revive Potion";
	}

	@Override
	public String getDescription() {
		return "Revives the user";
	}
	
	public boolean isDrinkable(){
		return false;
	}
}
