package items;

import java.awt.Color;

import creatures.Creature;
import creatures.Player;
import level.DungeonLevel;

public class RevealScroll extends Item {

	@Override
	public void use(Creature user) {
		if (user instanceof Player){
			((Player) user).getGame().revealLevel(((Player) user).getGame().getCurrentLevel());
			((Player) user).getGame().logMessage("The level is revealed!");
			((Player) user).getInventory().setMySpecial(null);
		}
		
	}

	@Override
	public String getName() {
		return "Scroll of Revealing";
	}

	@Override
	public String getDescription() {
		return "Reveals all rooms, items and traps";
	}

	@Override
	public char getChar() {
		return 'S';
	}

	@Override
	public Color getColor() {
		return null;
	}
}
