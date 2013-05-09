package fr.arbre.ihm;

import javax.swing.JDialog;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Tableau extends JDialog {

	public Tableau(JFrame parent, String title, boolean modal) {
		// On appelle le construteur de JDialog correspondant
		super(parent, title, modal);

		Panel_Csv2Array tableau = new Panel_Csv2Array();

		// On spécifie une taille
		this.setSize(200, 80);

		// La position
		this.setLocationRelativeTo(null);
		this.setModal(false);

		this.add(tableau);
		// this.add(new JLabel("hello"));

		// La boîte ne devra pas être redimensionnable
		this.setResizable(false);

		// Enfin on l'affiche
		this.pack();

		this.setVisible(true);
		// Tout ceci ressemble à ce que nous faisons depuis le début avec notre JFrame.
	}

}
