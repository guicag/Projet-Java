package Equipements;

public class Batterie extends Equipement {
	private String nom;
	private int cout;
	private double puissance_initiale;
	private double puissance_actuelle;
	private double ratio;
	
	/**
	 * Constructeur paramétré de Laser.
	 * @param n Nom de la batterie
	 * @param c Coût de la batterie
	 * @param pi Puissance de la batterie
	 * 
	 */
	public Batterie(String n, int c, double pi) {
		super(n, c, pi);
	}
	
	public Batterie() {
		super();
	}

	public boolean equals(Object bestBatterie) {
		boolean res = false;
		Batterie batterie = (Batterie) bestBatterie;
		if(nom == batterie.getNom() && cout == batterie.getCout() && puissance_initiale == batterie.getPuissance_initiale() && ratio == batterie.getRatio()) res = true;
		return res;
	}

}
