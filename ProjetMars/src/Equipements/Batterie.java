package Equipements;

public class Batterie extends Equipement {
	private String nomBatterie;
	private int coutBatterie;
	private double puissanceBatterieInitiale;
	private double puissanceBatterieActuelle;
	private double ratioBatterie;
	
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
		if(nom == batterie.getNom() && cout == batterie.getCout() && puissanceInitiale == batterie.getPuissanceInitiale() && ratio == batterie.getRatio()) res = true;
		return res;
	}

}
