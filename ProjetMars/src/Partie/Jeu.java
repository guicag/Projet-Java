package Partie;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import GestionFichiers.FileParser;
import equipements.Batterie;
import equipements.Equipement;
import equipements.Laser;
import equipements.Minerai;

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
	public Direction choisirDirection() {
		int posXRobot = robot.getPosX();
		int posYRobot = robot.getPosY();
		TreeMap<Minerai, Direction> listMineraiDir = new TreeMap<Minerai, Direction>();
		List<Minerai> listMinerais = new ArrayList<Minerai>();
		for(Direction dir : Direction.values()) {
			switch(dir) {
				case NORD :
					if(posXRobot != 0) {
						listMineraiDir.put(carte.getMatriceMinerais()[posXRobot-1][posYRobot], dir);// NORD impossible
						listMinerais.add(carte.getMatriceMinerais()[posXRobot-1][posYRobot]);
					}
					break;
				case SUD :
					if(posXRobot != carte.getRowLength()-1) {
						listMineraiDir.put(carte.getMatriceMinerais()[posXRobot+1][posYRobot], dir);// SUD impossible
						listMinerais.add(carte.getMatriceMinerais()[posXRobot+1][posYRobot]);
					}
					break;
				case EST :
					if(posYRobot != carte.getColumnLength()-1) {
						listMineraiDir.put(carte.getMatriceMinerais()[posXRobot][posYRobot+1], dir);// EST impossible
						listMinerais.add(carte.getMatriceMinerais()[posXRobot][posYRobot+1]);
					}
					break;
				case OUEST :
					if(posYRobot != 0) {
						listMineraiDir.put(carte.getMatriceMinerais()[posXRobot][posYRobot-1], dir);// OUEST impossible
						listMinerais.add(carte.getMatriceMinerais()[posXRobot][posYRobot-1]);
					}
					break;
			}
		}
		if (testZeroValues(listMinerais)) {
			
		}
		return listMineraiDir.firstEntry().getValue();
	}
	
	public boolean testZeroValues(List<Minerai> listMinerais) {
		boolean res;
		int count = 0;
		for (int i = 0; i < listMinerais.size(); i++) {
			if (listMinerais.get(i).getRatio() == 0) {
				count++;
			}
		}
		if (count == listMinerais.size()) {
			res = true;
		} else {
			res = false;
		}
		
		return true;
	}
	
	/**
	 * Fonction permettant de choisir une direction à suivre lors du prochain déplacement, et de toruner le robot dans celle-ci.
	 * 
	 * @return Un enum Direction indisuant la prhcaine direction à suivre.
	 */
	public void jouer() {
		//Première phase 
		//		Stratégie : Miner les plus rentable jusqu'à avoir le meilleur laser et la meilleure batterie.
		//while(robot.getConfiguration().get("temps_avant_que_nasa_repere"))
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
