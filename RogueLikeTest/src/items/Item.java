package items;
import java.awt.Color;

import creatures.Creature;
import gameEntities.Entity;

public interface Item extends Entity
{
	int getStack();
	
	int getValue();
	
	void setStack(int stack);
	
	void use(Creature user);
	
	boolean canRevive();
}
