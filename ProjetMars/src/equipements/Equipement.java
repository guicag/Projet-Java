package equipements;

/**
 * Objet Equipement, correspond au mat?riel que le robot peut ?quiper.
 * Impl?ment linterface Comparable afin de pouvoir les comparer en fonction de leur ratio.
 * @author Cl?ment
 */
public class Equipement implements Comparable<Equipement>{
	protected String nom;
	protected int cout;
	protected double puissanceInitiale;
	protected double puissanceActuelle;
	protected double ratio; // Le ratio correspond au rapport de la puissanceInitiale sur le co?t de l'?quipement
	
	/**
	 * Constructeur param?tr? d'un Equipement.
	 * @param n Nom de l'?quipement
	 * @param c Co?t de l'?quipement
	 * @param pi Puissance de l'?quipement
	 */
	public Equipement(String n, int c, double pi) {
		this.setNom(n);
		this.cout = c;
		this.puissanceInitiale = pi;
		this.puissanceActuelle = pi;		
		if(pi!=0) this.ratio = (double) c/ pi;
	}
	
	/**
	 * Constructeur non param?tr? de l'objet 
	 */
	public Equipement() {
		super();
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	@Override
	public int compareTo(Equipement o) {
		Equipement equip = o;
		int res = 0;
		if(equip.ratio<ratio) res = -1;
		if(equip.ratio>ratio) res = 1;
		return res;
	}
	
	public boolean equalsTo(Object o) {
		Equipement equip = (Equipement) o;
		boolean val = false;
		if(equip.ratio>ratio) val = true;
		return val;
	}

	public int getCout() {
		return cout;
	}

	public void setCout(int cout) {
		this.cout = cout;
	}

	public double getPuissanceInitiale() {
		return puissanceInitiale;
	}

	public void setPuissanceInitiale(double puissanceInitiale) {
		this.puissanceInitiale = puissanceInitiale;
	}

	public double getPuissanceActuelle() {
		return puissanceActuelle;
	}

	public void setPuissanceActuelle(double puissanceActuelle) {
		this.puissanceActuelle = puissanceActuelle;
	}

	public double getRatio() {
		return ratio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}
}
