package fr.arbre.dao.csv;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.arbre.model.Gender;
import fr.arbre.model.SimplePerson;

/**
 * Cette classe permet de créer un singleton qui contiendra une liste de personne préalablement
 * chargé par la fonction load().
 */
public class CsvPersonDao {

	private static CsvPersonDao instance = new CsvPersonDao();
	protected File file;
	protected List<SimplePerson> persons;
	private Map<Integer, Integer> mapLinksId; // clé id de la personne, valeur sa position dans la
												// liste persons
	protected String[][] table;
	protected String[] header;
	private int nbGeneration;
	private int[] nbPerGen;

	private CsvPersonDao() {
	}

	public static CsvPersonDao getInstance() {
		return instance;
	}

	/**
	 * Obtenir la table des données des personnes.
	 * 
	 * @return Un tableau à deux dimensions [id personne][id colonne] des données (pas de nom des
	 *         colonne).
	 */
	public String[][] getTable() {
		return table;
	}

	/**
	 * Cette fonction permet de charger les données dans un fichier <i>csv</i> et de le stocker dans
	 * l'instance unique de CsvPersonDao.
	 * 
	 * @param filename
	 *            Fichier où sont stockés les données des personnes de l'arbre.
	 */
	public void load(String filename) {
		Csv2Array data = new Csv2Array(filename);
		String[][] tab = data.toArray();

		header = tab[0].clone();
		table = new String[tab.length - 1][data.getMaxColumn()];
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[0].length; j++) {
				if (j < tab[i + 1].length)
					table[i][j] = tab[i + 1][j];
				else
					table[i][j] = "";
			}
		}

		persons = new ArrayList<SimplePerson>();
		mapLinksId = new HashMap<Integer, Integer>();
		for (int row = 0, size = table.length; row < size; row++) {
			SimplePerson person = new SimplePerson();

			/*
			 * index 0 : id, 1 : nom, 2 : prénom, 3 : genre, 4 : id du père, 5 : id de la mère, 6 :
			 * date de naissance, 7 : nom du fichier de la photo, 8 : liste d'id des enfants
			 */
			person.setId(Integer.parseInt(table[row][0]));
			person.setFirstname(table[row][1]);
			person.setName(table[row][2]);
			person.setGender("M".equalsIgnoreCase(table[row][3]) ? Gender.MALE : Gender.FEMALE);
			person.setFatherId("".equals(table[row][4]) ? 0 : Integer.parseInt(table[row][4]));
			person.setMotherId("".equals(table[row][5]) ? 0 : Integer.parseInt(table[row][5]));
			person.setBirthdate(table[row][6]);
			person.setPicname(table[row][7]);
			if (!table[row][8].isEmpty()) {
				String[] childrenId = table[row][8].split(" ");
				for (int i = childrenId.length - 1; i >= 0; i--) {
					person.addChildId(Integer.parseInt(childrenId[i]));
				}
			}

			persons.add(person);
			mapLinksId.put(person.getId(), row);
		}

		calculateTreeInfos();
	}

	/**
	 * Affiche les données de toutes les personnes sur la console.
	 */
	public void print() {
		for (SimplePerson person : persons) {
			System.out.println(person.toString());
		}
	}

	/**
	 * Récupère une personne en fonction de <i>id</i>.
	 * 
	 * @param id
	 *            Identifiant de la personne à chercher.
	 * @return Une SimplePerson si elle a été trouvée dans la liste sinon lance une exception.
	 * @throws PersonIdException
	 *             si l'id est incorrect ou si la personne n'a pas été trouvé.
	 */
	public SimplePerson getPerson(int id) throws PersonIdException {
		if (id < 0) // 0: personne vide, >=1: les personnes
			throw new PersonIdException("Valeur de l'index de la personne incorrect");

		SimplePerson returnPerson = null;

		for (SimplePerson person : persons) {
			if (person.getId() == id) {
				returnPerson = person;
				break;
			}
		}

		if (returnPerson == null)
			throw new PersonIdException("Personne non trouvée");

		return returnPerson;
	}

	/**
	 * Permet d'obtenir une liste de personne trié en fonction du genre.
	 * 
	 * @param filter
	 *            Gender.MALE ou Gender.FEMALE
	 * @return Une liste de SimplePerson filtré.
	 * @throws GenderException
	 *             Si genre incorrect.
	 */
	public List<SimplePerson> getTableByGender(Gender filter) throws GenderException {
		if (filter != Gender.MALE && filter != Gender.FEMALE)
			throw new GenderException("Le genre est incorrect");

		List<SimplePerson> list = new ArrayList<SimplePerson>();
		for (SimplePerson person : persons) {
			if (person.getGender() == filter) {
				list.add(person);
			}
		}

		return list;
	}

	/**
	 * Obtenir les noms des colonnes.
	 * 
	 * @return Un tableau de String.
	 */
	public String[] getHeader() {
		return header;
	}

	/**
	 * Calculer le nombre max de personne sur la meme ligne et le nombre de generation.<br/>
	 * <p>
	 * <b>Note : </b>Utile pour définir la taille de l'image du treedrawpanel.
	 * </p>
	 */
	private void calculateTreeInfos() {
		nbGeneration = 0;
		if (persons.size() == 0)
			return;

		nbGeneration = goUp(persons.get(mapLinksId.get(1)), 1)
				+ goDown(persons.get(mapLinksId.get(1)), 1) - 1;

	}

	/**
	 * Nombre de génération au dessus + actuelle
	 * 
	 * @param pers
	 *            La personne d'où il faut partir
	 * @param nbGen
	 *            Le nombre de génération actuelle
	 * @return Le nombre de génération compté vers le haut
	 */
	private int goUp(SimplePerson pers, int nbGen) {
		int height1 = nbGen;
		int height2 = nbGen;

		// récursivité powaaaa!!!
		if (pers.getFatherId() > 0) {
			height1 = goUp(persons.get(mapLinksId.get(pers.getFatherId())), nbGen + 1);
		}
		if (pers.getMotherId() > 0) {
			height2 = goUp(persons.get(mapLinksId.get(pers.getMotherId())), nbGen + 1);
		}

		return ((height1 > height2) ? height1 : height2);
	}

	/**
	 * Nombre de generation en dessous + actuelle
	 * 
	 * @param pers
	 *            La personne d'où il faut partir
	 * @param nbGen
	 *            Le nombre de génération actuelle
	 * @return Le nombre de génération compté vers le haut
	 */
	private int goDown(SimplePerson pers, int nbGen) {
		int maxDepth = nbGen;
		int curDepth = nbGen;

		for (Integer id : pers.getChildrenId()) {
			curDepth = goDown(persons.get(mapLinksId.get(id)), nbGen + 1);
			if (curDepth > maxDepth)
				maxDepth = curDepth;
		}

		return maxDepth;
	}
}
