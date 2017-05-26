package level;

public class StepTile extends Tile{

	int step;
	
	public StepTile(boolean type, DungeonLevel dungeon, int x, int y, int s) {
		super(type, dungeon, x, y);
		step = s;
	}
	
	public int getStep()
	{
		return step;
	}

	public String toString()
	{
		return super.toString() + "Step = " + step;
	}
}
