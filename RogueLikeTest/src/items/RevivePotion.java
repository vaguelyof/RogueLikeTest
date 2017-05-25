package items;
import java.awt.Color;

import creatures.Creature;
import statusEffects.*;

public class RevivePotion extends Potion implements RevivalItem {
	
	public RevivePotion(){
		super(0);
		
	}
	
	@Override
	public Color getColor() {
		return new Color(255,180,180);
	}
	
	@Override
	public int getValue(){
		return 10;
	}
	
	@Override
	public void revive(Creature user){
		user.setHealth(user.getMaxHealth()/2);
		user.addEffect(new InvulnStatusEffect(3));
		user.addEffect(new LevitationStatusEffect(3));
	}
	
	public String getName(){
		return "Revive Potion";
	}
}
