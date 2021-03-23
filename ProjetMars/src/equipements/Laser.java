package equipements;

public class Laser extends Equipement  {
	private String nomLaser;
	private int coutLaser;
	private double puissanceLaserInitiale;
	private double puissanceLaserActuelle;
	private double ratioLaser;
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
	
	public boolean equals(Object obj) {
		  if (obj == null)
		    return false;

		  if (this.getClass() != obj.getClass())
		    return false;
		return true;
		}

	public boolean notEquals(Object obj) {
		Laser laser = (Laser) obj;
		if (this.getNom() == laser.nom && this.getCout() == laser.cout && this.getPuissanceInitiale() == laser.puissanceInitiale && this.getRatio() == laser.ratio)
		    return false;
		return true;
	}
}
