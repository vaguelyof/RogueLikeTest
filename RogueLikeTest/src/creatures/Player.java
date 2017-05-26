package creatures;

import java.awt.Color;

import gameEntities.Chest;
import gameEntities.Door;
import gameEntities.Entity;
import gameBase.Game;
import items.Inventory;
import items.Item;
import items.RevivalItem;
import items.RevivePotion;
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
		int tempHealth = getHealth();
		if (myInv.getMyArmor() == null) {
			super.takeDamage(d);
			if (getHealth()<tempHealth)
				game.logMessage("You were hit!", Color.RED);
		} else {
			super.takeDamage(d - myInv.getMyArmor().getValue()); // Creature
																	// will
																	// catch if
																	// value is
																	// negative
			if (getHealth()<tempHealth)
				game.logMessage("You were hit!", Color.RED);
		}
	}

	public int getGold() {
		return myInv.getGold();
	}
	public int getKeys() {
		return myInv.getKeys();
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
		 * find out whether the item being picked up is an Armor or Weapon
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
	
	public void silentDrop(Item e) {
		if (e == null)
			return;

		Tile t = getTile();
		t.removeEntity(this);
		t.addEntity(e);
		t.addEntity(this);
	}

	public void die() {
		// checks various sources to see if the target dies
		if (hasEffect(3)) {
			if (getHealth() <= 0)
				setHealth(1);
			return;
		}
		if (myInv.getMySpecial() != null && myInv.getMySpecial() instanceof RevivalItem) {
			((RevivalItem) myInv.getMySpecial()).revive(this);
			game.logMessage("You were revived by your " + myInv.getMySpecial().getName() + "!", Color.YELLOW);
			myInv.setMySpecial(null);
			return;
		}
		if (myInv.getMyPotion() != null && myInv.getMyPotion() instanceof RevivalItem) {
			((RevivalItem) myInv.getMyPotion()).revive(this);
			game.logMessage("You were revived by your " + myInv.getMyPotion().getName() + "!", Color.YELLOW);
			myInv.setMyPotion(null);
			return;
		}
		super.die();
		// drop all items
		// gold is lost
		dropItem(myInv.getMySpecial());
		dropItem(myInv.getMyArmor());
		dropItem(myInv.getMyWeapon());
		dropItem(myInv.getMyPotion());
		game.revertToBeginning();
		game.logMessage("YOU DIED", Color.RED);
	}
	
	public void unlock(){
		if(myInv.useKey()){
		Chest chest = null;

		for (int i = getTile().getEntities().size() - 1; i >= 0; i--) {
			if (getTile().getEntities().get(i) instanceof Chest) {
				chest = (Chest) getTile().getEntities().get(i);
				getTile().removeEntity(chest);
				break;
			}
		}
		silentDrop(chest.getItem());
		game.logMessage("There was a " + chest.getItem().getName() + " in the chest.", Color.WHITE);
		}
		else{
			game.logMessage("You don't have a key.", Color.RED);
		}
	}
	
	public String items() {
		return myInv.toString();
	}

	public void usePotion() {
		if (myInv.getMyPotion() != null && !(myInv.getMyPotion() instanceof RevivePotion)) {
			myInv.getMyPotion().use(this);
			game.logMessage("You drank " + myInv.getMyPotion().getName() + ".", Color.GREEN);
			myInv.setMyPotion(null);
		} else {
			game.logMessage("You cannot drink a potion.");
		}

	}
	
	public void interact(){
		if(getTile().getEntities().size()<2){
			game.logMessage("There is nothing here.", Color.RED);
			return;
		}
		Entity e = getTile().getEntities().get(getTile().getEntities().size() - 2);
		if(e instanceof Item){
			pickUp();
			return;
		}
		if(e instanceof Chest){
			unlock();
			return;
		}
		game.logMessage("You cannot interact with this.");
	}
}
