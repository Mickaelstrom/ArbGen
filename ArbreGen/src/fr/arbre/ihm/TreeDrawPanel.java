package fr.arbre.ihm;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import fr.arbre.dao.csv.CsvPersonDao;
import fr.arbre.model.SimplePerson;

@SuppressWarnings("serial")
public class TreeDrawPanel extends JPanel {

	private Image offscreen; // image o� sera dessin� l'arbre

	public TreeDrawPanel() {
		offscreen = null;

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				offscreen = newImage();
			}
		});
	}

	private Image getImage() {
		if (offscreen == null) {
			offscreen = newImage();
		}

		return offscreen;
	}

	private Image newImage() {
		Image img = createImage(imageWidth(), imageHeight());
		Graphics g = img.getGraphics();

		g.setColor(Color.GRAY);
		g.fillRect(0, 0, imageWidth(), imageHeight());

		if (offscreen != null) {
			g.drawImage(offscreen, 0, 0, this);
			g.dispose();
		}

		repaint();

		return img;
	}

	private int imageWidth() {
		/**
		 * TODO impl�menter le calcul de la largeur max en fonction du nombre max de personne sur
		 * une meme ligne
		 */
		return 900;
	}

	private int imageHeight() {
		/**
		 * TODO impl�menter le calcul de la hauteur max en fonction du nombre max de ligne
		 */
		return 1500;
	}

	/**
	 * fonction de test...
	 */
	public void drawTest() {
		final Color pink = new Color(255, 174, 201);
		final Color blue = new Color(0, 162, 232);
		Font f = new Font(Font.DIALOG, Font.PLAIN, 48);
		BufferedImage img = null;

		int sw; // pour la taille en pixel d'un string

		Graphics g = getImage().getGraphics();
		g.setFont(f);

		// g.setColor(Color.BLACK);
		// g.drawLine(0, 0, calculateWidth(), calculateHeight());

		g.setColor(blue);
		g.fillRoundRect(500, 500, 100, 120, 0, 0);
		g.setColor(Color.BLACK);
		g.drawRoundRect(500, 500, 100, 120, 0, 0);
		g.setColor(pink);
		g.fillRoundRect(500 + 200, 500, 100, 120, 0, 0);
		g.setColor(Color.BLACK);
		g.drawRoundRect(500 + 200, 500, 100, 120, 0, 0);

		sw = g.getFontMetrics().stringWidth("Sangoku");
		g.drawString("Sangoku", 500 + (100 - sw) / 2, 500 + 120 - g.getFontMetrics().getDescent());
		sw = g.getFontMetrics().stringWidth("Chichi");
		g.drawString("Chichi", 500 + 200 + (100 - sw) / 2, 500 + 100 + g.getFontMetrics()
				.getHeight());

		try {
			img = ImageIO.read(new File("resources/Pictures/sangoku.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (img != null) {
			g.drawImage(img.getScaledInstance(90, 90, Image.SCALE_SMOOTH), 505, 505, null);
		}

		g.dispose();

		repaint();
	}

	public void drawTree() {
		CsvPersonDao dao = CsvPersonDao.getInstance();
		Graphics g = getImage().getGraphics();
		Font f = new Font(Font.DIALOG, Font.PLAIN, 20);
		g.setFont(f);

		for (Iterator<SimplePerson> it = dao.getPersons().iterator(); it.hasNext();/* */) {
			it.next().getFrame().draw(g);
		}

		g.dispose();

		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(getImage(), 0, 0, this);
	}

}
