package fr.arbre.ihm;

import java.awt.Component;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import fr.arbre.model.SimplePerson;

public class EditPersonDialog extends JDialog implements ActionListener {
	private static final long		serialVersionUID	= 9047490617977399646L;

	// -----------------------------------------------------------------

	private static EditPersonDialog	dialog;
	private SimplePerson			lambda;

	/**
	 * Ce showDialog affiche la fenêtre de création d'une personne
	 * 
	 * @param frameComp
	 *            Composant par rapport auquel elle est affiché
	 * @return Une référence sur la SimplePerson créée
	 */
	public static SimplePerson showDialog(Component frameComp) {
		SimplePerson person = new SimplePerson();
		Frame frame = JOptionPane.getFrameForComponent(frameComp);
		dialog = new EditPersonDialog(frame, "Créer une personne", person);
		dialog.setVisible(true);
		return person;
	}

	/**
	 * Ce showDialog affiche la fenêtre d'édition d'une personne
	 * 
	 * @param frameComp
	 *            Composant par rapport auquel elle est affiché
	 * @return Une référence sur la SimplePerson modifié
	 */
	public static SimplePerson showDialog(Component frameComp, SimplePerson person) {
		Frame frame = JOptionPane.getFrameForComponent(frameComp);
		dialog = new EditPersonDialog(frame, "Editer une personne", person);
		dialog.setVisible(true);
		return person;
	}

	/*-----------------------------------------------------------------------*/

	private final String	fatherNameString	= "Père";
	private final String	motherNameString	= "Mère";
	private JTextField		fatherNameField;
	private JTextField		motherNameField;

	private EditPersonDialog(Frame frame, String title, SimplePerson person) {
		super(frame, title, true);
		this.setIconImage(new ImageIcon("resources/Icons/personne-16.png").getImage());
		lambda = person;

		/* PARTIE GAUCHE ----------------------------------------------------------- */

		// TODO mettre le contenu de la fenetre ici

		/* PARTIE DROITE ----------------------------------------------------------- */
		setLayout(new GridBagLayout()); // TODO à adapter quand classe complété
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 5, 5, 5);

		JLabel fatherLabel = new JLabel(fatherNameString);
		c.gridx = 0;
		c.weightx = 0;
		c.gridwidth = 3;
		add(fatherLabel, c);
		
		fatherNameField = new JTextField(12);
		fatherNameField.setEditable(false);
		c.gridx = 3;
		c.weightx = 1;
		c.gridwidth = 7;
		add(fatherNameField, c);
		
		JButton buttonFplus = new JButton(new ImageIcon("resources/Icons/ajouter-16.png"));
		c.gridx = 10;
		c.weightx = 0;
		c.gridwidth = 1;
		add(buttonFplus, c);
		
		JButton buttonFminus = new JButton(new ImageIcon("resources/Icons/retirer-16.png"));
		c.gridx = 11;
		c.gridwidth = 1;
		add(buttonFminus, c);

		
		c.gridy = 1;
		JLabel motherLabel = new JLabel(motherNameString);
		c.gridx = 0;
		c.weightx = 0;
		c.gridwidth = 3;
		add(motherLabel, c);
		
		motherNameField = new JTextField(12);
		motherNameField.setEditable(false);
		c.gridx = 3;
		c.weightx = 1;
		c.gridwidth = 7;
		add(motherNameField, c);
		
		JButton buttonMplus = new JButton(new ImageIcon("resources/Icons/ajouter-16.png"));
		c.gridx = 10;
		c.weightx = 0;
		c.gridwidth = 1;
		add(buttonMplus, c);
		
		JButton buttonMminus = new JButton(new ImageIcon("resources/Icons/retirer-16.png"));
		c.gridx = 11;
		c.gridwidth = 1;
		add(buttonMminus, c);

		
		initFields();
		pack();
		setLocationRelativeTo(frame);
	}

	/**
	 * Sert à initialiser les champs en fonction de 'lambda' par exemple si c'est une personne à
	 * éditer.
	 */
	private void initFields() {

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		/*
		 * TODO les boutons pour ajouter un père/mère/enfant(s) -> ouvre une fenetre de choix
		 * 'Selection personne' à partir de l'objet csv2array
		 */
	}

}
