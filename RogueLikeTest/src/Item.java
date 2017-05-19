import java.awt.Color;

public interface Item extends Entity
{
	int getStack();
	
	void setStack(int stack);
	
	Creature getOwner();
	
	void setOwner(Creature owner);
	
	void use();
}
