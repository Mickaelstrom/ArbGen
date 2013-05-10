package fr.arbre.ihm;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Panel_Accueil extends JPanel {
	private JLabel tree;

	private Popup_Action popup_action = new Popup_Action();

	public Panel_Accueil() {

		tree = new JLabel(new ImageIcon("resources/arbre.png"));
		// tree.setMinimumSize(new Dimension(50,50));

		JPanel bottomPanel = new JPanel(new GridLayout(3, 1));
		JButton newtree = new JButton("Nouvel arbre");
		JButton changetree = new JButton("Changer arbre");
		JButton setarbre = new JButton("Affiche une table");

		// newtree.setPreferredSize(new Dimension(50,100));

		this.add(tree, BorderLayout.CENTER);
		bottomPanel.add(newtree);
		bottomPanel.add(changetree);
		bottomPanel.add(setarbre);
		this.add(bottomPanel, BorderLayout.EAST);

		setarbre.addActionListener(popup_action);
	}

	class Popup_Action implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			new Tableau(null, "Afficher une table", false);
		}
	}

}
