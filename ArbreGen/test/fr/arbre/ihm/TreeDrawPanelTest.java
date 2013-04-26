package fr.arbre.ihm;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import fr.arbre.ihm.TreeDrawPanel;

public class TreeDrawPanelTest {

	public static void main(String[] args) {
		JFrame frame;
		TreeDrawPanel panel;

		panel = new TreeDrawPanel();

		frame = new JFrame("TreeDrawPanel Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);

		panel.drawTest();
	}

}
