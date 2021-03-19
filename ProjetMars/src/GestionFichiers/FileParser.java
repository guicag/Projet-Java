package GestionFichiers;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Equipements.Batterie;
import Equipements.Equipement;
import Equipements.Laser;
import Equipements.Minerai;

 public abstract class FileParser {
	private static final String ERREUR_FICHIER = "File I/O error";
	private static final String FICHIER = "Fichiers";
	
	private FileParser() {
		super();
	}
	
	/**
	 * Lis le fichier fichiers/descriptif_mesures.txt afin de retourner une liste
	 * d'objets Minerai dont est compos�e la carte.
	 * 
	 * @return Une ArrayList des diff�rents objets Minerai dont est compos�e la
	 *         carte
	 * @throws IOException 
	 */
	public static List<Minerai> lectureDescriptifMesures() throws IOException {
		ArrayList<Minerai> listMinerai = new ArrayList<>();
		// Ouverture du fichier fichiers/descriptif_mesures.txt
		Path path = FileSystems.getDefault().getPath(FICHIER, "descriptif_mesures.txt");
		BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
		try {
			System.out.println("D�but de l'analyse des minerais.");
			String ligne = reader.readLine();
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
			System.out.println("Analyse des minerais termin�e. '-_-' \n");
		} catch (IOException e) {
			System.out.println(ERREUR_FICHIER);
			e.printStackTrace();
		}finally {
			reader.close();
		}
		return listMinerai;
	}


	/**
	 * Lis le fichier fichiers/zone_a_explorer.txt afin d'analyser les minerais qui composent la carte de Mars.
	 * 
	 * @param listMinerai : correspond a la liste des minerais dont la carte peut etre compose. Ne doit pas etre nulle, elle est necessaire a l'analyse.
	 * @return Une matrice d'objets Minerai correspondant a la carte.
	 * @throws IOException 
	 */
	public static Minerai[][] lectureCarte(List<Minerai> listMinerai) throws IOException {
		Minerai[][] carte = null;
		// Ouverture du fichier fichiers/zone_a_explorer.txt
		Path path = FileSystems.getDefault().getPath(FICHIER, "zone_a_explorer.txt");
		BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
		try {
			System.out.println("D�but de l'analyse de la carte.");
			
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
					//Recherche du Minerai correspondant au caractère
					for(Minerai minerai : listMinerai) {
						if(minerai.getCaractere()==c) mineraiTemp = minerai;
					}
					carte[ligne][col] = mineraiTemp;
				}
				contenuLigne = reader.readLine();
				ligne++;
			}
			System.out.println("Analyse de la carte termin�e. '-_-' \n");
		} catch (IOException e) {
			System.out.println(ERREUR_FICHIER);
			e.printStackTrace();
		}finally {
			reader.close();
		}
		return carte;
	}
	
	/**
	 * Lis le fichier fichiers/materiel_disonible.txt afin d'analyser les equipements dont dispose le robot.
	 * 
	 * 
	 * @return Une ArrayList d'equipements dont dispose le robot.
	 * @throws IOException 
	 */
	public static List<Equipement> lectureEquipementsDisponibles() throws IOException {
		ArrayList<Equipement> listEquipement = new ArrayList<>();
		// Ouverture du fichier fichiers/materiel_disponible.txt
		Path path = FileSystems.getDefault().getPath(FICHIER, "materiel_disponible.txt");
		BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
		try {
			System.out.println("D�but de l'analyse des lasers disponibles.");
			String ligne = reader.readLine();
			ligne = reader.readLine();
			while (ligne != null) {
				String[] mots = ligne.split("\t");
				String typeEquip = mots[0].split("_")[0];
				// Si l'equipement est bien un laser
				if(typeEquip.equals("laser")) {
					String nom = mots[0];
					int cout = Integer.parseInt(mots[1]);
					double puiInitiale = Double.parseDouble(mots[2]);
					Laser laserTemp = new Laser(nom, cout, puiInitiale);
					listEquipement.add(laserTemp);
				} else if(typeEquip.equals("batterie")) {
					String nom = mots[0];
					int cout = Integer.parseInt(mots[1]);
					double puiInitiale = Double.parseDouble(mots[2]);
					Batterie batterieTemp = new Batterie(nom, cout, puiInitiale);
					listEquipement.add(batterieTemp);
				}
				ligne = reader.readLine();
			}
			listEquipement.sort(null);
			System.out.println("Analyse des �quipements disponibles termin�e. '-_-' \n");
		} catch (IOException e) {
			System.out.println(ERREUR_FICHIER);
			e.printStackTrace();
		}
		finally {
			reader.close();
		}
		return  listEquipement;
	}
	
	/**
	 * Lis le fichier fichiers/configuration_robot.txt afin d'analyser la configuration du robot.
	 * 
	 * @return 
	 * @throws IOException 
	 */
	public static Map<String, Number> lectureConfigurationRobot() throws IOException {
		Map<String, Number> configuration = new HashMap<>();
		// Ouverture du fichier fichiers/configuration_robot.txt
		System.out.println("D�but de l'analyse de la configuration du robot.");
		Path path = FileSystems.getDefault().getPath(FICHIER, "configuration_robot.txt");
		BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
		try {
			
			String ligne = reader.readLine();
			while (ligne != null) {
				String[] mots = ligne.split("=");
				configuration.put(mots[0], Double.parseDouble(mots[1]));
				ligne = reader.readLine();
			}
			
			System.out.println("Analyse de la configuration du robot. '-_-' \n");
		} catch (IOException e) {
			System.out.println(ERREUR_FICHIER);
			e.printStackTrace();
		}finally {
			reader.close();
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
