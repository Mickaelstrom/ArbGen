package fr.arbre.dao.csv;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import fr.arbre.model.Gender;
import fr.arbre.model.SimplePerson;

public class CsvPersonDao {

	private static CsvPersonDao instance = new CsvPersonDao();
	protected File file;
	protected List<SimplePerson> persons;
	protected String[][] tab;

	private CsvPersonDao() {
	}

	public static CsvPersonDao getInstance() {
		return instance;
	}

	public String[][] getTable() {
		return tab;
	}

	public void load(String filename) {
		Csv2Array data = new Csv2Array(filename);
		tab = data.toArray();
		persons = new ArrayList<SimplePerson>();

		for (int row = 1, size = tab.length; row < size; row++) {
			SimplePerson person = new SimplePerson();

			/**
			 * index 0 : id 1 : nom 2 : prénom 3 : genre 4 : id du père 5 : id de la mère 6 : date
			 * de naissance 7 : nom du fichier de la photo
			 */
			person.setId(Integer.parseInt(tab[row][0]));
			person.setFirstname(tab[row][1]);
			person.setName(tab[row][2]);

			if ("M".equalsIgnoreCase(tab[row][3]))
				person.setGender(Gender.MALE);
			else
				person.setGender(Gender.FEMALE);

			if (tab[row].length > 4) {
				if ("".equals(tab[row][4]))
					person.setFatherId(0);
				else
					person.setFatherId(Integer.parseInt(tab[row][4]));
			}

			if (tab[row].length > 5) {
				if ("".equals(tab[row][5]))
					person.setMotherId(0);
				else
					person.setMotherId(Integer.parseInt(tab[row][5]));
			}

			if (tab[row].length > 6) {
				person.setBirthdate(tab[row][6]);
			}

			if (tab[row].length > 7) {
				person.setPicname(tab[row][7]);
			}

			persons.add(person);
		}
	}

	public void print() {
		for (SimplePerson person : persons) {
			System.out.println(person.toString());
		}
	}

	/**
	 * Récupère une personne en fonction de l'id passé
	 * 
	 * @param id
	 *            identifiant de la personne à chercher
	 * @return
	 */
	public SimplePerson getPerson(int id) throws PersonIdException {
		if (id < 1) // 1 personne la plus basse
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

}
