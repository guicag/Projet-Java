package Partie;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Equipements.Batterie;
import Equipements.Equipement;
import Equipements.Laser;
import Equipements.Minerai;
import GestionFichiers.FileParser;

public class Jeu {
	
	private Carte carte;
	private Robot robot;
	private ArrayList<String> parcoursRobot;
	
	/*
	 * Constructeur de l'objet Jeu. Il permet d'initialiser le jeu en analysant les fichiers de configuration.
	 */
	public Jeu() throws IOException {
		//Analyse des fichiers et création de la carte et du robot
		//Analyse des minerais
		ArrayList<Minerai> listMinerai = (ArrayList<Minerai>) FileParser.lectureDescriptifMesures();
		assert listMinerai != null : "Vous devez analyser les minerais qui composent la carte avant de l'analyser.";
		//Analyse de la carte
		this.carte = new Carte(FileParser.lectureCarte(listMinerai));
		//Analyse du matériel disponible
		ArrayList<Equipement> listEquipement = (ArrayList<Equipement>) FileParser.lectureEquipementsDisponibles();
		assert listEquipement != null : "Vous devez analyser les equipements dont le robot dispose avant de le créer.";
		//Analyse de la configuration du robot 
		Map<String, Number> configuration = FileParser.lectureConfigurationRobot();
		assert configuration != null : "Vous devez analyser la configuration du robot avant de le créer.";
		//Création du robot
		Batterie batterieDef = new Batterie();
		Laser laserDef = new Laser();
		for(Equipement equip : listEquipement) {
			if(equip.getNom().contains("defaut")) {
				if(equip.getNom().contains("batterie")) {
					batterieDef = (Batterie) equip;
				} else if(equip.getNom().contains("laser")) {
					laserDef = (Laser) equip;
				}
			}
		} 
		int[] position = carte.getBase();
		this.robot = new Robot(configuration, listEquipement, batterieDef, laserDef, position[0], position[1]);
		this.parcoursRobot = new ArrayList<>();
		System.out.println("Jeu initialisé '-_-'");
	}
	
	/**
	 * Fonction permettant de choisir une direction à suivre lors du prochain déplacement, et de toruner le robot dans celle-ci.
	 * 
	 * @return Un enum Direction indisuant la prhcaine direction à suivre.
	 */
	
	public void jouer() {
		//Première phase 
		//		Stratégie : Miner les plus rentable jusqu'à avoir le meilleur laser et la meilleure batterie.
		while(robot.getBatterieActuelle().equals(robot.getBestBatterie()) && robot.getLaserActuel().equals(robot.getBestLaser())) {
		}
	}

	public Carte getCarte() {
		return carte;
	}

	public void setCarte(Carte carte) {
		this.carte = carte;
	}

	public Robot getRobot() {
		return robot;
	}

	public void setRobot(Robot robot) {
		this.robot = robot;
	}

	public List<String> getParcoursRobot() {
		return parcoursRobot;
	}

	public void setParcoursRobot(List<String> parcoursRobot) {
		this.parcoursRobot = (ArrayList<String>) parcoursRobot;
	}

}
