package main;

import javax.swing.JFrame;

/**
 * This is the class that launchs the application
 * @author bovacu
 *
 */

public class Chess {
	
	public static void main(String args[]) {
		JFrame f = new JFrame("Chess");
		f.setVisible(true);
		f.setResizable(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setContentPane(new ChessBoard(f, 2));
		f.invalidate();
        f.validate();
	}
}
