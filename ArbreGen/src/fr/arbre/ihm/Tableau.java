package fr.arbre.ihm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import fr.arbre.model.Gender;

@SuppressWarnings("serial")
public class Tableau extends JDialog {

	public Tableau(String title, boolean selectON, Gender gender) {
		// super(new JFrame(), title, true);
		setTitle(title);
		setModal(true);

		// TODO implémenter un jfilechooser pour selectionner quel csv afficher

		final Table_Csv2Array tableau = new Table_Csv2Array("", gender);
		JScrollPane scrollPane = new JScrollPane(tableau);
		scrollPane.setPreferredSize(new Dimension(700, 300));

		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);

		if (selectON) {
			id = 0;
			JButton buttonOk = new JButton("Ok");
			buttonOk.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					id = tableau.getSelectedId();
					setVisible(false);
				}
			});
			JButton buttonCancel = new JButton("Annuler");
			buttonCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					id = 0;
					setVisible(false);
				}
			});

			tableau.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					if (arg0.getButton() == MouseEvent.BUTTON1) {
						if (arg0.getClickCount() == 2) {
							id = tableau.getSelectedId();
							setVisible(false);
						}
					}
				}

				@Override
				public void mouseEntered(MouseEvent e) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}
			});

			JPanel pan = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			pan.add(buttonOk, BorderLayout.SOUTH);
			pan.add(buttonCancel, BorderLayout.SOUTH);
			add(pan, BorderLayout.SOUTH);
		}

		pack();
		setLocationRelativeTo(null);

		setVisible(true);
	}

	public static int id;

	/**
	 * Ouvre une fenêtre pour sélectionner une personne dans un tableau.
	 * 
	 * @param frame
	 *            La fenêtre appelante
	 * @param title
	 *            Titre de la nouvelle fenêtre
	 *            <p>
	 * @return L'identifiant de la personne selectionné <br/>
	 *         <b>Note :</b> Retourne 0 si annulation
	 *         </p>
	 */
	public static int showPersonSelection(String title) {
		new Tableau(title, true, null);
		return id;
	}

	public static int showPersonByGenderSelection(String title, Gender gender) {
		new Tableau(title, true, gender);
		return id;
	}
}
