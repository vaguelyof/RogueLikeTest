package items;

import java.awt.Color;

import creatures.Creature;
import statusEffects.StatusEffect;

public class StatusPotion extends Potion {
	String myName;
	String myDesc;
	StatusEffect myEffect;

	public StatusPotion(StatusEffect effect){
		this(effect.getName().substring(0, 1).toUpperCase() + effect.getName().substring(1),effect);
		/*myName = effect.getAdjective()+" Potion";
		myDesc = effect.getName().substring(0, 1).toUpperCase() + effect.getName().substring(1)+"s the ";
		if (effect.isHazardous())
			myDesc+="target";
		else
			myDesc+="user";
		myEffect = effect;*/
	}
	
	public StatusPotion(String descName, StatusEffect effect){
		myName = effect.getAdjective()+" Potion";
		myDesc = descName+"s the ";
		if (effect.isHazardous())
			myDesc+="target";
		else
			myDesc+="user";
		//myDesc = desc;
		myEffect = effect;
	}
	
	public StatusPotion(String name, String descName, StatusEffect effect){
		this(descName,effect);
		myName = name;
		//myDesc = desc;
		//myEffect = effect;
	}
	@Override
	public String getName() {
		return myName;
	}

	@Override
	public String getDescription() {
		return myDesc;
	}

	@Override
	public void use(Creature user){
		user.addEffect(myEffect);
	}

	@Override
	public Color getColor() {
		return myEffect.getColor();
	}
	
	public boolean isThrowable(){
		return myEffect.isHazardous();
	}

	@Override
	public int getValue() {
		// TODO Auto-generated method stub
		return 0;
	}
}
