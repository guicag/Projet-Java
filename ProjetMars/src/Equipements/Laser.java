package Equipements;

public class Laser extends Equipement  {
	/**
	 * Constructeur param�tr� de Laser.
	 * @param n Nom du Laser
	 * @param c Co�t du laser
	 * @param pi Puissance initiale du laser
	 * 
	 */
	public Laser(String n, int c, double pi) {
		super(n, c, pi);
	}
	
	public Laser() {
		super();
	}
}
