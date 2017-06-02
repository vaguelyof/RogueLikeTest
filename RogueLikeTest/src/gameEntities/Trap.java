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
	boolean isRevealable;
	
	public Trap(){
		isVisible = true;
		isArmed = false;
		isRevealable = true;
	}
	
	public Trap(StatusEffect effect){
		this(effect.getAdjective()+" Trap",effect.getName().substring(0, 1).toUpperCase() + effect.getName().substring(1)+"s the victim",effect);
		/*myName = effect.getAdjective()+" Trap";
		myDesc = effect.getName().substring(0, 1).toUpperCase() + effect.getName().substring(1)+"s the victim";
		myEffect = effect;
		isVisible = false;
		isArmed = true;*/
	}
	
	public Trap(String desc, StatusEffect effect){
		this(effect.getAdjective()+" Trap",desc,effect);
		/*myName = effect.getAdjective()+" Trap";
		myDesc = desc;
		myEffect = effect;
		isVisible = false;
		isArmed = true;*/
	}
	
	public Trap(String name, String desc, StatusEffect effect){
		myName = name;
		myDesc = desc;
		myEffect = effect;
		isVisible = false;
		isArmed = true;
		isRevealable = Math.random()<0.5;
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
	
	public void disarm(){
		isArmed = false;
		isVisible = true;
	}
	
	public boolean isArmed(){
		return isArmed;
	}
	
	public boolean isVisible(){
		return isVisible||!isArmed;
	}
	
	public boolean isRevealable(){
		return isRevealable&&!isVisible;
	}
	
	public void trigger(Creature user){
		if (isArmed){
			user.addEffect(myEffect);
			isVisible = true;
			isArmed = false;
		}
	}

}
