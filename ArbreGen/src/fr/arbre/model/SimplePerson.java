package fr.arbre.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Classe qui repr�sente une personne de la famille.
 */
@SuppressWarnings("serial")
public class SimplePerson implements Person {
	/**
	 * Les identifiants sont attribu�s � partir de 1 (personne de qui l'arbre va �tre produit), puis
	 * incr�ment� � la cr�ation de chaque personne
	 */
	private static int currentId = 1;

	// ------------------------------------------------------------------------

	private int id;
	private String name;
	private String firstname;
	private String birthdate;
	private Gender gender;
	private int motherId;
	private int fatherId;
	private String picname;
	private List<Integer> childrenId;

	public SimplePerson() {
		this("", "", "", Gender.MALE, 0, 0, "", new ArrayList<Integer>());
	}

	public SimplePerson(String name, String firstname, String birthdate, Gender gender,
			int motherId, int fatherId, String picname, List<Integer> childrenId) {
		this.name = name;
		this.firstname = firstname;
		this.birthdate = birthdate;
		this.gender = gender;
		this.motherId = motherId;
		this.fatherId = fatherId;
		this.picname = picname;
		this.childrenId = childrenId;

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
		return childrenId;
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
	public void setPicname(String picname) {
		this.picname = picname;
	}

	@Override
	public void setChildrenId(List<Integer> childrenId) {
		this.childrenId = childrenId;
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
	public void addChildId(int childId) {
		childrenId.add(childId);
	}

	@Override
	public void eraseChildId(int childId) {
		childrenId.remove((Object) childId);
	}

	@Override
	public String toString() {
		return "SimplePerson[ id:" + id + ", nom:" + name + ", pr�nom:" + firstname + ", genre:"
				+ gender + ", id p�re:" + fatherId + ", id m�re:" + motherId + ", n�(e) le:"
				+ birthdate + ", fichier:" + picname + " ]";
	}

	@Override
	public SimplePerson clone() {
		SimplePerson copy = new SimplePerson();
		copy.setId(id);
		copy.setName(name);
		copy.setFirstname(firstname);
		copy.setGender(gender);
		copy.setFatherId(fatherId);
		copy.setMotherId(motherId);
		copy.setBirthdate(birthdate);
		copy.setPicname(picname);

		return copy;
	}
}
