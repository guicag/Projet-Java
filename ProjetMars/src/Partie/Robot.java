package Partie;

import java.util.ArrayList;
import java.util.Map;

import Equipements.Batterie;
import Equipements.Equipement;
import Equipements.Laser;
import Equipements.Minerai;

public class Robot {
	private int baseX;
	private int baseY;
	private int posX;
	private int posY;
	private int charge;
	private Batterie batterie_actuelle;
	private Laser laser_actuel;
	private ArrayList<Equipement> equipements_disponibles;
	private Map<String, Number> configuration;
	private Direction direction;
	private Number score;
	
	/**
	 * Constructeur non paramatré de l'objet Robot.
	 */
	public Robot() {
		super();
	}
	
	public Robot(Map<String, Number> configuration, ArrayList<Equipement> equipements_disponibles, Batterie batterie_def, Laser laser_def, int posX, int posY) {
		this.configuration = configuration;
		this.equipements_disponibles = equipements_disponibles;
		this.batterie_actuelle = batterie_def;
		this.laser_actuel = laser_def;
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
	public String avancer(Direction dir, Carte carte) throws Exception{
		String actions = "";
		// Décrémentation du temps de rotation
		if(this.direction!=dir) {
			this.batterie_actuelle.setPuissance_actuelle(batterie_actuelle.getPuissance_actuelle() - (Double) configuration.get("cout_rotation"));
			this.configuration.put("temps_avant_que_nasa_repere", (Double) configuration.get("temps_avant_que_nasa_repere") - (Double) configuration.get("temps_rotation"));
			this.direction = dir;
			actions += "TOURNER "+dir.name()+", AVANCER,";
		} else {
			actions += "AVANCER,";
		}
		
		switch(dir) {
			case NORD :
				this.posX--;
				if(carte.getMatriceMinerais()[posX][posY] != null) minage(carte.getMatriceMinerais()[posX][posY]);
				break;
			case SUD :
				this.posX++;
				if(carte.getMatriceMinerais()[posX][posY] != null) minage(carte.getMatriceMinerais()[posX][posY]);
				break;
			case EST :
				this.posY++;
				if(carte.getMatriceMinerais()[posX][posY] != null) minage(carte.getMatriceMinerais()[posX][posY]);
				break;
			case OUEST :
				this.posY--;
				if(carte.getMatriceMinerais()[posX][posY] != null) minage(carte.getMatriceMinerais()[posX][posY]);
				break;
		}
		return actions;
	}
	
	public void minage(Minerai minerai) throws Exception {
		int poidsMinerai = minerai.getPoids();
		int dureteMinerai = minerai.getDurete();
		int valeur = minerai.getValeur();
		double pui_laser = this.laser_actuel.getPuissance_actuelle();
		// Calcul du temps de minage et décrémentation du temps restant.
		double temps_minage =  dureteMinerai*100/pui_laser;
		double emoussage_laser = temps_minage * (Double) configuration.get("emoussage_laser"); 	// Emoussage du laser
		if(pui_laser - emoussage_laser > 0.1) { // Ne pas casser le laser
			this.laser_actuel.setPuissance_actuelle(pui_laser - emoussage_laser);
			// Utilisation de la batterie
			batterie_actuelle.setPuissance_actuelle(batterie_actuelle.getPuissance_actuelle() - (Double) configuration.get("cout_minage"));
			
			
			// incrémentation charge max
		} else {
			if(charge + poidsMinerai > (Double) configuration.get("charge_maximale")) throw new Exception("Vous ne pourrez pas porter ce minerai...");
			else throw new Exception("Le laser n'a plus la capacité de miner cela...");
		}
		
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

	public Batterie getBatterie_actuelle() {
		return batterie_actuelle;
	}

	public void setBatterie_actuelle(Batterie batterie_actuelle) {
		this.batterie_actuelle = batterie_actuelle;
	}

	public Laser getLaser_actuel() {
		return laser_actuel;
	}

	public void setLaser_actuel(Laser laser_actuel) {
		this.laser_actuel = laser_actuel;
	}

	public ArrayList<Equipement> getEquipements_disponibles() {
		return equipements_disponibles;
	}

	public void setEquipements_disponibles(ArrayList<Equipement> equipements_disponibles) {
		this.equipements_disponibles = equipements_disponibles;
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

	public void setScore(Number score) {
		this.score = score;
	}
}
