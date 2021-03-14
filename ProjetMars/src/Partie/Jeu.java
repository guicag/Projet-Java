package Partie;
import java.util.ArrayList;

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
		ProprietesRobot prop = FileParser.lectureConfigurationRobot();
		assert listEquipement != null : "Vous devez analyser les equipements dont le robot dispose avant de le créer.";
		this.robot = new Robot();
	}
}
