package items;
import java.awt.Color;

import creatures.Creature;

public class HealthPotion extends Potion {

	private int healAmount;
	
	public HealthPotion(){
		healAmount = 5;
	}
	
	public HealthPotion(int heal){
		healAmount = heal;
	}
	
	@Override
	public String getName() {
		return "Health Potion";
	}

	@Override
	public String getDescription() {
		return "Drink to heal";
	}
	
	@Override
	public Color getColor() {
		return new Color(0,255,0);
	}

	@Override
	public void use(Creature user){
		user.heal(healAmount);
	}
}
