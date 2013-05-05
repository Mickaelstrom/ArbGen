package fr.arbre.model;

import java.util.List;

public class SimplePerson implements Person {
	private static final long serialVersionUID = -4490492722630643380L;
	/**
	 * Les identifiants sont attribués à partir de 1 (personne de qui l'arbre va
	 * être produit), puis incrémenté à la création de chaque personne
	 */
	private static int currentId = 1;

	// ------------------------------------------------------------------------

	private String name;
	private String firstname;
	private String birthdate;
	private Gender gender;
	private int id;
	private int motherId;
	private int fatherId;
	private List<Integer> childrenId;
	private String picname;

	public SimplePerson() {
		this("", "", "", Gender.MALE, 0, 0, null, "");
	}

	public SimplePerson(String name, String firstname, String birthdate,
			Gender gender, int motherId, int fatherId,
			List<Integer> childrenId, String picname) {
		this.name = name;
		this.firstname = firstname;
		this.birthdate = birthdate;
		this.gender = gender;
		this.motherId = motherId;
		this.fatherId = fatherId;
		this.childrenId = childrenId;
		this.picname = picname;

		id = generateId();
	}

	private int generateId() {
		return currentId++;
	}

	// Getters ----------------------------------------------------------------

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
	public String getPicname() {
		return picname;
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

	// Setters ----------------------------------------------------------------

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

	@Override
	public void setPicname(String picname) {
		this.picname = picname;
	}

	// Other functions --------------------------------------------------------

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
