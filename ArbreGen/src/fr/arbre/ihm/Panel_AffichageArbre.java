package fr.arbre.ihm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;

@SuppressWarnings("serial")
public class Panel_AffichageArbre extends JPanel {
	private TreeDrawPanel	tree;

	public Panel_AffichageArbre() {

		// Définition du délai avant apparition de l'info-bulle sur les boutons
		int delai = 500;
		ToolTipManager.sharedInstance().setInitialDelay(delai);

		tree = new TreeDrawPanel();

		tree.setPreferredSize(new Dimension(910, 760));

		JPanel menu = new JPanel();

		JPanel affichageArbrePanel = new JPanel(new GridLayout(4, 1));

		menu.add(affichageArbrePanel);

		// Définition des boutons
		JButton newMember = new JButton(new ImageIcon(
				"../ArbreGen/resources/Icons/ajouter_personne-32.png"));
		newMember.setToolTipText("Ajouter un membre");

		JButton displayMemberTable = new JButton(new ImageIcon(
				"../ArbreGen/resources/Icons/table-48.png"));
		displayMemberTable.setToolTipText("Afficher la table des membres");

		JButton saveImg = new JButton(new ImageIcon(
				"../ArbreGen/resources/Icons/enregistrer_image-32.png"));
		saveImg.setToolTipText("Sauvegarder l'image");

		JButton veriffErrors = new JButton(new ImageIcon(
				"../ArbreGen/resources/Icons/valider-32.png"));
		veriffErrors.setToolTipText("Vérifier les erreurs");

		// Ajout des boutons au pannel

		affichageArbrePanel.add(newMember, BorderLayout.WEST);
		affichageArbrePanel.add(displayMemberTable, BorderLayout.WEST);
		affichageArbrePanel.add(saveImg, BorderLayout.WEST);
		affichageArbrePanel.add(veriffErrors, BorderLayout.WEST);

		this.add(tree, BorderLayout.CENTER);
		this.add(menu, BorderLayout.WEST);

	}

}
