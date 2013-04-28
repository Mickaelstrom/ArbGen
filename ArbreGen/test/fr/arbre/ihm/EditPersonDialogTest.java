package fr.arbre.ihm;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.arbre.model.SimplePerson;

public class EditPersonDialogTest {

	public static void main(String[] args) {
		final JFrame frame = new JFrame("EditPersonDialog Test");
		JPanel panel = new JPanel();
		final JButton b1 = new JButton("Créer personne");
		final JButton b2 = new JButton("Editer personne");

		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SimplePerson value = EditPersonDialog.showDialog(frame, b1,
						b1.getText());
				System.out.println("b1" + value.toString());
			}
		});

		b2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SimplePerson value = EditPersonDialog.showDialog(frame, b2,
						b2.getText());
				System.out.println("b2" + value.toString());
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
