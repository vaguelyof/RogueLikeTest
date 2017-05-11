import java.awt.Dimension;
import javax.swing.JFrame;
import java.awt.Color;
import asciiPanel.*;
public class FrameBuilder {
	public FrameBuilder(){
		
	}
	
	public GameFrame buildFrame(int x, int y){
		GameFrame frame = new GameFrame("RogueLike");
		AsciiFont font = AsciiFont.CP437_8x8;
		
		AsciiPanel panel1 = new AsciiPanel(x,y,font);
		int fontWidth = font.getWidth();
		int fontHeight = font.getHeight();
		frame.add(panel1);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setSize(frame.getPreferredSize());
		frame.setResizable(false);
		frame.setVisible(true);
		return frame;
	}
	
}
