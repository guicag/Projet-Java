package partie;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import elementsCarte.Base;
import elementsCarte.Case;
import elementsCarte.Minerai;
import elementsCarte.Vide;

import java.util.TreeMap;

import equipements.Batterie;
import equipements.Equipement;
import equipements.Laser;
import gestionFichiers.FileParser;
import gestionFichiers.FileWrite;

public class Jeu {
	
	private Carte carte;
	private Robot robot;
	private ArrayList<String> parcoursRobot;
	private int nbmouvement = 0;
	
	/*
	 * Constructeur de l'objet Jeu. Il permet d'initialiser le jeu en analysant les fichiers de configuration.
	 */
	public Jeu() throws IOException {
		//Analyse des fichiers et création de la carte et du robot
		//Analyse des minerais
		ArrayList<Minerai> listMinerai = (ArrayList<Minerai>) FileParser.lectureDescriptifMesures();
		//Analyse de la carte
		this.carte = new Carte(FileParser.lectureCarte(listMinerai), listMinerai);
		//Analyse du matériel disponible
		ArrayList<Equipement> listEquipement = (ArrayList<Equipement>) FileParser.lectureEquipementsDisponibles();
		//Analyse de la configuration du robot 
		Map<String, Number> configuration = FileParser.lectureConfigurationRobot();
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
		TreeMap<Case, Direction> listMineraiDir = new TreeMap<>();
		TreeMap<Double, Direction> listVideDir = new TreeMap<>();
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
	 * @param posX Position X du robot
	 * @param posY Position Y du robot
	 * @return Double
	 */
	public double calculDistance (int posX, int posY) {
		int posXBase = robot.getBaseX();
		int posYBase = robot.getBaseY();
		int x = (posXBase -  posX) * (posXBase -  posX);
		int y = (posYBase - posY) * (posYBase - posY);
		return Math.sqrt(x + y);
	}
	
	/**
	 * Permet de retourner une direction random
	 * @return Direction Une diretion indiquant 
	 */
	public Direction getRandomDirection() {
		int posXRobot = robot.getPosX();
		int posYRobot = robot.getPosY();
		List<Direction> listDirection = new ArrayList<>();
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
		int min = 0;
		int max = listDirection.size() -1;
	    int random = (int)(Math.random() * (max - min + 1) + min);
	    return listDirection.get(random);
	}
	
	/**
	 * Permet de recuperer le nombre de case Vide
	 * @return Integer Un entier correspondant au nombre de cases Vides dont est composée la carte.
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
	
	/**
	 * Retourne le nombre de case total
	 * @return Integer Un entier correspondant au nombre de cases total sur la carte.
	 */
	public int getNombreCase() {
		return carte.getColumnLength()*carte.getRowLength();
	}
	
	/**
	 * Fonction permettant de choisir une direction à suivre lors du prochain déplacement, et de toruner le robot dans celle-ci.
	 * 
	 * @return Un enum Direction indisuant la prhcaine direction à suivre.
	 * @throws IOException Une IOEsception en cas d'exception lors de l'écriture du parcours du robot dans le fichier mission.txt
	 */
	public int jouer() throws IOException {
		//Première phase 
		//Stratégie : Miner les plus rentable jusqu'à avoir le meilleur laser et la meilleure batterie.
		int pourcentageMinage = 50;
 		while ((Double) robot.getConfiguration().get("temps_avant_que_nasa_repere") > 0.0) {
 			double pourcentage = ((double) getNombreCaseVide()/ (double)getNombreCase()) * 100;
 			if (pourcentage < pourcentageMinage) {
 				Direction direction = choisirDirection();
 				if (robot.getCoutRetourBase() + (Double) robot.getConfiguration().get("cout_minage") < robot.getBatterieActuelle().getPuissanceActuelle()) {
 					if (testerMinerDirection(direction) ) {
 						nbmouvement++;
 						String s = robot.avancer(direction, carte);
 						parcoursRobot.add(s);
 						afficherInfoRobotMinage();
 					} else {
 						rentrerVider();
 					}
 				} else {
 					rentrerChangerEquipement();
 				}
 			} else {
 				FileWrite.ecritureMission(parcoursRobot);
 				String affichage = "\\n\\n*************Partie Termine*************\nVous avez mine " + pourcentageMinage + "% de la carte \n\n*************Carte Finale*************";
 				System.out.println(affichage);
 				carte.afficherCarte();
 				return 0;
 			}
		}
 		FileWrite.ecritureMission(parcoursRobot);
		String affichage = "\\n\\n*************Partie Termine*************\nLa nasa vous a reperé \n\n*************Carte Finale*************";
		System.out.println(affichage);
		carte.afficherCarte();
		return 1;
	}
	
	/**
	 * Cette fonction permet de revenir à la base quand la charge maximal est atteinte
	 * Enregistre les mouvements du robot aussi
	 */
	public void rentrerVider() {
		List<String> tempList;
		tempList = robot.rentrerBase(carte);
		for (String e : tempList) {
			parcoursRobot.add(e);
			afficherInfoRobotRentrerCharge();
		}
		parcoursRobot.add(robot.decharger());
		afficherInfoRobotDecharger();
	}
	
	/**
	 * Cette fonction permet de revenir à la base quand la batterie est trop faible
	 * Le robot achete la meilleure batterie (meilleur ratio) s'il peut sinon il achete la batterie qui peut
	 * S'il lui reste encore assez d'argent, il achete le laser avec le meilleur ratio sinon en focntion de son argent il achete un autre laser ou pas
	 */
	public void rentrerChangerEquipement() {
		List<String> temp;
		temp = robot.rentrerBase(carte);
		for (String e : temp) {
			parcoursRobot.add(e);
			afficherInfoRobotRentrerEquipement();
		}
		//double pourcentageLaser = (robot.getLaserActuel().getPuissanceActuelle()/robot.getLaserActuel().getPuissanceInitiale())*100;
		parcoursRobot.add(robot.decharger());
		afficherInfoRobotDecharger();
		if ((Integer) robot.getScore() >= robot.getBestBatterie().getCout()) {
			parcoursRobot.add(robot.equiper(robot.getBestBatterie()));
			afficherInfoRobotEquiper();
		} else if (robot.getBestBatterieAchetable() != null){
			parcoursRobot.add(robot.equiper(robot.getBestBatterieAchetable()));
			afficherInfoRobotEquiper();
		}

		if ((Integer) robot.getScore() >= robot.getBestLaser().getCout()) {
			parcoursRobot.add(robot.equiper(robot.getBestLaser()));
		} else if ( robot.getBestLaserAchetable() != null) {
			if ((Integer) robot.getScore() >= robot.getBestLaserAchetable().getCout()) {
				parcoursRobot.add(robot.equiper(robot.getBestLaserAchetable()));
				afficherInfoRobotEquiper();
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
	
	/**
	 * Permet d'afficher les informations apres que le robot ai miné.
	 */
	public void afficherInfoRobotMinage() {
		System.out.println("Mouvement n° " + nbmouvement + " (Action Avancer) : \n"
				+ "Temps -> " + robot.getConfiguration().get("temps_avant_que_nasa_repere") + "\n" 
				+ "Position X Robot : " + robot.getPosX() + "; Position Y Robot : " + robot.getPosY() + "\n" 
				+ "Score Robot -> " + robot.getScore() + "\n" 
				+ "Deplacement -> " + parcoursRobot.get(nbmouvement - 1) + "\n" 
				+ "Batterie -> " + robot.getBatterieActuelle().getNom() + " ; Puissance : " + robot.getBatterieActuelle().getPuissanceActuelle() + "\n" 
				+ "Laser -> " + robot.getLaserActuel().getNom() + " ; Puissance : " + robot.getLaserActuel().getPuissanceActuelle() + "\n\n");
	}
	
	/**
	 * Permet d'afficher les informations d'un mouvement du robot quand il rentre vers la base parce que la charge du robot est pleine
	 */
	public void afficherInfoRobotRentrerCharge() {
		nbmouvement++;
		System.out.println("Mouvement n° " + nbmouvement + " (Action Rentrer Base ChargeRobot >= ChargeMaxi) : \n******************Le Robot rentre à la Base******************\n\n");
	}
	
	/**
	 * Permet d'afficher les informations d'un mouvement du robot quand il rentre vers la base parce que la batterie est trop faible
	 */
	public void afficherInfoRobotRentrerEquipement() {
		nbmouvement++;
		System.out.println("Mouvement n° " + nbmouvement + " (Action Rentrer pour changer Equipement) : \n******************Le Robot rentre à la Base******************\n\n");
	}
	
	/**
	 * Permet d'afficher les informations d'un mouvement quand le robot se décharge
	 */
	public void afficherInfoRobotDecharger() {
		nbmouvement++;
		System.out.println("Mouvement n° " + nbmouvement + " (Action Decharger) : \n******************Le Robot se decharge******************\n\n");
	}
	
	/**
	 * Permet d'afficher les informations d'un mouvement quand le robot s'équipe
	 */
	public void afficherInfoRobotEquiper() {
		nbmouvement++;
		System.out.println("Mouvement n° " + nbmouvement + " (Action Decharger) : \n******************Le Robot s'équipe******************\n\n");
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
