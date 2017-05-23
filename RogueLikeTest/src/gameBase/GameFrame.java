package gameBase;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class GameFrame extends JFrame {
	private Game game;
	private JPanel contentPane;

	public GameFrame(String s, Game g) {
		super(s);
		addKeyListener(new KeyHandler());
		game = g;
	}
	
	private class KeyHandler implements KeyListener{
		
		
		
		@Override
		public void keyPressed(KeyEvent e) {
			game.getKeyPress(KeyEvent.getKeyText(e.getKeyCode()));
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

	}

}
