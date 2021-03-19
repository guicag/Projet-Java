package Partie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import equipements.Batterie;
import equipements.Equipement;
import equipements.Laser;
import equipements.Minerai;

public class Robot {
	private int baseX;
	private int baseY;
	private int posX;
	private int posY;
	private int charge;
	private Batterie batterieActuelle;
	private Laser laserActuel;
	private ArrayList<Equipement> equipementsDisponibles;
	private Map<String, Number> configuration;
	private ArrayList<Direction> listDeplacementsPourBase;
	private ArrayList<Minerai> pocheDeMinerais;
	private Direction direction;
	private int score;
	private static final String TEMPS_NASA_RESTANT = "temps_avant_que_nasa_repere";
	private static final String TEMPS_INSTALLATION = "temps_installation";
	
	/**
	 * Constructeur non paramatré de l'objet Robot.
	 */
	public Robot() {
		super();
	}
	
	public Robot(Map<String, Number> configuration, List<Equipement> equipementsDisponibles, Batterie batterieDef, Laser laserDef, int posX, int posY) {
		this.configuration = configuration;
		this.equipementsDisponibles = (ArrayList<Equipement>) equipementsDisponibles;
		this.batterieActuelle = batterieDef;
		this.laserActuel = laserDef;
		this.posX = posX;
		this.posY = posY;
		this.baseX = posX;
		this.baseY = posY;
		this.direction = Direction.NORD;
		this.score = 0;
		this.charge = 0;
	}
	
	
	/**
	 * Permet de déplacer le robot dans une direction.
	 * 
	 * @param dir Enum direction indiquant la direction dans laquelle avancer
	 * 
	 * @return actions7
	 * @throws Exception 
	 */
	public String avancer(Direction dir, Carte carte){
		String actions = "";
		if(this.direction!=dir) {
			// Décrémentation du temps de rotation et de la batterie
			this.batterieActuelle.setPuissanceActuelle(batterieActuelle.getPuissanceActuelle() - (Double) configuration.get("cout_rotation"));
			this.configuration.put(TEMPS_NASA_RESTANT, (Double) configuration.get(TEMPS_NASA_RESTANT) - (Double) configuration.get("temps_rotation"));
			this.direction = dir;
			actions += "TOURNER "+dir.name()+", AVANCER,";
		} else {
			actions += "AVANCER,";
		}
		// Avance dans la "dir" souhaitée
		switch(dir) {
			case NORD :
				this.posX--;
				if(carte.getMatriceMinerais()[posX][posY] != null) miner(carte.getMatriceMinerais()[posX][posY], dir);
				break;
			case SUD :
				this.posX++;
				if(carte.getMatriceMinerais()[posX][posY] != null) miner(carte.getMatriceMinerais()[posX][posY], dir);
				break;
			case EST :
				this.posY++;
				if(carte.getMatriceMinerais()[posX][posY] != null) miner(carte.getMatriceMinerais()[posX][posY], dir);
				break;
			case OUEST :
				this.posY--;
				if(carte.getMatriceMinerais()[posX][posY] != null) miner(carte.getMatriceMinerais()[posX][posY], dir);
				break;
		}
		return actions;
	}
	
	/**
	 * Permet de tester si le robot est capable de porter ce minerai.
	 * 
	 * @param minerai
	 * @return
	 */
	public boolean testerMiner(Minerai minerai) {
		boolean mineraiOk = true;
		if(charge + minerai.getPoids() > (Integer) configuration.get("charge_maximale")) {
			mineraiOk = false;
		}
		return mineraiOk;
	}
	
	/**
	 *  Permet de miner le minerai à l'endroit ou se trouve le robot.
	 *  
	 * @param minerai Minerai à miner.
	 * 
	 * @throws Exception Lorsque vous ne pouvez pas porter le minerai. (charge maximale atteinte)
	 */
	public void miner(Minerai minerai, Direction dir){
		// Récupération des caractéristiques du minerai
		int poidsMinerai = minerai.getPoids();
		int dureteMinerai = minerai.getDurete();
		int valeur = minerai.getValeur();
		double puiLaser = this.laserActuel.getPuissanceActuelle();
		// Calcul du temps de minage et décrémentation du temps restant.
		double tempsMinage =  dureteMinerai*100/puiLaser;
		double emoussageLaser = tempsMinage * (Double) configuration.get("emoussage_laser"); 	// Emoussage du laser
		this.laserActuel.setPuissanceActuelle(puiLaser - emoussageLaser);
		// Utilisation de la batterie
		batterieActuelle.setPuissanceActuelle(batterieActuelle.getPuissanceActuelle() - (Double) configuration.get("cout_minage"));
		// Incrémentation charge max
		charge += poidsMinerai;
		score += valeur;
		//Décrémentation du temps restant
		this.configuration.put(TEMPS_NASA_RESTANT, (Double) configuration.get(TEMPS_NASA_RESTANT) - tempsMinage);
	}
	
	
	
	/**
	 * Permet de décharger le robot. N'est appellée uniquement si le robot est bien à la base.
	 * 
	 */
	public void decharger() {
		if (baseX == posX && baseY == posY) { // Vérifie que le robot soit bien à la base
			this.charge = 0;
			for(Minerai minerai : pocheDeMinerais) {
				score += minerai.getValeur();
			}
		}
	}
	
	/**
	 * Permet de s'équiper d'un laser ou d'une batterie.
	 */
	public void equiper(Equipement equip) {
		if(equip.getClass()==laserActuel.getClass()) {
			this.laserActuel = (Laser) equip;
		} else {
			this.batterieActuelle = (Batterie) equip;
		}
		double prix = equip.getCout();
		this.score -= prix;
		this.configuration.put(TEMPS_NASA_RESTANT, (Double) configuration.get(TEMPS_NASA_RESTANT) - (Double) configuration.get(TEMPS_INSTALLATION));
	}
	
	
	/**
	 * Permet de s'équiper d'un laser ou d'une batterie.
	 */
	public String rentrerBase() {
		
		// tant que coord diff de base 
			// apple a avan
		return null;
	}
	
	public Equipement getBestLaser() {
		for(Equipement equip : equipementsDisponibles) {
			if(equip.getClass()==laserActuel.getClass()) return equip;
		}
		return null;
	}
	
	public Equipement getBestBatterie() {
		for(Equipement equip : equipementsDisponibles) {
			if(equip.getClass()==batterieActuelle.getClass()) return equip;
		}
		return null;
	}

	public int getBaseX() {
		return baseX;
	}

	public void setBaseX(int baseX) {
		this.baseX = baseX;
	}

	public int getBaseY() {
		return baseY;
	}

	public void setBaseY(int baseY) {
		this.baseY = baseY;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public Batterie getBatterieActuelle() {
		return batterieActuelle;
	}

	public void setBatterieActuelle(Batterie batterieActuelle) {
		this.batterieActuelle = batterieActuelle;
	}

	public Laser getLaserActuel() {
		return laserActuel;
	}

	public void setLaserActuel(Laser laserActuel) {
		this.laserActuel = laserActuel;
	}

	public List<Equipement> getEquipementsDisponibles() {
		return equipementsDisponibles;
	}

	public void setEquipementsDisponibles(List<Equipement> equipementsDisponibles) {
		this.equipementsDisponibles = (ArrayList<Equipement>) equipementsDisponibles;
	}

	public Map<String, Number> getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Map<String, Number> configuration) {
		this.configuration = configuration;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public int getCharge() {
		return charge;
	}

	public void setCharge(int charge) {
		this.charge = charge;
	}

	public Number getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
