package items;

import java.awt.Color;

import creatures.Creature;

public class LifePotion extends HealthPotion {
	public String getName() {
		return "Life Potion";
	}
	public String getDescription() {
		return "Drink to heal. Healing amount increases with level.";
	}
	public Color getColor() {
		return new Color(255,0,0);
	}

	public void use(Creature user){
		user.heal(user.getMaxHealth() / 4);
	}
}
