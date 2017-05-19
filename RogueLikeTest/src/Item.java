import java.awt.Color;

public interface Item extends Entity
{
	int getStack();
	
	void setStack(int stack);
	
	void use(Creature user);
}
