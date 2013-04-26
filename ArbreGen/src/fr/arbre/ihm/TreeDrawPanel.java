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

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class TreeDrawPanel extends JPanel {

	private Image offscreen; // image où sera dessiné l'arbre

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
		Image img = createImage(calculateWidth(), calculateHeight());
		Graphics g = img.getGraphics();

		g.setColor(Color.WHITE); // FIXME couleur de fond ? (ou image ?)
		g.fillRect(0, 0, calculateWidth(), calculateHeight());

		if (offscreen != null) {
			g.drawImage(offscreen, 0, 0, this);
			g.dispose();
		}

		repaint();

		return img;
	}
	
	private int calculateWidth(){
		/**
		 * TODO implémenter le calcul de la largeur max en fonction du nombre max de personne sur une meme ligne
		 */
		return 1600;
	}
	
	private int calculateHeight(){
		/**
		 * TODO implémenter le calcul de la hauteur max en fonction du nombre max de ligne
		 */
		return 1024;
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

		g.setColor(Color.BLACK);
		g.drawLine(0, 0, calculateWidth(), calculateHeight());

		g.setColor(blue);
		g.fillRoundRect(500, 500, 256, 256, 16, 16);
		g.setColor(Color.BLACK);
		g.drawRoundRect(500, 500, 256, 256, 16, 16);
		g.setColor(pink);
		g.fillRoundRect(500 + 256 + 16, 500, 256, 256, 16, 16);
		g.setColor(Color.BLACK);
		g.drawRoundRect(500 + 256 + 16, 500, 256, 256, 16, 16);

		sw = g.getFontMetrics().stringWidth("Sangoku");
		g.drawString("Sangoku", 500 + (256 - sw) / 2, 600+256/2);
		sw = g.getFontMetrics().stringWidth("Chichi");
		g.drawString("Chichi", 500 + 256 + 16 + (256 - sw) / 2, 600+256/2);

		try {
			img = ImageIO.read(new File(
					"resources/Pictures/strawberry.jpg"));
		} catch (IOException e) {
		}

		if (img != null) {
			g.drawImage(img, 400, 200, null);
		}

		g.dispose();

		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(getImage(), 0, 0, this);
	}

}
