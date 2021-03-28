package equipements;

/**
 * Objet Laser, correspond au laser que le robot utilise pour miner l'objet Minerai dans la Carte.
 * @author cleme
 */
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
	
	/**
	 * Constructeur non paramétré de l'objet Laser.
	 */
	public Laser() {
		super();
	}
	
	@Override
	public boolean equals(Object obj) {
		  if (obj == null)
		    return false;
		  boolean res = true;
		  if (this.getClass() != obj.getClass())
		    return false;
		return res;
		}

	public boolean notEquals(Object obj) {
		Laser laser = (Laser) obj;
		boolean res = true;
		if (this.getNom().equals(laser.nom) && this.getCout() == laser.cout && this.getPuissanceInitiale() == laser.puissanceInitiale && this.getRatio() == laser.ratio)
		    return false;
		return res;
	}
}
