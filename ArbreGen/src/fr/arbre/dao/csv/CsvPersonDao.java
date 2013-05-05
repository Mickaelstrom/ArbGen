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

	private CsvPersonDao() {

	}

	public static CsvPersonDao getInstance() {
		return instance;
	}

	public void load(String filename) {
		Csv2Array data = new Csv2Array(filename);
		String[][] tab = data.toArray();
		persons = new ArrayList<SimplePerson>();

		for (int row = 1, size = tab.length; row < size; row++) {
			SimplePerson person = new SimplePerson();

			// TODO position des colonnes ??? 
			// -> pris par rapport au "resources/CSV/gen-04.csv"
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

			persons.add(person);
		}
	}
	
	public void print(){
		for(SimplePerson person : persons){
			System.out.println(person.toString());
		}
	}
	
	/**
	 * R�cup�re une personne en fonction de l'id pass�
	 * @param id identifiant de la personne � chercher
	 * @return
	 */
	public SimplePerson getPerson(int id) throws PersonIdException {
		if(id < 1) // 1 personne la plus basse
			throw new PersonIdException("Valeur de l'index de la personne incorrect");
		
		SimplePerson returnPerson = null;

		for(SimplePerson person : persons){
			if(person.getId() == id)
			{
				returnPerson = person;
				break;
			}
		}
	
		if(returnPerson == null)
			throw new PersonIdException("Personne non trouv�e");
		
		return returnPerson;
	}

}
