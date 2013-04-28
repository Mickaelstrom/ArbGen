package fr.arbre.ihm;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import fr.arbre.model.SimplePerson;

public class EditPersonDialog extends JDialog implements ActionListener {
	private static final long serialVersionUID = 9047490617977399646L;
	// -----------------------------------------------------------------

	private static EditPersonDialog dialog;
	private static SimplePerson value = new SimplePerson();

	public static SimplePerson showDialog(Component frameComp,
			Component locationComp, String title) {
		Frame frame = JOptionPane.getFrameForComponent(frameComp);
		dialog = new EditPersonDialog(frame, locationComp, title);
		dialog.setVisible(true);
		return value;
	}

	private EditPersonDialog(Frame frame, Component locationComp, String title) {
		super(frame, title, true);

		// TODO mettre le contenu de la fenetre ici
		
		pack();
		setLocationRelativeTo(locationComp);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		/* 
		 * TODO les boutons pour ajouter un père/mère/enfant(s)
		 * -> ouvre une fenetre de choix a partir de l'objet csv2array
		 */
	}

}
