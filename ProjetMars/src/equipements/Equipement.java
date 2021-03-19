package equipements;

public class Equipement implements Comparable<Object>{
	protected String nom;
	protected int cout;
	protected double puissanceInitiale;
	protected double puissanceActuelle;
	protected double ratio;
	
	/**
	 * Constructeur paramétré d'un Equipement.
	 * @param n Nom de l'équipement
	 * @param c Coût de l'équipement
	 * @param p Puissance de l'équipement
	 */
	public Equipement(String n, int c, double pi) {
		this.setNom(n);
		this.cout = c;
		this.puissanceInitiale = pi;
		this.puissanceActuelle = pi;		
		if(pi!=0) this.ratio = (double) c/ pi;
	}
	
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
	public int compareTo(Object o) {
		Equipement equip = (Equipement) o;
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
