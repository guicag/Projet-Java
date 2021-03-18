package Partie;
import java.util.ArrayList;
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
	public Jeu() {
		//Analyse des fichiers et cr�ation de la carte et du robot
		//Analyse des minerais
		ArrayList<Minerai> listMinerai = FileParser.lectureDescriptifMesures();
		//Analyse de la carte
		assert listMinerai != null : "Vous devez analyser les minerais qui composent la carte avant d'analyser celle-ci.";
		this.carte = new Carte(FileParser.lectureCarte(listMinerai));
		
		System.out.println(carte.toString());
		
		//Analyse du mat�riel disponible
		ArrayList<Equipement> listEquipement = FileParser.lectureEquipementsDisponibles();
		Map<String, Number> configuration = FileParser.lectureConfigurationRobot();
		assert listEquipement != null : "Vous devez analyser les equipements dont le robot dispose avant de le cr�er.";
		assert configuration != null : "Vous devez analyser la configuration du robot avant de le cr�er.";
		Batterie batterie_def = new Batterie();
		Laser laser_def = new Laser();
		for(Equipement equip : listEquipement) {
			if(equip.getNom().contains("defaut")) {
				if(equip.getNom().contains("batterie")) {
					batterie_def = (Batterie) equip;
				} else if(equip.getNom().contains("laser")) {
					laser_def = (Laser) equip;
				}
			}
		} 
		int[] position = carte.getBase();
		this.robot = new Robot(configuration, listEquipement, batterie_def, laser_def, position[0], position[1]);
		this.parcoursRobot = new ArrayList<String>();
		System.out.println("Jeu initialis� '-_-'");
	}
	
	/**
	 * Fonction permettant de choisir une direction � suivre lors du prochain d�placement, et de toruner le robot dans celle-ci.
	 * 
	 * @return Un enum Direction indisuant la prhcaine direction � suivre.
	 */
	public Direction choisirDirection() {
		int posXRobot = robot.getPosX();
		int posYRobot = robot.getPosY();
		
		if(posXRobot - 1 < 0 || posXRobot + 1 > carte.getRowLength()-1) {
			
		}
		
		
		/*if(pos)
		Minerai minAuDessus = carte.getMatriceMinerais()[--posXRobot][posYRobot];*/
		
		return null;
	}
	
	public void jouer() {
		//Premi�re phase 
		//		Strat�gie : Miner les plus rentable jusqu'� avoir le meilleur laser et la meilleure batterie.
		while(robot.getBatterie_actuelle().equals(robot.getBestBatterie()) && robot.getLaser_actuel().equals(robot.getBestLaser())) {
			
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

	public ArrayList<String> getParcoursRobot() {
		return parcoursRobot;
	}

	public void setParcoursRobot(ArrayList<String> parcoursRobot) {
		this.parcoursRobot = parcoursRobot;
	}

}
