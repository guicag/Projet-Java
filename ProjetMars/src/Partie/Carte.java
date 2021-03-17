package Partie;

import java.util.ArrayList;

import Equipements.Minerai;
import GestionFichiers.FileParser;

public class Carte {
	
	private Minerai[][] matriceMinerais;
	private ArrayList<Minerai> minerais_disponibles;
	
	public Carte(Minerai[][] minerais) {
		this.matriceMinerais = minerais;
		this.minerais_disponibles = FileParser.lectureDescriptifMesures();
		afficherCarte();
	}
	
	public void afficherCarte() {
		System.out.println("Carte : ");
		for(int ligne = 0; ligne < getRowLength(); ligne++) {
			for(int col = 0; col < getColumnLength(); col++) {
				if(matriceMinerais[ligne][col]!=null)
					System.out.print(matriceMinerais[ligne][col].getCaractere());
				else System.out.print('@');
			}
			System.out.println();
		}
	}
	
	public String toString(){
		String s_carte = "";
		for(int ligne = 0; ligne < getRowLength(); ligne++) {
			for(int col = 0; col < getColumnLength(); col++) {
				if(matriceMinerais[ligne][col]!=null) s_carte += matriceMinerais[ligne][col].getCaractere();
				else s_carte += " ";
			}
			s_carte += "\n";
		}
		return s_carte;
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
	
	public ArrayList<Minerai> getMinerais_disponibles() {
		return this.minerais_disponibles;
	}
	
	/**
	 * Permet de récupérer la position INITIALE du robot. 
	 * 
	 * @return Un tableau d'netiers comprenant dans [0] la position X, et dans [1] la position Y.
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
	
	
}
