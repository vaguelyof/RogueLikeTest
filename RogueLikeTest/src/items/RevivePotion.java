package items;
import java.awt.Color;

import creatures.Creature;

public class RevivePotion extends Potion {
	
	public RevivePotion(){
		super(30);
	}
	
	@Override
	public Color getColor() {
		return new Color(255,180,180);
	}
	
	@Override
	public boolean canRevive(){
		return true;
	}
	
	@Override
	public int getValue(){
		return 500;
	}
	
	@Override
	public void use(Creature user){
		if (user.getHealth() == 0)
			user.heal(user.getMaxHealth()/2);
		else
			super.use(user);
	}
}
