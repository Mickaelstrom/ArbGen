package fr.arbre.ihm;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import fr.arbre.dao.csv.CsvPersonDao;
import fr.arbre.dao.csv.PersonIdException;
import fr.arbre.model.SimplePerson;

/**
 * Classe qui permet d'afficher une fenetre de cr�ation/�dition d'une personne
 * 
 * Une fenetre peut etre appel� en statique par showDialog
 */
@SuppressWarnings("serial")
public class EditPersonDialog extends JDialog implements ActionListener {

	private final String ADD_PIC = "add pic", DEL_PIC = "del pic", ADD_FATHER = "add father",
			DEL_FATHER = "del father", ADD_MOTHER = "add mother", DEL_MOTHER = "del mother",
			ADD_CHILD = "add child", DEL_CHILD = "del child", OK = "ok", CANCEL = "cancel";

	// -----------------------------------------------------------------

	private static EditPersonDialog dialog; // la fenetre
	private SimplePerson thePerson; // la personne �dit�/cr�e

	/**
	 * Ce showDialog affiche la fen�tre de cr�ation d'une personne.
	 * 
	 * @param frameComp
	 *            Composant par rapport auquel elle est affich�
	 * @return Une r�f�rence sur la SimplePerson cr��e
	 */
	public static SimplePerson showDialog(Component frameComp) {
		Frame frame = JOptionPane.getFrameForComponent(frameComp);
		dialog = new EditPersonDialog(frame, "Cr�er une personne", null);
		dialog.setVisible(true);
		return dialog.getThePerson();
	}

	/**
	 * Ce showDialog affiche la fen�tre d'�dition d'une personne
	 * 
	 * @param frameComp
	 *            Composant par rapport auquel elle est affich�
	 * @return Une r�f�rence sur la SimplePerson modifi�
	 */
	public static SimplePerson showDialog(Component frameComp, SimplePerson person) {
		Frame frame = JOptionPane.getFrameForComponent(frameComp);
		dialog = new EditPersonDialog(frame, "Editer une personne", person);
		dialog.setVisible(true);
		return dialog.getThePerson();
	}

	/**
	 * Utilis� pour r�cup�rer la personne quand on clique sur le bouton valider ou annuler.
	 * 
	 * @return La personne �dit�e/cr��e si validation, sinon retourne null (si cancel)
	 */
	private SimplePerson getThePerson() {
		return thePerson;
	}

	/*-----------------------------------------------------------------------*/

	private JLabel pictureLabel;
	private JTextField fatherNameField;
	private JTextField motherNameField;

	private EditPersonDialog(Frame frame, String title, SimplePerson person) {
		super(frame, title, true);
		this.setIconImage(new ImageIcon("resources/Icons/personne-16.png").getImage());
		// this.setResizable(false); // TODO ajouter cette option � la fin
		if (person == null)
			thePerson = new SimplePerson();
		else
			thePerson = person.clone();

		final int PANEL_HEIGHT = 260; // TODO supprimer quand tout sera ok
		// PARTIE GAUCHE ------------------------------------------------------
		JPanel leftPanel = new JPanel();
		leftPanel.setMinimumSize(new Dimension(200, PANEL_HEIGHT)); // TODO supprimer quand tout
																	// sera ok
		leftPanel.setPreferredSize(new Dimension(200, PANEL_HEIGHT)); // TODO supprimer quand tout
																		// sera ok

		BoxLayout leftLayout = new BoxLayout(leftPanel, BoxLayout.Y_AXIS);
		leftPanel.setLayout(leftLayout);

		JPanel lgbPanel = new JPanel();
		GridBagLayout lgbLayout = new GridBagLayout();
		lgbPanel.setLayout(lgbLayout);

		// TODO mettre le contenu de la fenetre gauche ici

		// PARTIE DROITE ------------------------------------------------------
		JPanel rightPanel = new JPanel();

		BoxLayout rightLayout = new BoxLayout(rightPanel, BoxLayout.Y_AXIS);
		rightPanel.setLayout(rightLayout);

		JPanel rgbPanel = new JPanel();
		GridBagLayout rgbLayout = new GridBagLayout();
		rgbPanel.setLayout(rgbLayout);

		pictureLabel = new JLabel(new ImageIcon("resources/Pictures/vide.jpg"));
		pictureLabel.setMinimumSize(new Dimension(140, 140));
		pictureLabel.setPreferredSize(new Dimension(140, 140));
		Border border = BorderFactory.createLineBorder(Color.black);
		pictureLabel.setBorder(border);
		pictureLabel.setAlignmentX(CENTER_ALIGNMENT);

		JButton buttonAddPic = new JButton(new ImageIcon("resources/Icons/ajouter-16.png"));
		buttonAddPic.setPreferredSize(new Dimension(28, 28));
		buttonAddPic.setMaximumSize(new Dimension(28, 28));
		buttonAddPic.setActionCommand(ADD_PIC);
		buttonAddPic.addActionListener(this);

		JButton buttonDelPic = new JButton(new ImageIcon("resources/Icons/retirer-16.png"));
		buttonDelPic.setPreferredSize(new Dimension(28, 28));
		buttonDelPic.setMaximumSize(new Dimension(28, 28));
		buttonDelPic.setActionCommand(DEL_PIC);
		buttonDelPic.addActionListener(this);

		JPanel picButtonsPanel = new JPanel();
		FlowLayout picLayout = new FlowLayout();
		picButtonsPanel.setLayout(picLayout);
		picButtonsPanel.setAlignmentX(CENTER_ALIGNMENT);

		JLabel fatherLabel = new JLabel("P�re");
		fatherNameField = new JTextField(18);
		fatherNameField.setEditable(false);

		JButton buttonAddFather = new JButton(new ImageIcon("resources/Icons/ajouter-16.png"));
		buttonAddFather.setPreferredSize(new Dimension(28, 28));
		buttonAddFather.setMaximumSize(new Dimension(28, 28));
		buttonAddFather.setActionCommand(ADD_FATHER);
		buttonAddFather.addActionListener(this);

		JButton buttonDelFather = new JButton(new ImageIcon("resources/Icons/retirer-16.png"));
		buttonDelFather.setPreferredSize(new Dimension(28, 28));
		buttonDelFather.setMaximumSize(new Dimension(28, 28));
		buttonDelFather.setActionCommand(DEL_FATHER);
		buttonDelFather.addActionListener(this);

		JLabel motherLabel = new JLabel("M�re");
		motherNameField = new JTextField(18);
		motherNameField.setEditable(false);

		JButton buttonAddMother = new JButton(new ImageIcon("resources/Icons/ajouter-16.png"));
		buttonAddMother.setPreferredSize(new Dimension(28, 28));
		buttonAddMother.setMaximumSize(new Dimension(28, 28));
		buttonAddMother.setActionCommand(ADD_MOTHER);
		buttonAddMother.addActionListener(this);

		JButton buttonDelMother = new JButton(new ImageIcon("resources/Icons/retirer-16.png"));
		buttonDelMother.setPreferredSize(new Dimension(28, 28));
		buttonDelMother.setMaximumSize(new Dimension(28, 28));
		buttonDelMother.setActionCommand(DEL_MOTHER);
		buttonDelMother.addActionListener(this);

		JButton buttonOk = new JButton("Ok");
		buttonOk.setActionCommand(OK);
		buttonOk.addActionListener(this);

		JButton buttonCancel = new JButton("Annuler");
		buttonCancel.setActionCommand(CANCEL);
		buttonCancel.addActionListener(this);

		JPanel dialogButtonsPanel = new JPanel();
		FlowLayout dbLayout = new FlowLayout(FlowLayout.TRAILING);
		dialogButtonsPanel.setLayout(dbLayout);

		// --------------------------------------------------------------------

		// leftPanel.add(lgbPanel);

		picButtonsPanel.add(buttonAddPic);
		picButtonsPanel.add(buttonDelPic);

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 5, 5, 5);

		c.gridy = 0;
		c.gridx = 0;
		c.weightx = 0;
		c.gridwidth = 1;
		rgbPanel.add(fatherLabel, c);
		c.gridx = 1;
		c.weightx = 1;
		c.gridwidth = 7;
		rgbPanel.add(fatherNameField, c);
		c.gridx = 8;
		c.weightx = 0;
		c.gridwidth = 1;
		rgbPanel.add(buttonAddFather, c);
		c.gridx = 9;
		c.weightx = 0;
		c.gridwidth = 1;
		rgbPanel.add(buttonDelFather, c);

		c.gridy = 1;
		c.gridx = 0;
		c.weightx = 0;
		c.gridwidth = 1;
		rgbPanel.add(motherLabel, c);
		c.gridx = 1;
		c.weightx = 1;
		c.gridwidth = 7;
		rgbPanel.add(motherNameField, c);
		c.gridx = 8;
		c.weightx = 0;
		c.gridwidth = 1;
		rgbPanel.add(buttonAddMother, c);
		c.gridx = 9;
		c.weightx = 0;
		c.gridwidth = 1;
		rgbPanel.add(buttonDelMother, c);

		dialogButtonsPanel.add(buttonOk);
		dialogButtonsPanel.add(buttonCancel);

		rightPanel.add(pictureLabel);
		rightPanel.add(picButtonsPanel);
		rightPanel.add(rgbPanel);
		rightPanel.add(dialogButtonsPanel);

		setLayout(new FlowLayout());
		add(leftPanel);
		add(rightPanel);

		// --------------------------------------------------------------------

		updateFields();
		pack();
		setLocationRelativeTo(frame);
	}

	/**
	 * Sert � initialiser les champs en fonction de <i>thePerson</i>, par exemple si c'est une
	 * personne � �diter.
	 */
	private void updateFields() {

		CsvPersonDao dao = CsvPersonDao.getInstance();
		SimplePerson temp = null;

		//
		//

		if (thePerson.getPicname().isEmpty())
			pictureLabel.setIcon(new ImageIcon("resources/Pictures/vide.jpg"));
		else
			pictureLabel.setIcon(new ImageIcon("resources/Pictures/" + thePerson.getPicname()));

		if (thePerson.getFatherId() > 0) {
			try {
				temp = dao.getPerson(thePerson.getFatherId());
			} catch (PersonIdException e) {
				e.printStackTrace();
				temp = null;
			}
			if (temp != null) {
				if (temp.getFirstname().isEmpty())
					fatherNameField.setText(temp.getName());
				else
					fatherNameField.setText(temp.getFirstname() + " " + temp.getName());
			} else {
				fatherNameField.setText("");
			}
		} else {
			fatherNameField.setText("");
		}

		if (thePerson.getMotherId() > 0) {
			try {
				temp = dao.getPerson(thePerson.getMotherId());
			} catch (PersonIdException e) {
				e.printStackTrace();
				temp = null;
			}
			if (temp != null) {
				if (temp.getFirstname().isEmpty())
					motherNameField.setText(temp.getName());
				else
					motherNameField.setText(temp.getFirstname() + " " + temp.getName());
			} else {
				motherNameField.setText("");
			}
		} else {
			motherNameField.setText("");
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		/*
		 * TODO les boutons pour ajouter un p�re/m�re/enfant(s) -> ouvre une fenetre de choix
		 * 'Selection personne' � partir de l'objet csv2array
		 */
		switch (arg0.getActionCommand()) {
		case ADD_PIC:
			System.out.println("add picture");
			// FIXME lambda.setPicname("azeaeaeaze");
			break;
		case DEL_PIC:
			System.out.println("del picture");
			thePerson.setPicname("");
			break;
		case ADD_FATHER:
			System.out.println("add father");
			// FIXME lambda.setFatherId(123456);
			break;
		case DEL_FATHER:
			System.out.println("del father");
			thePerson.setFatherId(0);
			break;
		case ADD_MOTHER:
			System.out.println("add mother");
			// FIXME lambda.setMotherId(123456);
			break;
		case DEL_MOTHER:
			System.out.println("del mother");
			thePerson.setMotherId(0);
			break;
		case ADD_CHILD:
			System.out.println("add child(ren)");
			// FIXME lambda.addChildId(123456);
			break;
		case DEL_CHILD:
			System.out.println("del child");
			// FIXME lambda.deleteChildId(123456);
			break;
		case CANCEL:
			thePerson = null; // pas de break!! (pour passer dans le case OK direct)
		case OK:
			this.setVisible(false);
			return;
		}

		updateFields();
	}

}
