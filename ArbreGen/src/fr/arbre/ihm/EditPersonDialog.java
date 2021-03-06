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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
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
 * Classe qui permet d'afficher une fen�tre de cr�ation/�dition d'une personne
 * 
 * Une fen�tre peut etre appel� en statique par showDialog
 */
@SuppressWarnings("serial")
public class EditPersonDialog extends JDialog implements ActionListener {

	private final String ADD_PIC = "add pic", DEL_PIC = "del pic", ADD_FATHER = "add father",
			DEL_FATHER = "del father", ADD_MOTHER = "add mother", DEL_MOTHER = "del mother",
			ADD_CHILD = "add child", DEL_CHILD = "del child", CANCEL = "cancel";

	// -----------------------------------------------------------------

	// private static EditPersonDialog dialog; // la fenetre
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
		EditPersonDialog dialog = new EditPersonDialog(frame, "Cr�er une personne", null);
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
		EditPersonDialog dialog = new EditPersonDialog(frame, "Editer une personne", person);
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

	private List<Integer> childrenId;
	private JLabel pictureLabel;
	private JTextField fatherNameField;
	private JTextField motherNameField;
	private DefaultListModel<String> listChildModel;
	private JList<String> listChild;

	ButtonGroup radioGroup;
	public static JScrollPane scrollpane = new JScrollPane();

	private EditPersonDialog(Frame frame, String title, SimplePerson person) {
		super(frame, title, true);
		setIconImage(new ImageIcon("resources/Icons/personne-16.png").getImage());
		setResizable(false);

		thePerson = (person == null) ? new SimplePerson() : person.clone();
		childrenId = new ArrayList<Integer>(thePerson.getChildrenId());

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
		JLabel firstNameLabel = new JLabel("Pr�nom ");

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
		maleButton.setSelected(true);

		// Birthdate
		JLabel birthDateLabel = new JLabel("N�(e) le ");

		DateFormat formatBirth = new SimpleDateFormat("dd/MM/yyyy");
		DateFormatter db = new DateFormatter(formatBirth);
		final JFormattedTextField dateFieldBirth = new JFormattedTextField(db);
		dateFieldBirth.setPreferredSize(new Dimension(20, 20));
		dateFieldBirth.setValue(new Date());

		// enfants
		JLabel childLabel = new JLabel("Enfant(s) ");
		listChildModel = new DefaultListModel<String>();
		listChild = new JList<String>(listChildModel);
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

		g.gridy = 4;
		g.gridx = 0;
		g.weightx = 0;
		g.gridwidth = 1;
		lgbPanel.add(childLabel, g);

		g.gridy = 4;
		g.gridx = 2;
		g.weightx = 0;
		g.gridwidth = 6;
		lgbPanel.add(scrollpane, g);

		g.gridy = 4;
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

		JLabel fatherLabel = new JLabel("P�re");
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

		JLabel motherLabel = new JLabel("M�re");
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
				thePerson.setName(nameField.getText());
				thePerson.setFirstname(firstNameField.getText());

				if (femaleButton.isSelected()) {
					thePerson.setGender(Gender.FEMALE);
				} else {
					thePerson.setGender(Gender.MALE);
				}

				thePerson.setBirthdate(dateFieldBirth.getText());

				thePerson.setChildrenId(childrenId);

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
	 * Sert � initialiser (et rafraichir) les champs en fonction de <i>thePerson</i>, par exemple si
	 * c'est une personne � �diter.
	 */
	private void initFields() {
		CsvPersonDao dao = CsvPersonDao.getInstance();
		SimplePerson temp = null;

		for (int i = 0; i < childrenId.size(); i++) {
			try {
				temp = CsvPersonDao.getInstance().getPerson(childrenId.get(i));
			} catch (PersonIdException e) {
				temp = null;
				e.printStackTrace();
			}
			if (temp != null) {
				if (temp.getFirstname().isEmpty()) {
					listChildModel.addElement(temp.getName());
				} else {
					listChildModel.addElement(temp.getFirstname() + " " + temp.getName());
				}
			}
		}

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
			addChild();
			break;
		case DEL_CHILD:
			if (!listChild.isSelectionEmpty()) {
				childrenId.remove(listChild.getSelectedIndex());
				listChildModel.remove(listChild.getSelectedIndex());
			}
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
		int id = Tableau.showPersonByGenderSelection("S�lectionner le p�re", Gender.MALE);

		if (id > 0) {
			thePerson.setFatherId(id);
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
				thePerson.setFatherId(0);
			}
		}
	}

	private void addMother() {
		int id = Tableau.showPersonByGenderSelection("S�lectionner la m�re", Gender.FEMALE);

		if (id > 0) {
			thePerson.setMotherId(id);
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
				thePerson.setMotherId(0);
			}
		}
	}

	private void addChild() {
		int id = Tableau.showPersonSelection("S�lectionner un enfant");

		if (id > 0) {
			childrenId.add(id);
			thePerson.addChildId(id);
			SimplePerson temp = null;
			try {
				temp = CsvPersonDao.getInstance().getPerson(
						(thePerson.getChildrenId()).get(thePerson.getChildrenId().size() - 1));
			} catch (PersonIdException e) {
				e.printStackTrace();
				temp = null;
			}

			if (temp != null) {

				thePerson.getChildrenId().remove((thePerson.getChildrenId().size()) - 1);
				if ((thePerson.getId() != temp.getId())
						&& (thePerson.getFatherId() != temp.getId())
						&& (thePerson.getMotherId() != temp.getId())) {

					if (thePerson.getChildrenId().contains(temp.getId()) == false) {

						thePerson.addChildId(temp.getId());

						if (temp.getFirstname().isEmpty()) {
							listChildModel.addElement(temp.getName());
						} else {
							listChildModel.addElement(temp.getFirstname() + " " + temp.getName());
						}
					} else {
						childrenId.remove((childrenId.size()) - 1);
					}
				} else {
					listChildModel.addElement(null);
					childrenId.remove((childrenId.size()) - 1);
				}

			}
		}
	}
}
