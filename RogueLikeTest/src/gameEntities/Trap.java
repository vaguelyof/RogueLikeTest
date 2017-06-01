package gameEntities;

import java.awt.Color;

import creatures.Creature;
import level.Tile;
import statusEffects.StatusEffect;

public class Trap extends Entity {
	String myName;
	String myDesc;
	StatusEffect myEffect;
	static boolean isVisible;
	boolean isArmed;
	
	public Trap(){
		isArmed = false;
	}
	
	public Trap(StatusEffect effect){
		myName = effect.getAdjective()+" Trap";
		myDesc = effect.getName().substring(0, 1).toUpperCase() + effect.getName().substring(1)+"s the victim";
		myEffect = effect;
		isVisible = false;
		isArmed = true;
	}
	
	public Trap(String desc, StatusEffect effect){
		myName = effect.getAdjective()+" Trap";
		myDesc = desc;
		myEffect = effect;
		isVisible = false;
		isArmed = true;
	}
	
	public Trap(String name, String desc, StatusEffect effect){
		myName = name;
		myDesc = desc;
		myEffect = effect;
		isVisible = false;
		isArmed = true;
	}
	
	@Override
	public String getName() {
		if (isArmed)
			return myName;
		return "Inactive Trap";
	}

	public String getDescription() {
		if (isArmed)
			return myDesc;
		return "An unarmed trap";
	}

	public char getChar() {
		if (isVisible)
			return 'X';
		return ' ';
	}

	public Color getColor() {
		if (isArmed)
			return myEffect.getColor();
		return new Color(150,150,150);
	}
	
	public static void reveal(){
		isVisible = true;
	}
	
	public boolean isArmed(){
		return isArmed;
	}
	
	public boolean isVisible(){
		return isVisible||!isArmed;
	}
	
	public void trigger(Creature user){
		if (isArmed){
			user.addEffect(myEffect);
			isVisible = true;
			isArmed = false;
		}
	}

}
