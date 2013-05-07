package fr.arbre.ihm;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
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

import fr.arbre.model.SimplePerson;

/**
 * Classe qui permet d'afficher une fenetre de création/édition d'une personne
 * 
 * Une fenetre peut etre appelé en statique par showDialog
 */
@SuppressWarnings("serial")
public class EditPersonDialog extends JDialog implements ActionListener {

	private final String ADD_PIC = "add pic", DEL_PIC = "del pic", ADD_FATHER = "add father",
			DEL_FATHER = "del father", ADD_MOTHER = "add mother", DEL_MOTHER = "del mother",
			ADD_CHILD = "add child", DEL_CHILD = "del child", OK = "ok", QUIT = "quit";

	// -----------------------------------------------------------------

	private static EditPersonDialog dialog; // la fenetre
	private SimplePerson lambda; // la personne édité/crée

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

	private JLabel pictureLabel;
	private JTextField fatherNameField;
	private JTextField motherNameField;

	private EditPersonDialog(Frame frame, String title, SimplePerson person) {
		super(frame, title, true);
		this.setIconImage(new ImageIcon("resources/Icons/personne-16.png").getImage());
		this.setPreferredSize(new Dimension(500, 400));
		lambda = person;

		// PARTIE GAUCHE ------------------------------------------------------
		JPanel leftPanel = new JPanel();
		leftPanel.setMinimumSize(new Dimension(200, 400));
		leftPanel.setPreferredSize(new Dimension(200, 400));

		BoxLayout leftLayout = new BoxLayout(leftPanel, BoxLayout.Y_AXIS);
		leftPanel.setLayout(leftLayout);

		JPanel lgbPanel = new JPanel();
		GridBagLayout lgbLayout = new GridBagLayout();
		lgbPanel.setLayout(lgbLayout);

		// TODO mettre le contenu de la fenetre gauche ici

		// PARTIE DROITE ------------------------------------------------------
		JPanel rightPanel = new JPanel();
		rightPanel.setMinimumSize(new Dimension(272, 400));
		rightPanel.setPreferredSize(new Dimension(272, 400));

		BoxLayout rightLayout = new BoxLayout(rightPanel, BoxLayout.Y_AXIS);
		rightPanel.setLayout(rightLayout);

		JPanel rgbPanel = new JPanel();
		GridBagLayout rgbLayout = new GridBagLayout();
		rgbPanel.setLayout(rgbLayout);

		pictureLabel = new JLabel(new ImageIcon("resources/Pictures/strawberry.jpg"));
		pictureLabel.setMinimumSize(new Dimension(100, 120));
		pictureLabel.setPreferredSize(new Dimension(100, 120));
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

		JLabel fatherLabel = new JLabel("Père");
		fatherNameField = new JTextField(15);
		fatherNameField.setEditable(false);
		fatherNameField.setText("Franklin Delano Roosevelt"); // TODO à enlever...

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

		JLabel motherLabel = new JLabel("Mère");
		motherNameField = new JTextField(15);
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
		buttonOk.setAlignmentX(RIGHT_ALIGNMENT);

		JButton buttonCancel = new JButton("Annuler");
		buttonCancel.setActionCommand(QUIT);
		buttonCancel.addActionListener(this);
		buttonCancel.setAlignmentX(RIGHT_ALIGNMENT);
		
		JPanel dialogButtonsPanel = new JPanel();
		dialogButtonsPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		BoxLayout dbLayout = new BoxLayout(dialogButtonsPanel, BoxLayout.X_AXIS);
		dialogButtonsPanel.setLayout(dbLayout);
		//dialogButtonsPanel.setAlignmentX(RIGHT_ALIGNMENT);

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
		c.gridwidth = 6;
		rgbPanel.add(fatherNameField, c);
		c.gridx = 7;
		c.weightx = 0;
		c.gridwidth = 1;
		rgbPanel.add(buttonAddFather, c);
		c.gridx = 8;
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
		c.gridwidth = 6;
		rgbPanel.add(motherNameField, c);
		c.gridx = 7;
		c.weightx = 0;
		c.gridwidth = 1;
		rgbPanel.add(buttonAddMother, c);
		c.gridx = 8;
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
	 * Sert à initialiser les champs en fonction de <i>lambda</i> par exemple si c'est une personne
	 * à éditer.
	 */
	private void updateFields() {

		/**
		 * TODO decommenter
		 * 
		 * CsvPersonDao dao = CsvPersonDao.getInstance(); SimplePerson temp = null; try { temp =
		 * dao.getPerson(lambda.getMotherId()); } catch (PersonIdException e) { e.printStackTrace();
		 * } motherNameField.setText(temp.getFirstname() + " " + temp.getName());
		 */
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		/*
		 * TODO les boutons pour ajouter un père/mère/enfant(s) -> ouvre une fenetre de choix
		 * 'Selection personne' à partir de l'objet csv2array
		 */
		switch (arg0.getActionCommand()) {
		case ADD_PIC:
			System.out.println("add picture");
			// lambda.setPicname("azeaeaeaze");
			break;
		case DEL_PIC:
			System.out.println("del picture");
			lambda.setPicname("");
			break;
		case ADD_FATHER:
			System.out.println("add father");
			// lambda.setFatherId(123456);
			break;
		case DEL_FATHER:
			System.out.println("del father");
			lambda.setFatherId(0);
			break;
		case ADD_MOTHER:
			System.out.println("add mother");
			// lambda.setMotherId(123456);
			break;
		case DEL_MOTHER:
			System.out.println("del mother");
			lambda.setMotherId(0);
			break;
		case ADD_CHILD:
			System.out.println("add child(ren)");
			// lambda.addChildId(123456);
			break;
		case DEL_CHILD:
			System.out.println("del child");
			// lambda.deleteChildId(123456);
			break;
		}

		updateFields();
	}

}
