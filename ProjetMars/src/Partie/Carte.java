package Partie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Equipements.Minerai;
import GestionFichiers.FileParser;

public class Carte {
	
	private Minerai[][] matriceMinerais;
	private ArrayList<Minerai> mineraisDisponibles;
	private static final String ERREUR_CARTE = "Erreur lors de la lecture du fichier de la carte.";
	/**
	 * Constructeur paramétré d'une carte.
	 * @param minerais Matrice de minerais 
	 */
	public Carte(Minerai[][] minerais) {
		this.matriceMinerais = minerais;
		try {
			this.mineraisDisponibles = (ArrayList<Minerai>) FileParser.lectureDescriptifMesures();
		} catch (IOException e) {
			getErreurCarte();
		}
		afficherCarte();
	}
	
	public void afficherCarte() {
		System.out.println("Carte : \n");
		for(int ligne = 0; ligne < getRowLength(); ligne++) {
			for(int col = 0; col < getColumnLength(); col++) {
				if(matriceMinerais[ligne][col] != null)
					System.out.print(matriceMinerais[ligne][col].getCaractere());
				else System.out.print('@');
			}
			System.out.println();
		}
	}
	
	public String toString(){
		String sCarte = "";
		for(int ligne = 0; ligne < getRowLength(); ligne++) {
			for(int col = 0; col < getColumnLength(); col++) {
				if(matriceMinerais[ligne][col]!=null) sCarte += matriceMinerais[ligne][col].getCaractere();
				else sCarte += " ";
			}
			sCarte += "\n";
		}
		return sCarte;
	}

	public Minerai[][] getMatriceMinerais() {
		return matriceMinerais;
	}

	public void setMatriceMinerais(Minerai[][] matriceMinerais) {
		this.matriceMinerais = matriceMinerais;
	}
	
	public int getRowLength() {
		return this.matriceMinerais.length;
	}
	
	public int getColumnLength() {
		return this.matriceMinerais[0].length;
	}
	
	public List<Minerai> getMineraisDisponibles() {
		return this.mineraisDisponibles;
	}
	
	/**
	 * Permet de récupérer la position INITIALE du robot. 
	 * 
	 * @return Un tableau d'entiers comprenant dans [0] la position X, et dans [1] la position Y.
	 */
	public int[] getBase() {
		for(int ligne = 0; ligne < getRowLength(); ligne++) {
			for(int col = 0; col < getColumnLength(); col++) {
				if (this.matriceMinerais[ligne][col] == null) {
					int[] posBase = {ligne, col};
					return posBase;
				}
					
			}
		}
		return null;
	}

	public static String getErreurCarte() {
		return ERREUR_CARTE;
	}
	
	
}
