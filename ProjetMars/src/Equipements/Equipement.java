package Equipements;

public class Equipement implements Comparable<Object>{
	private String nom;
	protected int cout;
	protected double puissance_initiale;
	protected double puissance_actuelle;
	protected double ratio;
	
	public Equipement(String n, int c, double pi) {
		this.setNom(n);
		this.cout = c;
		this.puissance_initiale = pi;
		this.puissance_actuelle = pi;		
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

	public int getCout() {
		return cout;
	}

	public void setCout(int cout) {
		this.cout = cout;
	}

	public double getPuissance_initiale() {
		return puissance_initiale;
	}

	public void setPuissance_initiale(double puissance_initiale) {
		this.puissance_initiale = puissance_initiale;
	}

	public double getPuissance_actuelle() {
		return puissance_actuelle;
	}

	public void setPuissance_actuelle(double puissance_actuelle) {
		this.puissance_actuelle = puissance_actuelle;
	}

	public double getRatio() {
		return ratio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}
}
