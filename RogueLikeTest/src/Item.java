import java.awt.Color;

public interface Item extends Entity
{
	int getStack();
	
	int getValue();
	
	void setStack(int stack);
	
	void use(Creature user);
	
	boolean canRevive();
}
