package equipements;

/**
 * Objet Batterie dont peut s'équiper l'objet Robot, et qui lui est nécéssaire pour les instructions de ses méthodes.
 * Hérite de Equipement.
 * @author Clément
 */
public class Batterie extends Equipement {
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
	
	/**
	 * Constructeur non paramétré de l'objet Batterie.
	 */
	public Batterie() {
		super();
	}

	public boolean equals(Object bestBatterie) {
		boolean res = false;
		Batterie batterie = (Batterie) bestBatterie;
		if(nom == batterie.getNom() && cout == batterie.getCout() && puissanceInitiale == batterie.getPuissanceInitiale() && ratio == batterie.getRatio()) res = true;
		return res;
	}

	public boolean notEquals(Equipement bestBatterie) {
		boolean res = true;
		Batterie batterie = (Batterie) bestBatterie;
		if(nom == batterie.getNom() && cout == batterie.getCout() && puissanceInitiale == batterie.getPuissanceInitiale() && ratio == batterie.getRatio()) res = false;
		return res;
	}
}
