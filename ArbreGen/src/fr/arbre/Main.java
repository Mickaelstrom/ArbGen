package fr.arbre;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import fr.arbre.ihm.TreeDrawPanel;

public class Main {

	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

		TreeDrawPanel p = new TreeDrawPanel();

		frame.getContentPane().add(p, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);

		p.drawTest();
	}

}
