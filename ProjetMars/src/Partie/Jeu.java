package Partie;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
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
		//assert configuration == null : "Vous devez analyser la configuration du robot avant de le créer.";
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
		TreeMap<Double, Direction> listVideDir = new TreeMap<Double, Direction>();
		for(Direction dir : Direction.values()) {
			switch(dir) {
				case NORD :
					if(posXRobot != 0)  { // NORD impossible
						if (!(carte.getMatriceMinerais()[posXRobot-1][posYRobot] instanceof Base) && !(carte.getMatriceMinerais()[posXRobot-1][posYRobot] instanceof Vide)) {
							listMineraiDir.put(carte.getMatriceMinerais()[posXRobot-1][posYRobot], dir);
						}
					}
					break;
				case SUD :
					if(posXRobot != carte.getRowLength()-1) { // SUD impossible
						if (!(carte.getMatriceMinerais()[posXRobot+1][posYRobot] instanceof Base) && !(carte.getMatriceMinerais()[posXRobot+1][posYRobot] instanceof Vide)) {
							listMineraiDir.put(carte.getMatriceMinerais()[posXRobot+1][posYRobot], dir);
						}
					}
					break;
				case EST :
					if(posYRobot != carte.getColumnLength()-1) { // EST impossible
						if (!(carte.getMatriceMinerais()[posXRobot][posYRobot+1] instanceof Base) && !(carte.getMatriceMinerais()[posXRobot][posYRobot+1] instanceof Vide)) {
							listMineraiDir.put(carte.getMatriceMinerais()[posXRobot][posYRobot+1], dir);
						}

					}
					break;
				case OUEST :
					if(posYRobot != 0) { // OUEST impossible
						if (!(carte.getMatriceMinerais()[posXRobot][posYRobot-1] instanceof Base) && !(carte.getMatriceMinerais()[posXRobot][posYRobot-1] instanceof Vide)) {
							listMineraiDir.put(carte.getMatriceMinerais()[posXRobot][posYRobot-1], dir);
						}
					}
					break;
			}
		}
		
		if (listMineraiDir.size() == 0) {
			if (posXRobot == robot.getBaseX() && posYRobot == robot.getBaseY()) {
				return getRandomDirection();
			}
			for(Direction dir : Direction.values()) {
				switch(dir) {
					case NORD :
						if(posXRobot != 0)  { // NORD impossible
							if (!(carte.getMatriceMinerais()[posXRobot-1][posYRobot] instanceof Base) && !(carte.getMatriceMinerais()[posXRobot-1][posYRobot] instanceof Minerai)) {
								listVideDir.put(calculDistance(posXRobot-1, posYRobot), Direction.NORD);
							}
						}
						break;
					case SUD :
						if(posXRobot != carte.getRowLength()-1) { // SUD impossible
							if (!(carte.getMatriceMinerais()[posXRobot+1][posYRobot] instanceof Base) && !(carte.getMatriceMinerais()[posXRobot+1][posYRobot] instanceof Minerai)) {
								listVideDir.put(calculDistance(posXRobot+1, posYRobot), Direction.SUD);
							}
						}
						break;
					case EST :
						if(posYRobot != carte.getColumnLength()-1) { // EST impossible
							if (!(carte.getMatriceMinerais()[posXRobot][posYRobot+1] instanceof Base) && !(carte.getMatriceMinerais()[posXRobot][posYRobot+1] instanceof Minerai)) {
								listVideDir.put(calculDistance(posXRobot, posYRobot+1), Direction.EST);
							}

						}
						break;
					case OUEST :
						if(posYRobot != 0) { // OUEST impossible
							if (!(carte.getMatriceMinerais()[posXRobot][posYRobot-1] instanceof Base) && !(carte.getMatriceMinerais()[posXRobot][posYRobot-1] instanceof Minerai)) {
								listVideDir.put(calculDistance(posXRobot, posYRobot-1), Direction.OUEST);
							}
						}
						break;
				}
			}
			return listVideDir.lastEntry().getValue();
		}
		return listMineraiDir.firstEntry().getValue();
	}
	
	/**
	 * Permet de calculer la distance d'un point depuis la base
	 * @param vide
	 * @return Double
	 */
	public double calculDistance (int posX, int posY) {
		int posXBase = robot.getBaseX();
		int posYBase = robot.getBaseY();
		double x = (posXBase -  posX) * (posXBase -  posX);
		double y = (posYBase - posY) * (posYBase - posY);
		double res = Math.sqrt(x + y);
		return res;
	}
	
	/**
	 * Permet de retourner une direction random
	 * @return Direction
	 */
	public Direction getRandomDirection() {
		int posXRobot = robot.getPosX();
		int posYRobot = robot.getPosY();
		List<Direction> listDirection = new ArrayList<Direction>();
    	listDirection.add(Direction.EST);
    	listDirection.add(Direction.OUEST);
    	listDirection.add(Direction.SUD);
    	listDirection.add(Direction.NORD);
	    if (posXRobot == 0) {
	    	listDirection.remove(Direction.NORD);
	    } else if (posXRobot == carte.getRowLength()-1) {
	    	listDirection.remove(Direction.SUD);
	    } else if (posYRobot == carte.getColumnLength()-1){
	    	listDirection.remove(Direction.EST);
		} else if (posYRobot == 0) {
			listDirection.remove(Direction.OUEST);
		}
		int min = 0, max = listDirection.size() -1;
	    int random = (int)(Math.random() * (max - min + 1) + min);
	    return listDirection.get(random);
	}
	
	/**
	 * Permet de recuperer le nombre de case Vide
	 * @return Intege
	 */
	public int getNombreCaseVide() {
		int count = 0;
		for (int i = 0; i < carte.getRowLength(); i++) {
			for (int j = 0; j < carte.getColumnLength(); j++) {
				if (carte.getMatriceMinerais()[i][j] instanceof Vide) {
					count++;
				}
			}
		}
		return count;
	}

	public int getNombreCase() {
		return carte.getColumnLength()*carte.getRowLength();
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
 			double pourcentage = (double) getNombreCaseVide()/ (double)getNombreCase() * 100;
 			if (pourcentage < 60) {
 				Direction direction = choisirDirection();
 				double coutBase = robot.getCoutRetourBase();
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
 				System.out.println(robot.getConfiguration().get("temps_avant_que_nasa_repere"));
 				System.out.println("PosX : "+robot.getPosX()+" PosY : "+robot.getPosY());
 				System.out.println(robot.getScore());
 				System.out.println(robot.getBatterieActuelle().getPuissanceActuelle());
 				System.out.println(robot.getLaserActuel().getPuissanceActuelle());
 				carte.afficherCarte();
 			} else {
 				break;
 			}	
		}
		
		/*for (int i = 0; i < 4; i++) {
			robot.avancer(choisirDirection(), carte);
		}
		System.out.println(robot.getCoutRetourBase());*/

		
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
		double pourcentageLaser = robot.getLaserActuel().getPuissanceActuelle()/robot.getLaserActuel().getPuissanceInitiale()*100;
		robot.rentrerBase(carte);
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
				if (posXRobot != 0) {
					if (!(carte.getMatriceMinerais()[robot.getPosX()-1][robot.getPosY()] instanceof Vide) && !(carte.getMatriceMinerais()[robot.getPosX()-1][robot.getPosY()] instanceof Base)) {
						res = robot.testerMiner((Minerai) carte.getMatriceMinerais()[robot.getPosX()-1][robot.getPosY()]);
					} else {
						res = true;
					}
				} 
				break;
			case SUD :
				if (posXRobot != carte.getRowLength()+1) {
					if (!(carte.getMatriceMinerais()[robot.getPosX()+1][robot.getPosY()] instanceof Vide) && !(carte.getMatriceMinerais()[robot.getPosX()+1][robot.getPosY()] instanceof Base)) {
						res = robot.testerMiner((Minerai) carte.getMatriceMinerais()[robot.getPosX()+1][robot.getPosY()]);
					} else {
						res = true;
					}
				} 
				break;
			case EST :
				if (posYRobot != carte.getColumnLength()+1) { 
					if (!(carte.getMatriceMinerais()[robot.getPosX()][robot.getPosY()+1] instanceof Vide) && !(carte.getMatriceMinerais()[robot.getPosX()][robot.getPosY()+1] instanceof Base)) {
						res = robot.testerMiner((Minerai) carte.getMatriceMinerais()[robot.getPosX()][robot.getPosY()+1]);
					} else {
						res = true;
					}
				} 
				break;
			case OUEST :
				if (posYRobot != 0) {
					if (!(carte.getMatriceMinerais()[robot.getPosX()][robot.getPosY()-1] instanceof Vide) && !(carte.getMatriceMinerais()[robot.getPosX()][robot.getPosY()-1] instanceof Base)) {
						res = robot.testerMiner((Minerai) carte.getMatriceMinerais()[robot.getPosX()][robot.getPosY()-1]);
					} else {
						res = true;
					}
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
