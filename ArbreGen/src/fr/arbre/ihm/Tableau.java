package fr.arbre.ihm;

import javax.swing.JDialog;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Tableau extends JDialog {

	Panel_Csv2Array	tableau	= new Panel_Csv2Array();

	public Tableau(JFrame parent, String title, boolean modal) {
		// On appelle le construteur de JDialog correspondant
		super(parent, title, modal);

		Panel_Csv2Array tableau = new Panel_Csv2Array();

		// On sp�cifie une taille
		this.setSize(200, 80);

		this.add(tableau);
		// this.add(new JLabel("hello"));

		// La bo�te ne devra pas �tre redimensionnable
		this.setResizable(false);
		// Enfin on l'affiche
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		// Tout ceci ressemble � ce que nous faisons depuis le d�but avec notre JFrame.
	}

}
