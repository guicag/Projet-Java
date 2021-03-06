package partie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import elementsCarte.Base;
import elementsCarte.Case;
import elementsCarte.Minerai;
import elementsCarte.Vide;
import gestionFichiers.FileParser;
/**
 * 
 * @author Cl?ment
 *
 */
public class Carte {
	
	private Case[][] matriceCase;
	private ArrayList<Minerai> mineraisDisponibles;
	
	/**
	 * Constructeur param?tr? d'une carte.
	 * @param minerais Matrice de minerais 
	 */
	public Carte(Case[][] minerais, ArrayList<Minerai> listMinerai) {
		this.matriceCase = minerais;
		this.mineraisDisponibles = listMinerai;
		afficherCarte();
	}
	
	public void afficherCarte() {
		System.out.println("Carte : \n");
		for(int ligne = 0; ligne < getRowLength(); ligne++) {
			for(int col = 0; col < getColumnLength(); col++) {
				if(matriceCase[ligne][col] instanceof Base) System.out.print('B');
				else if (matriceCase[ligne][col] instanceof Vide) System.out.print(" ");
				else System.out.print(matriceCase[ligne][col].getCaractere());
			}
			System.out.println();
		}
	}
	
	public String toString(){
		String sCarte = "";
		for(int ligne = 0; ligne < getRowLength(); ligne++) {
			for(int col = 0; col < getColumnLength(); col++) {
				if(matriceCase[ligne][col] instanceof Minerai) sCarte += matriceCase[ligne][col].getCaractere();
				if(matriceCase[ligne][col] instanceof Vide) sCarte += "1";
				if(matriceCase[ligne][col] instanceof Base) sCarte += "B";
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
	 * Permet de r?cup?rer la position INITIALE du robot. 
	 * 
	 * @return Un tableau d'entiers comprenant dans [0] la position X, et dans [1] la position Y.
	 */
	public int[] getBase() {
		int[] ret = null;
		for(int ligne = 0; ligne < getRowLength(); ligne++) {
			for(int col = 0; col < getColumnLength(); col++) {
				if (this.matriceCase[ligne][col] instanceof Base) {
					int[] posBase = {ligne, col};
					return posBase;
				}
			}
		}
		return ret;
	}
}
