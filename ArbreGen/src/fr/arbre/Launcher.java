package fr.arbre;

import javax.swing.JFrame;

public class Launcher {

	public static void main(String[] args) {
		/**
		 * TODO à implémenter correctement au final
		 */
		JFrame frame = new JFrame("Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

		frame.pack();
		frame.setVisible(true);
	}

}
