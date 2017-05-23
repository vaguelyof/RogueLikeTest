import java.awt.Color;

public class RevivePotion extends Potion {
	
	public RevivePotion(){
		super(15);
	}
	
	@Override
	public Color getColor() {
		return new Color(255,180,180);
	}
	
	@Override
	public boolean canRevive(){
		return true;
	}
	
	@Override
	public int getValue(){
		return 500;
	}
}
