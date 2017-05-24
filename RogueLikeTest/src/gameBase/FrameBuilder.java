package gameBase;
import java.awt.Dimension;
import javax.swing.JFrame;
import java.awt.Color;
import asciiPanel.*;
public class FrameBuilder {
	public FrameBuilder(){
		
	}
	
	public GameFrame buildFrame(int x, int y, Game g){
		
		AsciiFont font = AsciiFont.CP437_8x8;
		
		AsciiPanel panel1 = new AsciiPanel(x,y,font);
		
		GameFrame frame = new GameFrame("RogueLike", g);
		g.addPanel(panel1);
		frame.add(panel1);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setSize((int)frame.getPreferredSize().getWidth() + 6, (int)frame.getPreferredSize().getHeight() + 29);
		frame.setResizable(false);
		frame.setVisible(true);
		return frame;
	}
	
}
