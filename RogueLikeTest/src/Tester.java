
/**
 * Write a description of class Tester here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tester
{
    public static void TestDungeonGenerator(){
        DungeonLevel dun = new DungeonLevel(0, 51);
        dun.generateLevel();
        dun.printMap();
    }
    
    public static void TestGUI(Game g){
    	FrameBuilder f = new FrameBuilder();
    	f.buildFrame(100, 50, g);	
    	
    }
    
    public static void main(String[] args){
    	Game g = new Game();
    	//TestDungeonGenerator();
    	TestGUI(g);
    	//g.generateNextLevel();
    	//DungeonLevel dun = g.getLevel(0);
    	//g.getLevel(0).printMap();
    	
        //Tile t = dun.getMap()[25][25];
        g.start();
    }
}
