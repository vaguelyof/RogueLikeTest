package items;

import java.awt.Color;

import creatures.Creature;
import creatures.Player;
import statusEffects.InvulnStatusEffect;
import statusEffects.LevitationStatusEffect;

public class PotionPouch extends Item {
	Potion heldPotion;
	
	public PotionPouch(){
	}

	public PotionPouch(Potion potion){
		heldPotion = potion;
	}
	
	@Override
	public Color getColor() {
		return new Color(255,180,180);
	}
	
	@Override
	public char getChar() {
		return 'P';
	}
	public String getName(){
		return "Potion Pouch";
	}

	@Override
	public String getDescription() {
		return "Holds a single potion";
	}

	@Override
	public void use(Creature user){
		if (!(user instanceof Player))
			return;
		Inventory inv = ((Player)user).getInventory();
		Potion temp = inv.getMyPotion();
		inv.setMyPotion(heldPotion);
		heldPotion = temp;
		if (heldPotion!=null && inv.getMyPotion()!=null)
			((Player)user).getGame().logMessage("You swapped potions");
		else if (heldPotion!=null && inv.getMyPotion()==null)
			((Player)user).getGame().logMessage("You took a potion out of your poutch");
		else if (heldPotion==null && inv.getMyPotion()!=null)
			((Player)user).getGame().logMessage("You put the potion in your poutch");
		else
			((Player)user).getGame().logMessage("You don't have any potions");
	}
}
