
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
    
    public static void TestGUI(){
    	FrameBuilder f = new FrameBuilder();
    	f.buildFrame(50, 50);	
    	
    }
    
    public static void main(String[] args){
    	TestDungeonGenerator();
    	TestGUI();
    }
}
