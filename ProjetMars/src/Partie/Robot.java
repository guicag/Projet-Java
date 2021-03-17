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
	private Batterie batterie_actuelle;
	private Laser laser_actuel;
	private ArrayList<Equipement> equipements_disponibles;
	private Map<String, Number> configuration;
	private Direction direction;
	private Number argent;
	
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
		this.argent = 0;
	}
	
	
	/**
	 * Permet de déplacer le robot dans une direction.
	 * 
	 * @param dir Enum direction indiquant la direction dans laquelle avancer
	 * 
	 * @return actions7
	 */
	public String avancer(Direction dir, Carte carte){
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
	
	public void minage(Minerai minerai) {
		int poidsMinerai = minerai.getPoids();
		int dureteMinerai = minerai.getDurete();
		int valeur = minerai.getValeur();
		double pui_batterie = this.batterie_actuelle.getPuissance_actuelle();
		double pui_laser = this.laser_actuel.getPuissance_actuelle();
		
		this.configuration.put("limite_emoussage", (Double) configuration.get("limite_emoussage") - (Double) configuration.get("emoussage_laser"));
		double temps_minage =  dureteMinerai*100/pui_laser;
		double emoussage_laser = temps_minage * (Double) configuration.get("emoussage_laser");
		
		//incrémùentation charge mlax
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
}
