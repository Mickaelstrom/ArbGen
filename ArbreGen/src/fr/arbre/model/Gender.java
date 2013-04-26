package fr.arbre.model;

/**
 * Enumération représentant un genre
 */
public enum Gender {

	// Valeurs de l'enum possible
	MALE(1), FEMALE(2);

	// Attribut
	private final int code;

	// Construteur
	Gender(final int code) {
		this.code = code;
	}

	// Getter
	public int getCode() {
		return code;
	}
	
	public static Gender typeByCode(final int code) throws IllegalArgumentException {
		for(Gender gender : values()){
			if(gender.code == code)
				return gender;
		}

		throw new IllegalArgumentException("Le code du genre est erroné");
	}

	public boolean isMale() {
		return (this == MALE);
	}
}
