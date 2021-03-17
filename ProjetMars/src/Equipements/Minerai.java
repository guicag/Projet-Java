package Equipements;
/**
 * @author cleme
 *
 *
 */
public class Minerai implements Comparable<Object>{

	private char caractere;
	private String nom;
	private int valeur;
	private int durete;
	private int poids;
	private double ratio;// Le ratio correspond au rapport de la dureté sur la valeur du minerai. Différent de 0 si valeur l'est aussi.
	
	public Minerai() {
		super();
	}
	
	/**
	 * Constructeur paramétré de Minerai.
	 * @param c Symbole du minerai
	 * @param n Nom du minerai
	 * @param v Valeur du minerai
	 * @param d Dureté du minerai
	 * @param p Poids du minerai
	 */
	public Minerai(char c, String n, int v, int d, int p) {
		this.caractere = c;
		this.nom = n;
		this.setValeur(v);
		this.durete = d;
		this.poids = p;
		this.ratio = (double) v/ (double) d;
	}

	public int getValeur() {
		return valeur;
	}

	public void setValeur(int valeur) {
		this.valeur = valeur;
	}

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

	public int getDurete() {
		return durete;
	}

	public void setDurete(int durete) {
		this.durete = durete;
	}

	public int getPoids() {
		return poids;
	}

	public void setPoids(int poids) {
		this.poids = poids;
	}

	public double getRatio() {
		return ratio;
	}

	public void setRatio(int ratio) {
		this.ratio = ratio;
	}

	@Override
	public int compareTo(Object o) {
		Minerai min = (Minerai) o;
		int res = 0;
		if(min.ratio<ratio) res = -1;
		if(min.ratio>ratio) res = 1;
		return res;
	}

	
}
