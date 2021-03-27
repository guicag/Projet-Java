package equipements;

public class Batterie extends Equipement {
	/**
	 * Constructeur param�tr� de Laser.
	 * @param n Nom de la batterie
	 * @param c Co�t de la batterie
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

	public boolean notEquals(Equipement bestBatterie) {
		boolean res = true;
		Batterie batterie = (Batterie) bestBatterie;
		if(nom == batterie.getNom() && cout == batterie.getCout() && puissanceInitiale == batterie.getPuissanceInitiale() && ratio == batterie.getRatio()) res = false;
		return res;
	}
}
