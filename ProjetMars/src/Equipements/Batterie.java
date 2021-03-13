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

}
