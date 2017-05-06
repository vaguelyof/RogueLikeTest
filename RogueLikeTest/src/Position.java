
/**
 * Stores a 
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Position
{
    private int x;
    private int y;
    private DungeonLevel dun;
    public Position(int xIn, int yIn, DungeonLevel dungeon){
        x = xIn;
        y = yIn;
        dun = dungeon;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public DungeonLevel getDungeon(){
        return dun;
    }
}
