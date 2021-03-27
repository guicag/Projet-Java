package tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import elementsCarte.Base;
import elementsCarte.Case;
import elementsCarte.Minerai;
import equipements.Equipement;
import gestionFichiers.FileParser;

/**
 * Classe permettant de tester les fonctions de la classe FileParser, qui lit les fichiers du jeu. 
 * @author cleme
 */
class TestFileParser {

	@Test
	/**
	 * Permet de tester la fonction de lecture du fichier descriptif_mesures.txt . 
	 * La fonction vérifie si la liste de minerai renvoyée n'est pas nulle.
	 */
	void testLectureDescriptifMesures() {
		try {
			List<Minerai> listMinerai = FileParser.lectureDescriptifMesures();
			assertNotNull(listMinerai);
			assertTrue(isSortedListMinerai(listMinerai));
		} catch (IOException e) {
			System.out.println("Exception de lecture du fichier descriptif_mesures.txt.");
			e.printStackTrace();
		}
	}
	
	@Test
	/**
	 * Permet de vérifier que la carte soit bien initialisée, elle qu'elle contienne 
	 * le bon nombre de minerais en fonction de sa taille, et une base et une seule.
	 */
	void testLectureCarte(){
		List<Minerai> listMinerai;
		try {
			listMinerai = FileParser.lectureDescriptifMesures();
			Case[][] matriceCases = FileParser.lectureCarte(listMinerai);
			assertNotNull(matriceCases);
			int nbMinerais = 0;
			int nbBase = 0;
			int nbCasesTotal = matriceCases.length * matriceCases[0].length;
			// Décompte du nombre de Minerai et de Base dans la carte
			for(int ligne = 0; ligne < matriceCases.length; ligne++) { 
				for(int col = 0; col < matriceCases[0].length; col ++) {
					if(matriceCases[ligne][col] instanceof Minerai) nbMinerais++;
					if(matriceCases[ligne][col] instanceof Base) nbBase++;
				}
			}
			assertEquals(1, nbBase);
			assertEquals(nbCasesTotal-1, nbMinerais);
		} catch (IOException e) {
			System.out.println("Exception dans la lecture du fichier descriptif_mesures.txt.");
			e.printStackTrace();
		}
	}
	
	@Test
	/**
	 * Permet de tester la fonction de lecture du fichier contenant les equipement disponible fichiers/materiel_disponible.txt .
	 * Le test vérifie que la liste d'équipements renvoyée contient le bon nombre de laser et de batteries, et qu'elle est bien triée par ratio décroissant.
	 */
	void testLectureEquipementsDisponible() {
		try {
			List<Equipement> listEquipement = FileParser.lectureEquipementsDisponibles();
			assertNotNull(listEquipement);
			assertTrue(isSortedListEquipement(listEquipement));
		} catch (IOException e) {
			System.out.println("Exception dans la lecture du fichier fichiers/materiel_disponible.txt .");
			e.printStackTrace();
		}
	}
	
	@Test
	/**
	 * Permet de tester la fonction de lecture du fichier contenant la configuration du robot fichiers/configuration_robot.txt .
	 * Le test vérifie que la Map renvoyée n'est pas nulle.
	 */
	void testLectureConfigurationRobot() {
		try {
			Map<String, Number> configuration = FileParser.lectureConfigurationRobot();
			assertNotNull(configuration);
		} catch (IOException e) {
			System.out.println("Exception dans la lecture du fichier fichiers/configuration_robot.txt .");
			e.printStackTrace();
		}
	}
	
	/**
	 * Permet de tester si une liste de Minerai est bien trié en fonction du ratio, dans l'ordre décroissant.
	 * @param listMinerai
	 * @return boolean Un booléen indiquant si la liste de Minerai est bien triée.
	 */
	@Test
	boolean isSortedListMinerai (List<Minerai> listMinerai) {
		for(int i = 0; i < listMinerai.size()-1; i++) {
			if(listMinerai.get(i).compareTo(listMinerai.get(i+1)) > 0) return false;
		}
		return true;
	}
	
	/**
	 * Permet de tester si une liste d'objets Equipement est bien trié en fonction du ratio, dans l'ordre décroissant.
	 * @param listEquipements
	 * @return boolean Un booléen indiquant si la liste de Minerai est bien triée.
	 */
	@Test
	boolean isSortedListEquipement (List<Equipement> listEquipements) {
		for(int i = 0; i < listEquipements.size()-1; i++) {
			if(listEquipements.get(i).compareTo(listEquipements.get(i+1)) > 0) return false;
		}
		return true;
	}
	
}
