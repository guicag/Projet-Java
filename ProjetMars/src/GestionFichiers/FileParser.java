package GestionFichiers;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import Equipements.Batterie;
import Equipements.Equipement;
import Equipements.Laser;
import Equipements.Minerai;

public abstract class FileParser {

	/**
	 * Lis le fichier fichiers/descriptif_mesures.txt afin de retourner une liste
	 * d'objets Minerai dont est composï¿½e la carte.
	 * 
	 * @return Une ArrayList des diffï¿½rents objets Minerai dont est composï¿½e la
	 *         carte
	 */
	public static ArrayList<Minerai> lectureDescriptifMesures() {
		ArrayList<Minerai> listMinerai = new ArrayList<Minerai>();
		try {//
			System.out.println("Début de l'analyse des minerais.");
			// Ouverture du fichier fichiers/descriptif_mesures.txt
			Path path = FileSystems.getDefault().getPath("fichiers", "descriptif_mesures.txt");
			BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
			String ligne = reader.readLine();
			ligne = reader.readLine();
			while (ligne != null) {
				String[] mots = ligne.split("\t");
				char symbole = mots[0].charAt(0);
				String nom = mots[1];
				int val = Integer.parseInt(mots[2]);
				int durete = Integer.parseInt(mots[3]);
				int poids = Integer.parseInt(mots[4]);
				Minerai mineraiTemp = new Minerai(symbole, nom, val, durete, poids);
				listMinerai.add(mineraiTemp);				
				ligne = reader.readLine();
			}
			listMinerai.sort(null);
			System.out.println("Analyse des minerais terminée. '-_-' \n");
		} catch (IOException e) {
			System.out.println("File I/O error!");
			e.printStackTrace();
		}
		return listMinerai;
	}


	/**
	 * Lis le fichier fichiers/zone_a_explorer.txt afin d'analyser les minerais qui composent la carte de Mars.
	 * 
	 * @param listMinerai : correspond a la liste des minerais dont la carte peut etre compose. Ne doit pas etre nulle, elle est necessaire a l'analyse.
	 * @return Une matrice d'objets Minerai correspondant a la carte.
	 */
	public static Minerai[][] lectureCarte(ArrayList<Minerai> listMinerai) {
		Minerai[][] carte = null;
		try {
			System.out.println("Début de l'analyse de la carte.");
			// Ouverture du fichier fichiers/zone_a_explorer.txt
			Path path = FileSystems.getDefault().getPath("fichiers", "zone_a_explorer.txt");
			BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
			String contenuLigne = reader.readLine();
			int nbLigne = 0;
			int nbCol = contenuLigne.length();
			while (contenuLigne != null) {
				contenuLigne = reader.readLine();
				nbLigne++;
			}
			reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
			carte = new Minerai[nbLigne][nbCol];
			int ligne = 0;
			contenuLigne = reader.readLine();
			while (contenuLigne != null) {
				for (int col = 0; col < contenuLigne.length(); col++) {
					Character c = contenuLigne.charAt(col);
					Minerai mineraiTemp = null;
					//Recherche du Minerai correspondant au caractÃ¨re
					for(Minerai minerai : listMinerai) {
						if(minerai.getCaractere()==c) mineraiTemp = minerai;
					}
					carte[ligne][col] = mineraiTemp;
				}
				contenuLigne = reader.readLine();
				ligne++;
			}
			System.out.println("Analyse de la carte terminée. '-_-' \n");
		} catch (IOException e) {
			System.out.println("File I/O error!");
			e.printStackTrace();
		}
		return carte;
	}
	
	/**
	 * Lis le fichier fichiers/materiel_disonible.txt afin d'analyser les ï¿½quipements dont dispose le robot.
	 * 
	 * 
	 * @return Une ArrayList d'Ã©quipemnts dont dispose le robot.
	 */
	public static ArrayList<Equipement> lectureEquipementsDisponibles() {
		ArrayList<Equipement> listEquipement = new ArrayList<Equipement>();
		try {
			System.out.println("Début de l'analyse des lasers disponibles.");
			// Ouverture du fichier fichiers/zone_a_explorer.txt
			Path path = FileSystems.getDefault().getPath("fichiers", "materiel_disponible.txt");
			BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
			String ligne = reader.readLine();
			ligne = reader.readLine();
			while (ligne != null) {
				String[] mots = ligne.split("\t");
				String typeEquip = mots[0].split("_")[0];
				// Si l'ï¿½quipement est bien un laser
				if(typeEquip.equals("laser")) {
					String nom = mots[0];
					int cout = Integer.parseInt(mots[1]);
					double pui_initiale = Double.parseDouble(mots[2]);
					Laser laserTemp = new Laser(nom, cout, pui_initiale);
					listEquipement.add(laserTemp);
				} else if(typeEquip.equals("batterie")) {
					String nom = mots[0];
					int cout = Integer.parseInt(mots[1]);
					double pui_initiale = Double.parseDouble(mots[2]);
					Batterie batterieTemp = new Batterie(nom, cout, pui_initiale);
					listEquipement.add(batterieTemp);
				}
				ligne = reader.readLine();
			}
			listEquipement.sort(null);
			System.out.println("Analyse des équipements disponibles terminée. '-_-' \n");
		} catch (IOException e) {
			System.out.println("File I/O error!");
			e.printStackTrace();
		}
		return  listEquipement;
	}
	
	/**
	 * Lis le fichier fichiers/configuration_robot.txt afin d'analyser la configuration du robot.
	 * 
	 * @return 
	 */
	public static Map<String, Number> lectureConfigurationRobot() {
		Map<String, Number> configuration = new HashMap<String, Number>();
		try {
			System.out.println("Début de l'analyse de la configuration du robot.");

			// Ouverture du fichier fichiers/configuration_robot.txt
			Path path = FileSystems.getDefault().getPath("fichiers", "configuration_robot.txt");
			BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
			String ligne = reader.readLine();
			while (ligne != null) {
				String[] mots = ligne.split("=");
				configuration.put(mots[0], Double.parseDouble(mots[1]));
				ligne = reader.readLine();
			}
			
			for (String stringProp : configuration.keySet()) {
				Number valProp = configuration.get(stringProp);
			}
			
			System.out.println("Analyse de la configuration du robot. '-_-' \n");
		} catch (IOException e) {
			System.out.println("File I/O error!");
			e.printStackTrace();
		}
		return  null;
	}

	public static boolean isNumericString(String s) {
		boolean res = true;
		for (int i = 0; i < s.length(); i++) {
			if (Character.isDigit(s.charAt(i)) == false) {
				res = false;
			}
		}
		return res;
	}
}
