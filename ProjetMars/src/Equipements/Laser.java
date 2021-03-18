package Equipements;

public class Laser extends Equipement  {
	/**
	 * Constructeur paramétré de Laser.
	 * @param n Nom du laser
	 * @param c Coût du laser
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
