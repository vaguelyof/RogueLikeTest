import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

public class GameGUI extends JFrame {
	private JButton[][] buttonArray;
	private JButton newGameButton;
	private JButton exitButton;
	private JLabel health;
	final static int NUM_ROWS = 11;
	final static int NUM_COLS = 11;
	final static int PIXEL_CONSTANT = 64;
	
	
	public GameGUI() {
		super("A Pixel Odyssey");
		setSize(300,500);
		setResizable(false);
		Container container = getContentPane();
		container.setLayout(new GridLayout(4,1));
		
		newGameButton = new JButton("Start New Game");
		exitButton = new JButton("Exit");
	    
		MenuHandler menuHandler = new MenuHandler(this);
		
		container.add(newGameButton);
		newGameButton.addActionListener(menuHandler);
		
		container.add(exitButton);
		exitButton.addActionListener(menuHandler);
		
		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private class VisibleGUI extends JFrame{
		public VisibleGUI(){
			super("Rogue");
			setSize(NUM_COLS* PIXEL_CONSTANT, (NUM_ROWS) * PIXEL_CONSTANT);
			setResizable(false);
			Container container = getContentPane();
			container.setLayout(new GridLayout(NUM_ROWS, NUM_COLS));
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			buttonArray = new JButton[NUM_ROWS][NUM_COLS];
			ButtonHandler buttonHandler = new ButtonHandler();
			for (int r = 0; r < NUM_ROWS; r++)
				for (int c = 0; c < NUM_COLS; c++) {
					buttonArray[r][c] = new JButton(r + "," + c);
					// instantiate each JButton with a row/col label
					container.add(buttonArray[r][c]);
					// add the JButton to the Container
					buttonArray[r][c].addActionListener(buttonHandler);
					// register the JButton with the event handler
				}
		}
	}
	
	private class HelpGUI extends JFrame{
		public HelpGUI(){
			super("How To Play");
		}
		
	}
	
	private class MenuHandler implements ActionListener {
		GameGUI gui;
		public MenuHandler(GameGUI g){
			super();
			gui = g;
		}
		
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == exitButton){
				gui.setVisible(false);
				gui.dispose();
			}
			else if(e.getSource() == newGameButton){
				gui.setVisible(false);
				
				VisibleGUI g = new VisibleGUI();
				g.setVisible(true);
				g.setLocationRelativeTo(null);
				gui.dispose();
			}
		}
		
	}
	
	private class ButtonHandler implements ActionListener {
		
		public void actionPerformed(ActionEvent event) {
			for (int r = 0; r < NUM_ROWS; r++)
				for (int c = 0; c < NUM_COLS; c++) {
					if (event.getSource() == buttonArray[r][c]) {
						System.out.println("Button at row " + r + ",col " + c + " pressed");
					}
				}
		}

	}

}