package gameBase;

import asciiPanel.AsciiFont;
import level.DungeonLevel;

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
    
    public static void TestGUI(Game g, AsciiFont font){
    	FrameBuilder f = new FrameBuilder();
    	f.buildFrame(100, 50, g, font);	
    	
    }
    
    public static void main(String[] args){
    	AsciiFont font = AsciiFont.CP437_16x16;
    	Game g = new Game();
    	TestGUI(g, font);
        g.start();
    }
}
