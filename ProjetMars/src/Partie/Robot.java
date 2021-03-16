package Partie;

import java.util.ArrayList;
import java.util.Map;

import Equipements.Batterie;
import Equipements.Equipement;
import Equipements.Laser;

public class Robot {
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
	
	public Robot(Map<String, Number> configuration, ArrayList<Equipement> equipements_disponibles) {
		this.configuration = configuration;
		this.equipements_disponibles = equipements_disponibles;
	}
	
}
