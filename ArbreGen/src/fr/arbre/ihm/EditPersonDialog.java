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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DateFormatter;

import fr.arbre.dao.csv.CsvPersonDao;
import fr.arbre.dao.csv.PersonIdException;
import fr.arbre.model.Gender;
import fr.arbre.model.SimplePerson;

/**
 * Classe qui permet d'afficher une fenêtre de création/édition d'une personne
 * 
 * Une fenêtre peut etre appelé en statique par showDialog
 */
@SuppressWarnings("serial")
public class EditPersonDialog extends JDialog implements ActionListener {

	private final String ADD_PIC = "add pic", DEL_PIC = "del pic", ADD_FATHER = "add father",
			DEL_FATHER = "del father", ADD_MOTHER = "add mother", DEL_MOTHER = "del mother",
			ADD_CHILD = "add child", DEL_CHILD = "del child", CANCEL = "cancel";

	// -----------------------------------------------------------------

	// private static EditPersonDialog dialog; // la fenetre
	private SimplePerson thePerson; // la personne édité/crée

	/**
	 * Ce showDialog affiche la fenêtre de création d'une personne.
	 * 
	 * @param frameComp
	 *            Composant par rapport auquel elle est affiché
	 * @return Une référence sur la SimplePerson créée
	 */
	public static SimplePerson showDialog(Component frameComp) {
		Frame frame = JOptionPane.getFrameForComponent(frameComp);
		EditPersonDialog dialog = new EditPersonDialog(frame, "Créer une personne", null);
		dialog.setVisible(true);
		return dialog.getThePerson();
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
		EditPersonDialog dialog = new EditPersonDialog(frame, "Editer une personne", person);
		dialog.setVisible(true);
		return dialog.getThePerson();
	}

	/**
	 * Utilisé pour récupérer la personne quand on clique sur le bouton valider ou annuler.
	 * 
	 * @return La personne éditée/créée si validation, sinon retourne null (si cancel)
	 */
	private SimplePerson getThePerson() {
		return thePerson;
	}

	/*-----------------------------------------------------------------------*/

	private JLabel pictureLabel;
	private JTextField fatherNameField;
	private JTextField motherNameField;

	ButtonGroup radioGroup;
	public static JScrollPane scrollpane = new JScrollPane();

	private EditPersonDialog(Frame frame, String title, SimplePerson person) {
		super(frame, title, true);
		setIconImage(new ImageIcon("resources/Icons/personne-16.png").getImage());
		setResizable(false);

		thePerson = (person == null) ? new SimplePerson() : person.clone();

		final Dimension icoButDim = new Dimension(28, 28);
		// PARTIE GAUCHE ------------------------------------------------------
		JPanel leftPanel = new JPanel();

		BoxLayout leftLayout = new BoxLayout(leftPanel, BoxLayout.Y_AXIS);
		leftPanel.setLayout(leftLayout);

		JPanel lgbPanel = new JPanel(new GridBagLayout());

		// Name
		final JTextField nameField = new JTextField();
		nameField.setPreferredSize(new Dimension(200, 20));
		JLabel nameLabel = new JLabel("Nom ");

		// Fisrt Name
		final JTextField firstNameField = new JTextField();
		firstNameField.setPreferredSize(new Dimension(200, 20));
		JLabel firstNameLabel = new JLabel("Prénom ");

		// Gender
		JLabel genderLabel = new JLabel("Genre ");
		JPanel panGender = new JPanel();

		ButtonGroup radioGroup = new ButtonGroup();
		final JRadioButton maleButton;
		radioGroup.add(maleButton = new JRadioButton("Homme"));
		panGender.add(maleButton);
		panGender.add(Box.createVerticalStrut(30));
		final JRadioButton femaleButton;
		radioGroup.add(femaleButton = new JRadioButton("Femme"));
		panGender.add(femaleButton);

		// Birthdate
		JLabel birthDateLabel = new JLabel("Né(e) le ");

		DateFormat formatBirth = new SimpleDateFormat("dd/MM/yyyy");
		DateFormatter db = new DateFormatter(formatBirth);
		final JFormattedTextField dateFieldBirth = new JFormattedTextField(db);
		dateFieldBirth.setPreferredSize(new Dimension(20, 20));
		dateFieldBirth.setValue(new Date());

		// Date of death
		/*
		 * FIXME garder ou non ? telle est la question... JLabel dateDeathLabel = new
		 * JLabel("Parti(e) le ");
		 * 
		 * DateFormat formatDeath = new SimpleDateFormat("dd/MM/yyyy"); DateFormatter df = new
		 * DateFormatter(formatDeath); final JFormattedTextField dateFieldDeath = new
		 * JFormattedTextField(df); dateFieldDeath.setPreferredSize(new Dimension(20, 20));
		 * dateFieldDeath.setValue(new Date());
		 */

		// TODO Enfant à modifier plus tard
		JLabel childLabel = new JLabel("Enfant(s) ");
		String child[] = { "Household", "Office", "Extended Family", "Company (US)",
				"Company (World)", "Team", "Will", "Birthday Card List", "High School", "Country",
				"Continent", "Planet" };
		final JList<String> listChild = new JList<String>(child);

		listChild.setVisibleRowCount(4);
		scrollpane = new JScrollPane(listChild);

		JButton buttonAddChild = new JButton(new ImageIcon("resources/Icons/ajouter-16.png"));
		buttonAddChild.setPreferredSize(icoButDim);
		buttonAddChild.setMaximumSize(icoButDim);
		buttonAddChild.setActionCommand(ADD_CHILD);
		buttonAddChild.addActionListener(this);

		JButton buttonDelChild = new JButton(new ImageIcon("resources/Icons/supprimer-16.png"));
		buttonDelChild.setPreferredSize(icoButDim);
		buttonDelChild.setMaximumSize(icoButDim);
		buttonDelChild.setActionCommand(DEL_CHILD);
		buttonDelChild.addActionListener(this);

		Box vertiE = Box.createVerticalBox();
		vertiE.add(buttonAddChild);
		vertiE.add(Box.createVerticalStrut(20));
		vertiE.add(buttonDelChild);

		JPanel panChild = new JPanel();
		panChild.add(vertiE);

		// --------------------------------------------------------------------
		GridBagConstraints g = new GridBagConstraints();
		g.fill = GridBagConstraints.HORIZONTAL;
		g.insets = new Insets(5, 5, 5, 5);

		g.gridy = 0;
		g.gridx = 0;
		g.weightx = 0;
		g.gridwidth = 1;
		lgbPanel.add(nameLabel, g);

		g.gridy = 0;
		g.gridx = 1;
		g.weightx = 0;
		g.gridwidth = 7;
		lgbPanel.add(nameField, g);

		g.gridy = 1;
		g.gridx = 0;
		g.weightx = 0;
		g.gridwidth = 1;
		lgbPanel.add(firstNameLabel, g);

		g.gridy = 1;
		g.gridx = 1;
		g.weightx = 0;
		g.gridwidth = 7;
		lgbPanel.add(firstNameField, g);

		g.gridy = 2;
		g.gridx = 0;
		g.weightx = 0;
		g.gridwidth = 1;
		lgbPanel.add(genderLabel, g);

		g.gridy = 2;
		g.gridx = 2;
		g.weightx = 0;
		g.gridwidth = 6;
		lgbPanel.add(panGender, g);

		g.gridy = 3;
		g.gridx = 0;
		g.weightx = 0;
		g.gridwidth = 1;
		lgbPanel.add(birthDateLabel, g);

		g.gridy = 3;
		g.gridx = 2;
		g.weightx = 0;
		g.gridwidth = 6;
		lgbPanel.add(dateFieldBirth, g);

		/*
		 * FIXME garder ou non ? g.gridy = 4; g.gridx = 0; g.weightx = 0; g.gridwidth = 1;
		 * lgbPanel.add(dateDeathLabel, g);
		 * 
		 * g.gridy = 4; g.gridx = 2; g.weightx = 0; g.gridwidth = 6; lgbPanel.add(dateFieldDeath,
		 * g);
		 */

		g.gridy = 4;// 5;
		g.gridx = 0;
		g.weightx = 0;
		g.gridwidth = 1;
		lgbPanel.add(childLabel, g);

		g.gridy = 4;// 5;
		g.gridx = 2;
		g.weightx = 0;
		g.gridwidth = 6;
		lgbPanel.add(scrollpane, g);

		g.gridy = 4;// 5;
		g.gridx = 8;
		g.weightx = 0;
		g.gridwidth = 1;
		lgbPanel.add(panChild, g);

		// PARTIE DROITE ------------------------------------------------------
		JPanel rightPanel = new JPanel();

		BoxLayout rightLayout = new BoxLayout(rightPanel, BoxLayout.Y_AXIS);
		rightPanel.setLayout(rightLayout);

		JPanel rgbPanel = new JPanel();
		GridBagLayout rgbLayout = new GridBagLayout();
		rgbPanel.setLayout(rgbLayout);

		pictureLabel = new JLabel(new ImageIcon("resources/Pictures/vide.jpg"));
		pictureLabel.setPreferredSize(new Dimension(140, 140));
		Border border = BorderFactory.createLineBorder(Color.black);
		pictureLabel.setBorder(border);
		pictureLabel.setAlignmentX(CENTER_ALIGNMENT);

		JButton buttonAddPic = new JButton(new ImageIcon("resources/Icons/ajouter-16.png"));
		buttonAddPic.setPreferredSize(icoButDim);
		buttonAddPic.setActionCommand(ADD_PIC);
		buttonAddPic.addActionListener(this);

		JButton buttonDelPic = new JButton(new ImageIcon("resources/Icons/supprimer-16.png"));
		buttonDelPic.setPreferredSize(icoButDim);
		buttonDelPic.setActionCommand(DEL_PIC);
		buttonDelPic.addActionListener(this);

		JPanel picButtonsPanel = new JPanel();
		FlowLayout picLayout = new FlowLayout();
		picButtonsPanel.setLayout(picLayout);
		picButtonsPanel.setAlignmentX(CENTER_ALIGNMENT);

		JLabel fatherLabel = new JLabel("Père");
		fatherNameField = new JTextField(18);
		fatherNameField.setEditable(false);

		JButton buttonAddFather = new JButton(new ImageIcon("resources/Icons/ajouter-16.png"));
		buttonAddFather.setPreferredSize(icoButDim);
		buttonAddFather.setActionCommand(ADD_FATHER);
		buttonAddFather.addActionListener(this);

		JButton buttonDelFather = new JButton(new ImageIcon("resources/Icons/supprimer-16.png"));
		buttonDelFather.setPreferredSize(icoButDim);
		buttonDelFather.setActionCommand(DEL_FATHER);
		buttonDelFather.addActionListener(this);

		JLabel motherLabel = new JLabel("Mère");
		motherNameField = new JTextField(18);
		motherNameField.setEditable(false);

		JButton buttonAddMother = new JButton(new ImageIcon("resources/Icons/ajouter-16.png"));
		buttonAddMother.setPreferredSize(icoButDim);
		buttonAddMother.setActionCommand(ADD_MOTHER);
		buttonAddMother.addActionListener(this);

		JButton buttonDelMother = new JButton(new ImageIcon("resources/Icons/supprimer-16.png"));
		buttonDelMother.setPreferredSize(icoButDim);
		buttonDelMother.setActionCommand(DEL_MOTHER);
		buttonDelMother.addActionListener(this);

		JButton buttonOk = new JButton("Ok");
		buttonOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nameFieldText = nameField.getText();
				System.out.println(nameFieldText);
				thePerson.setName(nameFieldText);

				String firstNameFieldText = firstNameField.getText();
				System.out.println(firstNameFieldText);
				thePerson.setFirstname(firstNameFieldText);

				String genderText;
				genderText = femaleButton.isSelected() ? femaleButton.getText() : maleButton
						.isSelected() ? maleButton.getText() : "non determine";
				System.out.println(genderText);
				Gender genderTextField = null;
				if (genderText == "Homme") {
					genderTextField = fr.arbre.model.Gender.MALE;
				}
				if (genderText == "Femme") {
					genderTextField = fr.arbre.model.Gender.FEMALE;
				}
				thePerson.setGender(genderTextField);

				String birthDateText = dateFieldBirth.getText();
				System.out.println(birthDateText);
				thePerson.setBirthdate(birthDateText);

				/*
				 * String deathDateText = dateFieldDeath.getText();
				 * System.out.println(deathDateText); thePerson.setBirthdate(deathDateText);
				 */

				String childText = (String) listChild.getSelectedValue();
				System.out.println(childText);

				setVisible(false);
			}
		});

		JButton buttonCancel = new JButton("Annuler");
		buttonCancel.setActionCommand(CANCEL);
		buttonCancel.addActionListener(this);

		JPanel dialogButtonsPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));

		// --------------------------------------------------------------------

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

		leftPanel.add(lgbPanel);

		rightPanel.add(pictureLabel);
		rightPanel.add(picButtonsPanel);
		rightPanel.add(rgbPanel);
		rightPanel.add(dialogButtonsPanel);

		setLayout(new FlowLayout());
		add(leftPanel);
		add(rightPanel);

		// --------------------------------------------------------------------

		initFields();
		pack();
		setLocationRelativeTo(frame);
	}

	/**
	 * Sert à initialiser (et rafraichir) les champs en fonction de <i>thePerson</i>, par exemple si
	 * c'est une personne à éditer.
	 */
	private void initFields() {
		CsvPersonDao dao = CsvPersonDao.getInstance();
		SimplePerson temp = null;

		/*
		 * TODO ajouter l'initialisation des champs nom, prenom,..., liste des enfants (c'est pour
		 * quand on édite)
		 */

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
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		/*
		 * TODO les boutons pour ajouter un père/mère/enfant(s) -> ouvre une fenetre de choix
		 * 'Selection personne' à partir de l'objet csv2array
		 */
		switch (arg0.getActionCommand()) {
		case ADD_PIC:
			addPic();
			break;
		case DEL_PIC:
			thePerson.setPicname("");
			pictureLabel.setIcon(new ImageIcon("resources/Pictures/vide.jpg"));
			break;
		case ADD_FATHER:
			addFather();
			break;
		case DEL_FATHER:
			thePerson.setFatherId(0);
			fatherNameField.setText("");
			break;
		case ADD_MOTHER:
			addMother();
			break;
		case DEL_MOTHER:
			thePerson.setMotherId(0);
			motherNameField.setText("");
			break;
		case ADD_CHILD:
			System.out.println("add child(ren)");
			addChild();
			break;
		case DEL_CHILD:
			System.out.println("del child");
			// FIXME lambda.deleteChildId(123456);
			break;
		case CANCEL:
			thePerson = null;
			setVisible(false);
			break;
		}
	}

	private void addPic() {
		JFileChooser fc = new JFileChooser("resources/Pictures/");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg",
				"png", "bmp", "gif");
		fc.setFileFilter(filter);
		if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			thePerson.setPicname(fc.getSelectedFile().getName());
			if (!thePerson.getPicname().isEmpty())
				pictureLabel.setIcon(new ImageIcon("resources/Pictures/" + thePerson.getPicname()));
		}

	}

	private void addFather() {
		thePerson.setFatherId(Tableau.showPersonSelection(null, "Sélectionner le père"));

		if (thePerson.getFatherId() > 0) {
			SimplePerson temp = null;
			try {
				temp = CsvPersonDao.getInstance().getPerson(thePerson.getFatherId());
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
		}
	}

	private void addMother() {
		thePerson.setMotherId(Tableau.showPersonSelection(null, "Sélectionner la mère"));

		if (thePerson.getMotherId() > 0) {
			SimplePerson temp = null;
			try {
				temp = CsvPersonDao.getInstance().getPerson(thePerson.getMotherId());
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
		}
	}

	private void addChild() {
		// FIXME thePerson.addChildId(123456);
	}
}
