package breakoutgame;

import java.awt.Color;
import javax.swing.*;
import javax.swing.JFrame;

public class Breakout extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private BreakoutPanel panel;
	
	public Breakout() {
		// Set the size of the screen (use Settings.WINDOW_WIDTH/HEIGHT)
		setSize(Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT);
		// Set the title
		setTitle("Breakout");
		// Set the background colour to white
		getContentPane().setBackground(Color.WHITE);
		// Set resizable to false
		setResizable(false);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    panel = new BreakoutPanel(this);
	    add(panel);
	    panel.requestFocus(); // Set focus on the panel
		// Set visible to true
		setVisible(true);
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
	         public void run() {
	        	 new Breakout();	
	         }
		});
	}
}
