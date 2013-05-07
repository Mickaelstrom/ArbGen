package fr.arbre.ihm;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class Fenetre extends JFrame {

	private JMenuBar	menuBar		= new JMenuBar();
	private JMenu		File		= new JMenu("File");
	private JMenuItem	Exit		= new JMenuItem("Exit");
	private Exit_Action	exit_action	= new Exit_Action();

	public Fenetre() {

		this.setTitle("Accueil");

		Panel_Accueil panel = new Panel_Accueil();

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(400, 300));
		// this.setSize(new Dimension(1000, 800));

		Exit.addActionListener(exit_action);

		this.menuBar.add(File);

		this.setJMenuBar(menuBar);
		this.initMenu();

		panel.setAlignmentX(RIGHT_ALIGNMENT);
		this.add(panel);

		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private void initMenu() {
		Exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_MASK));
		this.File.add(Exit);
	}

	class Exit_Action implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}

	}

}
