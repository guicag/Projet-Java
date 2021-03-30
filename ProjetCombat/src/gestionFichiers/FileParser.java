package gestionFichiers;

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
import java.util.Random;

import elementsCarte.Case;
import elementsCarte.CaseRobot;
import elementsCarte.Mur;
import elementsCarte.PileEnergie;
import elementsCarte.Vide;
import partie.Carte;
import partie.Robot;

public abstract class FileParser {
	public static Case[][] lectureCarte() throws IOException {
		Case[][] carte = null;
		// Ouverture du fichier fichiers/carte.txt
		Path path = FileSystems.getDefault().getPath("fichiers", "carte.txt");
		BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
		try {
			System.out.println("Debut de l'analyse de la carte.");
			String contenuLigne = reader.readLine();
			int nbLigne = 0;
			int nbCol = contenuLigne.length();
			while (contenuLigne != null) {
				contenuLigne = reader.readLine();
				nbLigne++;
			}
			reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
			carte = new Case[nbLigne][nbCol];
			int ligne = 0;
			contenuLigne = reader.readLine();
			while (contenuLigne != null) {
				for (int col = 0; col < contenuLigne.length(); col++) {
					Character c = contenuLigne.charAt(col);
					switch(c) {
						case ' ' :
							carte[ligne][col] = Vide.getInstance();
							break;
						case '%' :
							carte[ligne][col] = new PileEnergie();
							break;
						case '#' :
							carte[ligne][col] = Mur.getInstance();
							break;
						case '@' :
							carte[ligne][col] = CaseRobot.getInstance();
							break;
					}
				}
				contenuLigne = reader.readLine();
				ligne++;
			}
			System.out.println("Analyse de la carte termine. '-_-' \n");
		} catch (IOException e) {
			System.out.println("Erreur lors de la lecture du fichier carte.txt");
			e.printStackTrace();
		} finally {
			reader.close();
		}
		return carte;
	}
	
	public static List<Robot> lectureRobot(Carte carte) {
		List<Robot> listRobot =  new ArrayList<Robot>();
		Map<Integer, Integer> positionsRobot = getPositionRobot(carte);
		try {
			Path path = FileSystems.getDefault().getPath("fichiers", "robots.txt");
			BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
			String contenuLigne = reader.readLine();
			Object[] keys = positionsRobot.keySet().toArray();
			while (contenuLigne != null) {
				String[] mots = contenuLigne.split(" ");
				int randomIndex = new Random().nextInt(positionsRobot.size());
				Robot robotTemp = new Robot(mots[0], mots[1], (Integer) keys[randomIndex], positionsRobot.get(keys[randomIndex]));
				listRobot.add(robotTemp);
				contenuLigne = reader.readLine();
			}
			
			return listRobot;
		} catch (IOException e) {
			System.out.println("Erreur lors de la lecture du fichier robots.txt");
			e.printStackTrace();
		}
		for(int i = 0; i < positionsRobot.size();  i++) {
			
		}
		
		return null;
	}
	
	public static Map<Integer, Integer> getPositionRobot(Carte carte) {
		Map<Integer, Integer> positionsRobot =  new HashMap<Integer, Integer>();
		int nbLigne = carte.getGrille().length;
		int nbCol = carte.getGrille()[0].length;
		for(int i = 0; i <  nbLigne; i++) {
			for(int j = 0; j < nbCol; j++) {
				if(carte.getGrille()[i][j] instanceof CaseRobot) positionsRobot.put(i, j);
			}
		}
		return positionsRobot;
	}
	
	
}
