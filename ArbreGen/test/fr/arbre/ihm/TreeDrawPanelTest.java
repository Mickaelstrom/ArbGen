package fr.arbre.ihm;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import fr.arbre.dao.csv.CsvPersonDao;
import fr.arbre.ihm.TreeDrawPanel;

public class TreeDrawPanelTest {

	public static void main(String[] args) {
		// SwingUtilities.invokeLater pour régler les problèmes de timing pour l'affichage d'image
		// (thread concurrency)
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame;
				TreeDrawPanel panel;

				panel = new TreeDrawPanel();
				frame = new JFrame("TreeDrawPanel Test");

				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				frame.getContentPane().add(panel, BorderLayout.CENTER);

				frame.pack();
				frame.setVisible(true);

				// panel.drawTest();
				CsvPersonDao dao = CsvPersonDao.getInstance();
				dao.load("resources/CSV/gen-dbz.csv");
				panel.drawTree();
			}
		});
	}

}
