package items;

import java.awt.Color;

import creatures.Creature;
import statusEffects.InvulnStatusEffect;
import statusEffects.LevitationStatusEffect;

public class RevivalCharm extends Item implements RevivalItem {
	
	public RevivalCharm(){
	}
	
	@Override
	public Color getColor() {
		return new Color(255,180,180);
	}
	
	@Override
	public char getChar() {
		return 'O';
	}
	public String getName(){
		return "Revival Charm";
	}

	@Override
	public String getDescription() {
		return "A strong revival item";
	}
	
	@Override
	public void revive(Creature user){
		user.setHealth(user.getMaxHealth());
		user.addEffect(new InvulnStatusEffect(5));
		user.addEffect(new LevitationStatusEffect(5));
	}

	@Override
	public void use(Creature user){
	}

	@Override
	public int getValue() {
		// TODO Auto-generated method stub
		return 0;
	}
}
