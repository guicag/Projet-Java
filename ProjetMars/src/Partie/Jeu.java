package Partie;
import java.util.ArrayList;
import java.util.Map;

import Equipements.Equipement;
import Equipements.Minerai;
import GestionFichiers.FileParser;

public class Jeu {
	
	private Carte carte;
	private Robot robot;
	
	public Jeu() {
		//Analyse des fichiers et création de la carte et du robot
		
		//Analyse des minerais
		ArrayList<Minerai> listMinerai = FileParser.lectureDescriptifMesures();
		//Analyse de la carte
		assert listMinerai != null : "Vous devez analyser les minerais qui composent la carte avant d'analyser celle-ci.";
		this.carte = new Carte(FileParser.lectureCarte(listMinerai));
		
		//Analyse du matériel disponible
		ArrayList<Equipement> listEquipement = FileParser.lectureEquipementsDisponibles();
		Map<String, Number> configuration = FileParser.lectureConfigurationRobot();
		assert listEquipement != null : "Vous devez analyser les equipements dont le robot dispose avant de le créer.";
		assert configuration != null : "Vous devez analyser la configuration du robot avant de le créer.";
		this.robot = new Robot(configuration, listEquipement);
		System.out.println("Jeu initialisé '-_-'");
	}
}
