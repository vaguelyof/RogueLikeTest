import java.awt.Color;

public class Potion implements Item {
	private Tile myTile;
	private int myStack;
	private int healAmount;
	
	public Potion(){
		healAmount = 20;
	}
	
	public void use(Creature user){
		user.heal(healAmount);
	}
	
	@Override
	public String getName() {
		return "Potion";
	}

	@Override
	public String getDescription() {
		return "Drink to heal";
	}

	@Override
	public Tile getTile() {
		// TODO Auto-generated method stub
		return myTile;
	}

	@Override
	public void setTile(Tile t) {
		myTile = t;

	}

	@Override
	public char getChar() {
		return '+';
	}

	@Override
	public Color getColor() {
		return new Color(0,255,0);
	}
	
	@Override
	public int getStack() {
		return myStack;
	}
	
	@Override
	public void setStack(int stack) {
		myStack = stack;
	}
}
