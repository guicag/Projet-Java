package equipements;

public class Case implements Comparable<Case> {

	/**
	 * Constructeur param�tr� de Case.
	 * @param caractere Caract�re correpsondant � la Case.
	 * @param nom  Nom de la case.
	 */
	public Case(char caractere, String nom) {
		super();
		this.caractere = caractere;
		this.nom = nom;
	}

	/**
	 * Constructeur non param�tr� de Case.
	 */
	public Case() {
		super();
	}

	protected char caractere;
	protected String nom;

	public char getCaractere() {
		return caractere;
	}

	public void setCaractere(char caractere) {
		this.caractere = caractere;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@Override
	public int compareTo(Case o) {
		if(this instanceof Minerai && o instanceof Minerai) {
			
			return ((Minerai) this).compareTo(o);
		}
		return 0;
	}

}