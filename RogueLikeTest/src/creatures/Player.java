package creatures;

import java.awt.Color;

import gameBase.Game;
import items.Inventory;
import items.Item;
import level.Tile;

public class Player extends Creature {
	Inventory myInv;
	Game game;

	public Player(String aName, String description, int health, int dmg, Game g) {
		super(aName, description, health, dmg);
		setColor(Color.BLUE);
		setChar((char) 1);
		game = g;
		myInv = new Inventory();
	}

	public void takeDamage(int d) {

		if (myInv.getMyArmor() == null) {
			super.takeDamage(d);
			game.logMessage("You were hit!", Color.RED);
		} else {
			super.takeDamage(d - myInv.getMyArmor().getValue()); // Creature
																	// will
																	// catch if
																	// value is
																	// negative
			game.logMessage("You were hit!", Color.RED);
		}
	}

	public int getDamage() {

		if (myInv.getMyWeapon() != null)
			return super.getDamage() + myInv.getMyWeapon().getValue();

		return super.getDamage();
	}

	public void attack(Creature c) {
		c.takeDamage(getDamage());
		if (myInv.getMyWeapon() != null) {
			game.logMessage("You struck " + c.getName() + " with " + myInv.getMyWeapon().getName() + ".", Color.WHITE);
		} else {
			game.logMessage("You punched " + c.getName() + ".");
		}
	}

	public void pickUp() {
		/*
		 * TODO: find out whether the item being picked up is an Armor or Weapon
		 * then call equipArmor or equipWeapon and delete item being picked up
		 */
		Item item = null;

		for (int i = getTile().getEntities().size() - 1; i >= 0; i--) {
			if (getTile().getEntities().get(i) instanceof Item) {
				item = (Item) getTile().getEntities().get(i);
				getTile().removeEntity(item);
				break;
			}
		}
		if (item != null) {
			dropItem(myInv.pickUpItem(item));
			game.logMessage("You picked up " + item.getName() + ".", Color.GREEN);
		} else {
			game.logMessage("There is nothing here.", Color.RED);
		}
	}

	public void dropItem(Item e) {
		if (e == null)
			return;
		Tile t = getTile();
		t.removeEntity(this);
		t.addEntity(e);
		t.addEntity(this);
		game.logMessage("You dropped " + e.getName() + ".", Color.RED);
	}

	public void die() {
		// drop all items
		// drop all gold
		dropItem(myInv.getMySpecial());
		dropItem(myInv.getMyArmor());
		dropItem(myInv.getMyWeapon());
		dropItem(myInv.getMyPotion());
		super.die();
		game.revertToBeginning();
	}
}
