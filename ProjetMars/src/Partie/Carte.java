package Partie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import GestionFichiers.FileParser;
import equipements.Base;
import equipements.Case;
import equipements.Minerai;
import equipements.Vide;

public class Carte {
	
	private Case[][] matriceCase;
	private ArrayList<Minerai> mineraisDisponibles;
	private static final String ERREUR_CARTE = "Erreur lors de la lecture du fichier de la carte.";
	/**
	 * Constructeur paramétré d'une carte.
	 * @param minerais Matrice de minerais 
	 */
	public Carte(Case[][] minerais) {
		this.matriceCase = minerais;
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
				if(matriceCase[ligne][col] instanceof Base) System.out.print('B');
				else if (matriceCase[ligne][col] instanceof Vide) System.out.print("1");
				else System.out.print(matriceCase[ligne][col].getCaractere());
			}
			System.out.println();
		}
	}
	
	public String toString(){
		String sCarte = "";
		for(int ligne = 0; ligne < getRowLength(); ligne++) {
			for(int col = 0; col < getColumnLength(); col++) {
				if(matriceCase[ligne][col]!=null) sCarte += matriceCase[ligne][col].getCaractere();
				else sCarte += " ";
			}
			sCarte += "\n";
		}
		return sCarte;
	}

	public Case[][] getMatriceMinerais() {
		return matriceCase;
	}

	public void setMatriceMinerais(Minerai[][] matriceMinerais) {
		this.matriceCase = matriceMinerais;
	}
	
	public int getRowLength() {
		return this.matriceCase.length;
	}
	
	public int getColumnLength() {
		return this.matriceCase[0].length;
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
				if (this.matriceCase[ligne][col] instanceof Base) {
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
