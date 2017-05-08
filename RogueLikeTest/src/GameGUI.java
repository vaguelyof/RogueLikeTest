import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

public class GameGUI extends JFrame {
	private JButton[][] buttonArray;
	final static int NUM_ROWS = 8;
	final static int NUM_COLS = 8;

	public GameGUI() {
		super("Why chello there");
		setSize(275, 270);
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