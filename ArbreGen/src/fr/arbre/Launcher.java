package fr.arbre;

import javax.swing.UIManager;

import fr.arbre.ihm.Fenetre;

public class Launcher {

	public static void main(String[] args) {

		// Change le style des fenêtres
		// FIXME se mettre d'accord sur le style des fenêtres ??????
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		new Fenetre();
		// mainWindow.setExtendedState(JFrame.MAXIMIZED_BOTH); // FIXME extended or not extended
		// ????

	}

}
