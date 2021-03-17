package Partie;

import java.util.ArrayList;
import java.util.Map;

import Equipements.Batterie;
import Equipements.Equipement;
import Equipements.Laser;

public class Robot {
	private int baseX;
	private int baseY;
	private int posX;
	private int posY;
	private Batterie batterie_actuelle;
	private Laser laser_actuel;
	private ArrayList<Equipement> equipements_disponibles;
	private Map<String, Number> configuration;
	
	/**
	 * Constructeur non paramatré de l'obejt Robot.
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
	}
	
}
