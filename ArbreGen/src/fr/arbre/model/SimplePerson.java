package fr.arbre.model;

import java.util.List;

public class SimplePerson implements Person {
	private static final long serialVersionUID = -4490492722630643380L;
	private static int currentId = 1; // incrémenté à la création d'une personne
										// (0=erreur)

	// -----------------------------------------------------------------

	private String name;
	private String firstname;
	private String birthdate;
	private String deathdate;
	private Gender gender;
	private int id;
	private int motherId;
	private int fatherId;
	private List<Integer> childrenId;

	public SimplePerson(String name, String firstname, String birthdate,
			String deathdate, Gender gender, int motherId, int fatherId,
			List<Integer> childrenId) {
		this.name = name;
		this.firstname = firstname;
		this.birthdate = birthdate;
		this.deathdate = deathdate;
		this.gender = gender;
		this.motherId = motherId;
		this.fatherId = fatherId;
		this.childrenId = childrenId;

		id = generateId();
	}

	public SimplePerson() {
		this.name = "";
		this.firstname = "";
		this.birthdate = "";
		this.deathdate = "";
		this.gender = Gender.MALE;
		this.motherId = 0;
		this.fatherId = 0;
		this.childrenId = null;

		id = generateId();
	}

	private int generateId() {
		return currentId++;
	}

	// Getters --------------------------------------------

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getFirstname() {
		return firstname;
	}

	@Override
	public String getBirthdate() {
		return birthdate;
	}

	@Override
	public String getDeathdate() {
		return deathdate;
	}

	@Override
	public Gender getGender() {
		return gender;
	}

	@Override
	public int getMotherId() {
		return this.motherId;
	}

	@Override
	public int getFatherId() {
		return this.fatherId;
	}

	@Override
	public List<Integer> getChildrenId() {
		return this.childrenId;
	}

	// Setters --------------------------------------------

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@Override
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	@Override
	public void setDeathdate(String deathdate) {
		this.deathdate = deathdate;
	}

	@Override
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Override
	public void setMotherId(int mid) {
		this.motherId = mid;
	}

	@Override
	public void setFatherId(int fid) {
		this.fatherId = fid;
	}

	@Override
	public void setChildrenId(List<Integer> childrenId) {
		this.childrenId = childrenId;
	}

	// Other functions

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "SimplePerson [nom=" + name + ", prenom=" + firstname
				+ ", genre=" + gender + ", naissance le=" + birthdate + "]";
	}
}
