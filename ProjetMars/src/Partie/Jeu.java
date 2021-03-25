package Partie;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import GestionFichiers.FileParser;
import equipements.Base;
import equipements.Batterie;
import equipements.Case;
import equipements.Equipement;
import equipements.Laser;
import equipements.Minerai;
import equipements.Vide;

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
		assert configuration == null : "Vous devez analyser la configuration du robot avant de le créer.";
		//Création du robot
		Batterie batterieDef = new Batterie("batterie_defaut", 0, (Double) configuration.get("batterie_defaut"));
		Laser laserDef = new Laser("laser_defaut", 0, (Double) configuration.get("laser_defaut"));
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
	public Direction choisirDirection() {
		int posXRobot = robot.getPosX();
		int posYRobot = robot.getPosY();
		TreeMap<Case, Direction> listMineraiDir = new TreeMap<Case, Direction>();
		for(Direction dir : Direction.values()) {
			switch(dir) {
				case NORD :
					if(posXRobot != 0  && !(carte.getMatriceMinerais()[posXRobot-1][posYRobot] instanceof Base) && !(carte.getMatriceMinerais()[posXRobot-1][posYRobot] instanceof Vide)) {
						listMineraiDir.put(carte.getMatriceMinerais()[posXRobot-1][posYRobot], dir);// NORD impossible
					}
					break;
				case SUD :
					if(posXRobot != carte.getRowLength()-1 && !(carte.getMatriceMinerais()[posXRobot+1][posYRobot] instanceof Base) && !(carte.getMatriceMinerais()[posXRobot+1][posYRobot] instanceof Vide)) {
						listMineraiDir.put(carte.getMatriceMinerais()[posXRobot+1][posYRobot], dir);// SUD impossible
					}
					break;
				case EST :
					if(posYRobot != carte.getColumnLength()-1 && !(carte.getMatriceMinerais()[posXRobot][posYRobot+1] instanceof Base) && !(carte.getMatriceMinerais()[posXRobot][posYRobot+1] instanceof Vide)) {
						listMineraiDir.put(carte.getMatriceMinerais()[posXRobot][posYRobot+1], dir);// EST impossible
					}
					break;
				case OUEST :
					if(posYRobot != 0  && !(carte.getMatriceMinerais()[posXRobot][posYRobot-1] instanceof Base) && !(carte.getMatriceMinerais()[posXRobot][posYRobot-1] instanceof Vide)) {
						listMineraiDir.put(carte.getMatriceMinerais()[posXRobot][posYRobot-1], dir);// OUEST impossible
					}
					break;
			}
		}
		if (listMineraiDir.size() == 0) {
			if (posXRobot != 0) return Direction.NORD;
			if (posYRobot != carte.getColumnLength()-1) return Direction.EST;
			if (posXRobot != carte.getRowLength()-1) return Direction.SUD;
			if (posYRobot != 0) return Direction.OUEST;
		}
		return listMineraiDir.firstEntry().getValue();
	}
	
	/**
	 * Permet de déterminer si le robot est entouré de minerais ayant un ratio égal à 0;
	 * 
	 * @param listMineraiDir Liste des minerais qui entourent le robot.
	 * @return Boolean true si que des zéros autour du robot, sinon false
	 */
	public boolean testNullValues(TreeMap<Minerai, Direction> listMineraiDir) {
		boolean res = true;
		for (Entry<Minerai, Direction> entry : listMineraiDir.entrySet()) {
            if(((Case) entry.getKey()) != null) res =  false;
        }
		return res;
	}
	
	/**
	 * Fonction permettant de choisir une direction à suivre lors du prochain déplacement, et de toruner le robot dans celle-ci.
	 * 
	 * @return Un enum Direction indisuant la prhcaine direction à suivre.
	 */
	public void jouer() {
		//Première phase 
		//Stratégie : Miner les plus rentable jusqu'à avoir le meilleur laser et la meilleure batterie.

 		while ((Double) robot.getConfiguration().get("temps_avant_que_nasa_repere") > 0.0) {
			Direction direction = choisirDirection();
			if (robot.getCoutRetourBase() + (Double) robot.getConfiguration().get("cout_minage") < robot.getBatterieActuelle().getPuissanceActuelle()) {
				if (testerMinerDirection(direction) ) {
					String s = robot.avancer(direction, carte);
					parcoursRobot.add(s);
				} else {
					rentrerVider();
				}
			} else {
				rentrerChangerEquipement();
			}
			System.out.println("PosX : "+robot.getPosX()+" PosY : "+robot.getPosY());
			carte.afficherCarte();
		}
		
		/*for (int i = 0 ; i < 15; i++) {
			robot.avancer(choisirDirection(), carte);
		}
		carte.afficherCarte();
		robot.rentrerBase(carte);
		if (robot.getBaseX() == robot.getPosX() && robot.getBaseY() == robot.getPosY()) {
			System.out.println("test reussi");
		}*/
	}
	
	/**
	 * Cette fonction permet de revenir à la base quand la charge maximal
	 */
	public void rentrerVider() {
		robot.rentrerBase(carte);
		if (robot.getPosX() == robot.getBaseX() && robot.getPosY() == robot.getBaseY()) {
			robot.decharger();
		}
	}
	
	public void rentrerChangerEquipement() {
		robot.rentrerBase(carte);
		if (robot.getPosX() == robot.getBaseX() && robot.getPosY() == robot.getBaseY()) {
			robot.decharger();
			if ((Integer) robot.getScore() >= robot.getBestBatterie().getCout()) {
				robot.equiper(robot.getBestBatterie());
			} else if (robot.getBestBatterieAchetable() != null){
				robot.equiper(robot.getBestBatterieAchetable());
			}
			if ((Integer) robot.getScore() >= robot.getBestLaser().getCout()) {
				robot.equiper(robot.getBestLaser());
			} else if ( robot.getBestLaserAchetable() != null) {
				if ((Integer) robot.getScore() >= robot.getBestLaserAchetable().getCout()) robot.equiper(robot.getBestLaserAchetable());
			}
		}
	}
	
	
	/**
	 * Permet de tester si le robot peut miner le minerai dans la direction indiquée en paramètre.
	 * 
	 * @param direction Direction du minerai à tester.
	 * @return boolean un booléen indiquant si le robot peut miner le minerai.
	 */
	public boolean testerMinerDirection(Direction direction) {
		boolean res = false;
		int posXRobot = robot.getPosX();
		int posYRobot = robot.getPosY();
		switch(direction) {
			case NORD :
				if (posXRobot != 0 && !(carte.getMatriceMinerais()[robot.getPosX()-1][robot.getPosY()] instanceof Vide)) {
					res = robot.testerMiner((Minerai) carte.getMatriceMinerais()[robot.getPosX()-1][robot.getPosY()]);
				} else {
					res = true;
				}
				break;
			case SUD :
				if (posXRobot != carte.getRowLength()-1 && !(carte.getMatriceMinerais()[robot.getPosX()+1][robot.getPosY()] instanceof Vide)) {
					res = robot.testerMiner((Minerai) carte.getMatriceMinerais()[robot.getPosX()+1][robot.getPosY()]);
				} else {
					res = true;
				}
				break;
			case EST :
				if (posYRobot != carte.getColumnLength()-1 && !(carte.getMatriceMinerais()[robot.getPosX()][robot.getPosY()+1] instanceof Vide)) {
					res = robot.testerMiner((Minerai) carte.getMatriceMinerais()[robot.getPosX()][robot.getPosY()+1]);
				} else {
					res = true;
				}
				break;
			case OUEST :
				if (posYRobot != 0  && !(carte.getMatriceMinerais()[robot.getPosX()][robot.getPosY()-1] instanceof Vide)) {
					res = robot.testerMiner((Minerai) carte.getMatriceMinerais()[robot.getPosX()][robot.getPosY()-1]);
				} else {
					res = true;
				}
				break;
		}
		return res;
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
