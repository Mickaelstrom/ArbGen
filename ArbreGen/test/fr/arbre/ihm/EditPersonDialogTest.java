package fr.arbre.ihm;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.arbre.ihm.EditPersonDialog;
import fr.arbre.model.Gender;
import fr.arbre.model.SimplePerson;

/**
 * Test de la fenêtre EditPersonDialog
 * 
 * -> Changement du titre de la fenêtre
 * -> Test création d'une personne
 * -> Test édition d'une personne
 */
public class EditPersonDialogTest {

	static SimplePerson p1, p2;

	public static void main(String[] args) {
		final JFrame frame = new JFrame("EditPersonDialog Test");
		JPanel panel = new JPanel();
		final JButton b1 = new JButton("Créer personne");
		final JButton b2 = new JButton("Editer personne");
		p2 = new SimplePerson("nom", "prenom", "12/12/2012", "", Gender.FEMALE,
				0, 0, null);

		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				p1 = EditPersonDialog.showDialog(frame);
				System.out.println("id1= " + p1.getId());
				System.out.println("id2= " + p2.getId() + " " + p2.toString());
			}
		});

		b2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				p2 = EditPersonDialog.showDialog(frame, p2);
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
