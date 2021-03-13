package GestionFichiers;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import Equipements.Batterie;
import Equipements.Equipement;
import Equipements.Laser;
import Equipements.Minerai;

public abstract class FileParser {

	/**
	 * Lis le fichier fichiers/descriptif_mesures.txt afin de retourner une liste
	 * d'objets Minerai dont est composée la carte.
	 * 
	 * @return Une ArrayList des différents objets Minerai dont est composée la
	 *         carte
	 */
	public static ArrayList<Minerai> lectureDescriptifMesures() {
		ArrayList<Minerai> listMinerai = new ArrayList<Minerai>();
		try {
			System.out.println("Début de l'analyse des minerais.");
			// Ouverture du fichier fichiers/descriptif_mesures.txt
			Path path = FileSystems.getDefault().getPath("fichiers", "descriptif_mesures.txt");
			BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
			String ligne = reader.readLine();
			int cpt = 0;
			while (ligne != null) {
				String[] mots = ligne.split(" ");
				// Saut de la première ligne
				if (cpt != 0) {
					char symbole = mots[0].charAt(0);
					String nom;
					int val = 0;
					int durete = 0;
					int poids = 0;

					// chars().allMatch(Character::isDigit)
					if (!isNumericString(mots[2]) && isNumericString(mots[3])) { // Nom du minerai comprend 2 mots
						nom = mots[1] + " " + mots[2];
						val = Integer.parseInt(mots[3].replaceAll(" ", ""));
						durete = Integer.parseInt(mots[4].replaceAll(" ", ""));
						poids = Integer.parseInt(mots[5].replaceAll(" ", ""));
					} else if (!isNumericString(mots[2]) && !isNumericString(mots[3])) { // Nom du minerai comprend 3 mots
						nom = mots[1] + " " + mots[2] + " " + mots[3];
						val = Integer.parseInt(mots[4].replaceAll(" ", ""));
						durete = Integer.parseInt(mots[5].replaceAll(" ", ""));
						poids = Integer.parseInt(mots[6].replaceAll(" ", ""));
					} else { // nom du minerai
						nom = mots[1];
						val = Integer.parseInt(mots[2].replaceAll(" ", ""));
						durete = Integer.parseInt(mots[3].replaceAll(" ", ""));
						poids = Integer.parseInt(mots[4].replaceAll(" ", ""));
					}
					Minerai mineraiTemp = new Minerai(symbole, nom, val, durete, poids);
					listMinerai.add(mineraiTemp);
				}
				ligne = reader.readLine();
				cpt++;
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
	 * @param listMinerai : correspond à la liste des minerais dont la carte peut être composée. Ne doit pas être nulle, elle est nécéssaire à l'analyse.
	 * @return Une matrice d'objets Minerai correspondant à la carte.
	 */
	public static Minerai[][] lectureCarte(ArrayList<Minerai> listMinerai) {
		Minerai[][] carte = new Minerai[20][40];
		try {
			System.out.println("Début de l'analyse de la carte.");

			// Ouverture du fichier fichiers/zone_a_explorer.txt
			Path path = FileSystems.getDefault().getPath("fichiers", "zone_a_explorer.txt");
			BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
			String contenuLigne = reader.readLine();
			
			int ligne = 0;
			while (contenuLigne != null) {
				for (int col = 0; col < contenuLigne.length(); col++) {
					Character c = contenuLigne.charAt(col);
					Minerai mineraiTemp = null;
					
					//Recherche du Minerai correspondant au caractère
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
	 * Lis le fichier fichiers/materiel_disonible.txt afin d'analyser les équipements dont dispose le robot.
	 * 
	 * 
	 * @return Une ArrayList d'équipemnts dont dispose le robot.
	 */
	public static ArrayList<Equipement> lectureEquipementsDisponibles() {
		ArrayList<Equipement> listEquipement = new ArrayList<Equipement>();
		try {
			System.out.println("Début de l'analyse des lasers disponibles.");

			// Ouverture du fichier fichiers/zone_a_explorer.txt
			Path path = FileSystems.getDefault().getPath("fichiers", "materiel_disponible.txt");
			BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
			String ligne = reader.readLine();
			int cpt = 0;
			while (ligne != null) {
				//  Saut de la première ligne
				if(cpt!=0) {
					String[] mots = ligne.split(" ");
					String typeEquip = mots[0].split("_")[0];
					// Si l'équipement est bien un laser
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
				}
				cpt -=- 1;
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
