package fr.arbre.model;

import java.io.Serializable;
import java.util.List;

public interface Person extends Serializable {

	// Getters --------------------------------------------
	String getName();

	String getFirstname();

	String getBirthdate();

	String getDeathdate();

	Gender getGender();

	int getId();

	int getMotherId();

	int getFatherId();

	List<Integer> getChildrenId();

	// Setters --------------------------------------------
	void setName(String name);

	void setFirstname(String firstname);

	void setBirthdate(String birthdate);

	void setDeathdate(String deathdate);

	void setGender(Gender gender);

	void setId(int id);

	void setMotherId(int mid);

	void setFatherId(int fid);

	void setChildrenId(List<Integer> childrenId);
}
