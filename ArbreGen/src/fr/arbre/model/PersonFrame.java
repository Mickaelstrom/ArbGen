package fr.arbre.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import fr.arbre.dao.csv.CsvPersonDao;
import fr.arbre.dao.csv.PersonIdException;

public class PersonFrame {

	private Point position;
	private SimplePerson refPerson;
	public final static int WIDTH = 120;
	public final static int SEMI_W = WIDTH / 2;
	public final static int HEIGHT = 150;
	public final static int SEMI_H = HEIGHT / 2;
	public final static int GAP = WIDTH * 6 / 100;

	public PersonFrame(SimplePerson refPerson, int x, int y) {
		this.refPerson = refPerson;
		position = new Point(x, y);
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(int x, int y) {
		position.x = x;
		position.y = y;
	}

	/**
	 * Pour obtenir le milieu du côté d'une personne pour son union.
	 * 
	 * @return Milieu droit si c'est un homme, milieu gauche si c'est une femme.
	 */
	public Point getCenterSide() {
		if (refPerson.getGender().isMale()) {
			// les hommes à gauche et les femmes à droite
			return new Point(position.x + WIDTH, position.y + SEMI_H);
		} else {
			return new Point(position.x, position.y + SEMI_H);
		}
	}

	/**
	 * Utilisé pour avoir le milieu-dessus d'un enfant.
	 * 
	 * @return
	 */
	public Point getMidTop() {
		return new Point(position.x + SEMI_W, position.y);
	}

	public Point getCenterLinkFromParent() {
		CsvPersonDao dao = CsvPersonDao.getInstance();

		Point p1 = null;
		try {
			p1 = dao.getPerson(refPerson.getFatherId()).getFrame().getCenterSide();
		} catch (PersonIdException e) {
			e.printStackTrace();
		}

		Point p2 = null;
		try {
			p2 = dao.getPerson(refPerson.getMotherId()).getFrame().getCenterSide();
		} catch (PersonIdException e) {
			e.printStackTrace();
		}

		int cx;
		int cy;
		if (p1 == null && p2 == null) {
			cx = -1;
			cy = -1;
		} else if (p1 == null) {
			cx = p2.x - WIDTH / 2;
			cy = p2.y + SEMI_H;
		} else if (p2 == null) {
			cx = p1.x - WIDTH / 2;
			cy = p1.y + SEMI_H;
		} else {
			cx = (p1.x + WIDTH + p2.x) / 2;
			cy = p1.y + SEMI_H;
		}

		return new Point(cx, cy);
	}

	// ---------------------------------------------------------------------------------------------

	private final static Color pink = new Color(255, 174, 201);
	private final static Color blue = new Color(0, 162, 232);

	public void draw(Graphics g) {
		final int oy = (HEIGHT - WIDTH) / 2;
		BufferedImage img = null;
		int sw; // pour la largeur en pixel d'un string;

		if (refPerson.getGender().isMale()) {
			g.setColor(blue);
		} else {
			g.setColor(pink);
		}
		g.fillRect(position.x, position.y, WIDTH, HEIGHT); // couleur de fond

		g.setColor(Color.BLACK);
		g.drawRect(position.x, position.y, WIDTH, HEIGHT); // le contour

		// dessiner le prénom
		sw = g.getFontMetrics().stringWidth(refPerson.getFirstname());
		g.drawString(refPerson.getFirstname(), position.x + SEMI_W - sw / 2, position.y + HEIGHT
				- oy - g.getFontMetrics().getDescent());

		// dessiner le nom en dessous du prénom
		sw = g.getFontMetrics().stringWidth(refPerson.getName());
		g.drawString(refPerson.getName(), position.x + SEMI_W - sw / 2, position.y + HEIGHT
				- g.getFontMetrics().getDescent());

		// dessiner l'image
		try {
			img = ImageIO.read(new File("resources/Pictures/" + refPerson.getPicname()));
		} catch (IOException e) {
			img = null;
		}
		if (img != null) {
			g.drawImage(
					img.getScaledInstance(WIDTH - GAP * 2, WIDTH - GAP * 2, Image.SCALE_SMOOTH),
					position.x + GAP, position.y + GAP, null);
		}
	}
}
