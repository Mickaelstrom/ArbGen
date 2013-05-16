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
	private Action action = new Action();
	private Fenetre reference;
	private JButton setarbre = new JButton("Affiche une table");
	private JButton newtree = new JButton("Nouvel arbre");
	private JButton changetree = new JButton("Charger arbre");

	public Panel_Accueil(Fenetre ref) {

		reference = ref;

		tree = new JLabel(new ImageIcon("resources/arbre.png"));
		// tree.setMinimumSize(new Dimension(50,50));

		JPanel bottomPanel = new JPanel(new GridLayout(3, 1));

		// newtree.setPreferredSize(new Dimension(50,100));

		this.add(tree, BorderLayout.CENTER);
		bottomPanel.add(newtree);
		bottomPanel.add(changetree);
		bottomPanel.add(setarbre);
		this.add(bottomPanel, BorderLayout.EAST);

		setarbre.addActionListener(action);
		newtree.addActionListener(action);
		changetree.addActionListener(action);

	}

	class Action implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == setarbre) {
				new Tableau("Afficher une table", false, null);
			} else if (e.getSource() == newtree) {
				reference.setPanel(2);
			} else if (e.getSource() == changetree) {
				reference.setPanel(3);
			}
		}
	}

}
