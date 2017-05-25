package items;
import java.awt.Color;

public class HealthPotion extends Potion {
	
	public HealthPotion(){
		super(5);
	}
	
	@Override
	public Color getColor() {
		return new Color(0,255,0);
	}
	
	@Override
	public int getValue(){
		return 2;
	}
}
