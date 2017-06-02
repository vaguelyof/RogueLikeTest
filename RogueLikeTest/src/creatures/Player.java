package creatures;

import java.awt.Color;
import java.util.ArrayList;

import gameEntities.Chest;
import gameEntities.Door;
import gameEntities.DownStairs;
import gameEntities.Entity;
import gameEntities.UpStairs;
import gameBase.Game;
import items.Inventory;
import items.Item;
import items.RevivalItem;
import items.RevivePotion;
import level.Tile;
import statusEffects.StatusEffect;

public class Player extends Creature {
	Inventory myInv;
	public Game game;
	private int experience;
	private int level;
	private int xpNeeded;
	
	public Player(String aName, String description, int health, int dmg, Game g) {
		super(aName, description, health, dmg);
		setColor(new Color(150, 150, 255));
		setChar((char) 1);
		game = g;
		myInv = new Inventory();
		experience = 0;
		level = 1;
		xpNeeded = 10;
	}
	public void addEffect(StatusEffect e){
		super.addEffect(e);
		game.logMessage(e.getTriggerMessage(), e.getColor());
	}
	
	public void deleteEffect(int id){
		if (getIndexOfEffect(id)!=-1){
			StatusEffect e = status.get(getIndexOfEffect(id));
			game.logMessage(e.getEndMessage());
		}
		super.deleteEffect(id);
	}
	
	public void deleteAllEffects(){
		super.deleteAllEffects();
		game.logMessage("You were cured of all ailments!", Color.YELLOW);
	}
	
	public boolean hasEffect(int id){
		return getIndexOfEffect(id)!=-1;
	}
	
	public void addXp(int xp){
		game.logMessage("Gained " + xp + " experience.");
		experience += xp;
		while(experience >= xpNeeded){
			experience -= xpNeeded;
			xpNeeded += 10;
			level++;
			game.logMessage("You are now level " + level + "! Maximum health increased!", Color.GREEN);
			setMaxHealth(getMaxHealth() + 5 * level);
			heal(getMaxHealth());
		}
	}

	public void takeDamage(int d) {
		int tempHealth = getHealth();
		if (myInv.getMyArmor() == null) {
			super.takeDamage(d);
			if (getHealth()<tempHealth)
				game.logMessage("You were hit!", Color.RED);
		} else {
			super.takeDamage(d - myInv.getMyArmor().getStrength()); // Creature
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
			return super.getDamage() + myInv.getMyWeapon().getDamage();

		return super.getDamage();
	}

	public void attack(Creature c) {
		c.takeDamage(getDamage());
		if (myInv.getMyWeapon() != null) {
			game.logMessage("You struck " + c.getName() + " with " + myInv.getMyWeapon().getName() + ".", Color.WHITE);
		} else {
			game.logMessage("You punched " + c.getName() + ".");
		}
		if(c.getTile() == null){
			game.logMessage("You defeated "+ c.getName() +".", Color.GREEN);
			addXp((c.getDamage() + c.getMaxHealth())/2);
		}
		else{
			game.logMessage("("+c.getHealth()+"/"+c.getMaxHealth()+")");
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
		game.logMessage("The chest contained " + chest.getItem().getName() + ".", Color.WHITE);
		}
		else{
			game.logMessage("You don't have a key.", Color.RED);
		}
	}
	
	public String items() {
		return myInv.toString();
	}

	public void usePotion() {
		if (myInv.getMyPotion() != null && myInv.getMyPotion().isDrinkable()) {
			myInv.getMyPotion().use(this);
			game.logMessage("You drank " + myInv.getMyPotion().getName() + ".", Color.GREEN);
			myInv.setMyPotion(null);
		} else if (myInv.getMyPotion() != null) {
			game.logMessage("You cannot drink this potion.",Color.RED);
		} else {
			game.logMessage("You cannot drink a potion.");
		}

	}
	
	public void interact(){
		int foundTraps = 0;
		for (int i=0;i<8;i++){
			if (getTile().getTileInDirection(i).disarmTrap())
				foundTraps++;
		}
		if (foundTraps == 1){
			game.logMessage("You disarmed the trap!", Color.YELLOW);
			return;
		}
		if (foundTraps > 1){
			game.logMessage("You disarmed nearby traps!", Color.YELLOW);
			return;
		}
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
		if(e instanceof DownStairs){
			game.goDown();
			return;
		}
		if(e instanceof UpStairs){
			game.goUp();
			return;
		}
		game.logMessage("You cannot interact with this.");
	}

	public int getLevel() {
		return level;
	}

	public int getXp() {
		// TODO Auto-generated method stub
		return experience;
	}

	public int getNeededXp() {
		// TODO Auto-generated method stub
		return xpNeeded;
	}
	
	public void tickAllEffects(){
		super.tickAllEffects();
		if(myInv.getGold() >= 1000){
			myInv.upgradeItems();
			game.logMessage("1000 Gold reached! Items upgraded!", Color.GREEN);
		}
	}
}
