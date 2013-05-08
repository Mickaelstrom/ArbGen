package fr.arbre.dao.csv;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
	protected String[][] table;
	protected String[] header;

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
		for (int row = 1, size = table.length; row < size; row++) {
			SimplePerson person = new SimplePerson();

			/*
			 * index 0 : id 1 : nom 2 : prénom 3 : genre 4 : id du père 5 : id de la mère 6 : date
			 * de naissance 7 : nom du fichier de la photo
			 */
			person.setId(Integer.parseInt(table[row][0]));
			person.setFirstname(table[row][1]);
			person.setName(table[row][2]);
			person.setGender("M".equalsIgnoreCase(table[row][3]) ? Gender.MALE : Gender.FEMALE);
			person.setFatherId("".equals(table[row][4]) ? 0 : Integer.parseInt(table[row][4]));
			person.setMotherId("".equals(table[row][5]) ? 0 : Integer.parseInt(table[row][5]));
			person.setBirthdate(table[row][6]);
			person.setPicname(table[row][7]);

			persons.add(person);
		}
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
		if (id < 0) // 0:personne sans information, >=1:départ des autres personnes
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

}
