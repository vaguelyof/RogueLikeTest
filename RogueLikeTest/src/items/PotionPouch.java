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
		return new Color(200,100,0);
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
		String desc = "Holds a single potion";
		if (heldPotion!=null)
			desc+=(". Currently contains a "+heldPotion.getName());
		return desc;
	}
	
	public Potion getPotion(){
		return heldPotion;
	}
	
	public void setPotion(Potion potion){
		heldPotion = potion;
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
			((Player)user).getGame().logMessage("You put your "+heldPotion.getName()+" away and took out a "+inv.getMyPotion().getName());
		else if (heldPotion!=null && inv.getMyPotion()==null)
			((Player)user).getGame().logMessage("You put the "+heldPotion.getName()+" in your poutch");
		else if (heldPotion==null && inv.getMyPotion()!=null)
			((Player)user).getGame().logMessage("You took a "+inv.getMyPotion().getName()+" out of your poutch");
		else
			((Player)user).getGame().logMessage("You don't have any potions");
	}
}
