import java.awt.Color;

public class RevivePotion extends Potion {
	
	public RevivePotion(){
		healAmount = 50;
	}
	
	public Color getColor() {
		return new Color(255,180,180);
	}
	
	public boolean canRevive(){
		return true;
	}
	
	@Override
	public int getValue(){
		return 500;
	}
}
