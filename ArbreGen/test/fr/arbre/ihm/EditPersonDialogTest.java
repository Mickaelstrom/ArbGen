package fr.arbre.ihm;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import fr.arbre.dao.csv.CsvPersonDao;
import fr.arbre.dao.csv.PersonIdException;
import fr.arbre.ihm.EditPersonDialog;
import fr.arbre.model.Gender;
import fr.arbre.model.SimplePerson;

/**
 * Test de la fenêtre EditPersonDialog
 * 
 * <p>
 * -> Changement du titre de la fenêtre
 * </p>
 * <p>
 * -> Test création d'une personne
 * </p>
 * <p>
 * -> Test édition d'une personne
 * </p>
 */
public class EditPersonDialogTest {

	static SimplePerson p1, p2;

	public static void main(String[] args) {

		// Changer le style des fenêtres
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		final JFrame frame = new JFrame("EditPersonDialog Test");
		JPanel panel = new JPanel();
		final JButton b1 = new JButton("Créer personne");
		final JButton b2 = new JButton("Editer personne");
		

		CsvPersonDao dao = CsvPersonDao.getInstance();
		dao.load("resources/CSV/gen-dbz.csv");

		p1 = null;
		try {
			p2 = dao.getPerson(5);
		} catch (PersonIdException e1) {
			e1.printStackTrace();
		}

		System.out.println(p2);
		
		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Création d'une personne id1");
				p1 = EditPersonDialog.showDialog(frame);
				System.out.println(p1);
				if (p1 != null)
					System.out.println("id1= " + p1.getId());
				System.out.println("id2= " + p2.getId() + " " + p2.toString());
			}
		});

		b2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Edition de la personne id2");
				SimplePerson temp = EditPersonDialog.showDialog(frame, p2);
				System.out.println(p1);
				if (temp != null)
					p2 = temp;
				if (p1 != null)
					System.out.println("id1= " + p1.getId());
				System.out.println("id2= " + p2.getId() + " " + p2.toString());
			}
		});

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(320, 200));
		panel.setLayout(new GridLayout());

		panel.add(b1);
		panel.add(b2);
		frame.setContentPane(panel);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
