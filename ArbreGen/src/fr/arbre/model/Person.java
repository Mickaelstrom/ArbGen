package fr.arbre.model;

import java.io.Serializable;
import java.util.List;

public interface Person extends Serializable {

	// Getters ----------------------------------------------------------------
	String getName();

	String getFirstname();

	String getBirthdate();

	Gender getGender();

	int getId();

	int getMotherId();

	int getFatherId();

	List<Integer> getChildrenId();

	String getPicname();
	
	PersonFrame getFrame();

	// Setters ----------------------------------------------------------------
	void setName(String name);

	void setFirstname(String firstname);

	void setBirthdate(String birthdate);

	void setGender(Gender gender);

	void setId(int id);

	void setMotherId(int mid);

	void setFatherId(int fid);

	void setChildrenId(List<Integer> childrenId);

	void setPicname(String picname);
	
	void setFrame(PersonFrame frame);

	void addChildId(int childId);
	
	void eraseChildId(int childId);
}
